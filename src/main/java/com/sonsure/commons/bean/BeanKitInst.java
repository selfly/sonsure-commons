package com.sonsure.commons.bean;

import com.sonsure.commons.exception.SonsureBeanException;
import com.sonsure.commons.model.Model;
import com.sonsure.commons.model.Page;
import com.sonsure.commons.utils.ClassUtils;
import com.sonsure.commons.utils.NameUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class BeanKitInst {

    private List<TypeConverter> typeConverters = new ArrayList<>();

    public BeanKitInst() {
        typeConverters.add(new IEnumStringConverter());
        typeConverters.add(new NumberConverter());
    }

    /**
     * 拷贝属性值
     *
     * @param orig
     * @param dest
     */
    public void copyFields(Object orig, Object dest) {

        if (orig == null || dest == null) {
            return;
        }

        Field[] destBeanFields = ClassUtils.getBeanFields(dest.getClass());

        for (Field destField : destBeanFields) {
            try {
                if (Modifier.isFinal(destField.getModifiers())) {
                    continue;
                }
                Field origField = ClassUtils.getBeanField(orig.getClass(), destField.getName());
                if (origField == null) {
                    continue;
                }
                origField.setAccessible(true);
                Object value = origField.get(orig);
                destField.setAccessible(true);
                destField.set(dest, value);
            } catch (IllegalAccessException e) {
                throw new SonsureBeanException("Field拷贝失败:" + destField.getName(), e);
            }
        }
    }

    /**
     * map转为bean，key名为下划线命名方式
     *
     * @param <T>       the type parameter
     * @param mapList   the map list
     * @param beanClass the bean class
     * @return t list
     */
    public <T> List<T> underlineKeyMapToBean(List<Map<String, Object>> mapList, Class<T> beanClass) {
        return mapToBean(mapList, beanClass, '_');
    }

    /**
     * map转为bean，key名为bean属性名
     *
     * @param <T>       the type parameter
     * @param mapList   the map list
     * @param beanClass the bean class
     * @return t list
     */
    public <T> List<T> mapToBean(List<Map<String, Object>> mapList, Class<T> beanClass) {
        return mapToBean(mapList, beanClass, null);
    }

    /**
     * map转为bean，最后一个参数指定map中的key转换成骆驼命名法(JavaBean中惯用的属性命名)的分隔符,例如login_name转换成loginName,分隔符为下划线_
     * 指定了分隔符进行转换时如果属性不带分隔符会统一转成小写,毕竟JavaBean中除了常量外应该不会定义有大写的属性
     * 为空则不进行任何转换
     *
     * @param <T>       the type parameter
     * @param mapList   the map list
     * @param beanClass the bean class
     * @return t list
     */
    public <T> List<T> mapToBean(List<Map<String, Object>> mapList, Class<T> beanClass, Character delimiter) {

        List<T> beanList = new ArrayList<T>(mapList == null ? 0 : mapList.size());
        if (mapList == null) {
            return beanList;
        }
        for (Map<String, Object> map : mapList) {

            T t = mapToBean(map, beanClass, delimiter);

            beanList.add(t);
        }
        return beanList;
    }

    /**
     * bean转换成map
     *
     * @param obj
     * @return
     */
    public Map<String, Object> beanToMap(Object obj) {
        if (obj == null) {
            return Collections.EMPTY_MAP;
        }
        Map<String, Object> map = new HashMap<>();
        PropertyDescriptor[] propertyDescriptors = ClassUtils.getPropertyDescriptors(obj.getClass());
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

            if (propertyDescriptor.getReadMethod() == null) {
                continue;
            }

            Method readMethod = propertyDescriptor.getReadMethod();
            Object value = ClassUtils.invokeMethod(readMethod, obj);
            if (value == null) {
                continue;
            }
            map.put(propertyDescriptor.getName(), value);
        }
        return map;
    }

    /**
     * map转为bean
     *
     * @param map       the map
     * @param beanClass the bean class
     * @return t
     */
    public <T> T underlineKeyMapToBean(Map<String, Object> map, Class<T> beanClass) {
        return mapToBean(map, beanClass, '_');
    }

    /**
     * map转为bean
     *
     * @param map       the map
     * @param beanClass the bean class
     * @return t
     */
    public <T> T mapToBean(Map<String, Object> map, Class<T> beanClass) {
        return mapToBean(map, beanClass, null);
    }

    /**
     * map转为bean，最后一个参数指定map中的key转换成骆驼命名法(JavaBean中惯用的属性命名)的分隔符,例如login_name转换成loginName,分隔符为下划线_
     * 指定了分隔符进行转换时如果属性不带分隔符会统一转成小写,毕竟JavaBean中除了常量外应该不会定义有大写的属性
     * 为空则不进行任何转换
     *
     * @param map       the map
     * @param beanClass the bean class
     * @param delimiter the delimiter
     * @return t
     */
    @SuppressWarnings("unchecked")
    public <T> T mapToBean(Map<String, Object> map, Class<T> beanClass, Character delimiter) {

        T bean = (T) ClassUtils.newInstance(beanClass);
        if (map == null) {
            return bean;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {

            Object value = entry.getValue();

            if (value == null) {
                continue;
            }

            String name = entry.getKey();
            if (delimiter != null) {
                name = StringUtils.indexOf(name, delimiter) != -1 ? NameUtils.getCamelName(name, delimiter) : name
                        .toLowerCase();
            }
            PropertyDescriptor targetPd = ClassUtils.getPropertyDescriptor(beanClass, name);

            Method writeMethod;
            if (targetPd == null || (writeMethod = targetPd.getWriteMethod()) == null) {

                if (Model.class.isAssignableFrom(beanClass)) {
                    ((Model) bean).addProperty(name, value);
                }
            } else {

                value = typeConvert(value.getClass(), targetPd.getPropertyType(), name, value);

                ClassUtils.invokeMethod(writeMethod, bean, value);
            }
        }
        return bean;
    }

    public <T> Page<T> copyPage(Class<T> clazz, Page<?> page) {
        Page<T> resultPage = new Page<>(page.getPagination());
        List<T> list = this.copyProperties(clazz, page.getList());
        resultPage.setList(list);
        return resultPage;
    }

    /**
     * 拷贝属性
     *
     * @param clazz the clazz
     * @param list  the list
     * @return the page list
     */
    public <T> List<T> copyProperties(Class<T> clazz, List<?> list) {
        return copyProperties(clazz, list, null);
    }

    /**
     * 拷贝属性
     *
     * @param clazz            the clazz
     * @param list             the list
     * @param ignoreProperties the ignore properties
     * @return the page list
     */
    public <T> List<T> copyProperties(Class<T> clazz, List<?> list, String[] ignoreProperties) {

        //返回的list列表
        List<T> resultList = new ArrayList<T>();

        if (list == null || list.isEmpty()) {
            return resultList;
        }

        Iterator<?> iterator = list.iterator();

        //循环调用转换单个对象
        while (iterator.hasNext()) {

            try {

                T t = clazz.newInstance();

                Object obj = iterator.next();

                t = copyProperties(t, obj, ignoreProperties);

                resultList.add(t);

            } catch (Exception e) {
                throw new SonsureBeanException("列表转换失败", e);
            }
        }

        return resultList;
    }

    /**
     * 单个对象拷贝
     *
     * @param target 目标对象
     * @param source 源对象
     * @return 转换后的目标对象
     */
    public <T> T copyProperties(T target, Object source) {
        return copyProperties(target, source, null);
    }

    /**
     * 单个对象转换
     *
     * @param target           目标对象
     * @param source           源对象
     * @param ignoreProperties 需要过滤的属性
     * @return 转换后的目标对象
     */
    public <T> T copyProperties(T target, Object source, String[] ignoreProperties) {

        //过滤的属性
        List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

        //拷贝相同的属性
        copySameProperties(target, source, ignoreList);

        //拷贝扩展属性
        copyExtensionProperties(target, source, ignoreList);

        return target;
    }

    /**
     * 拷贝相同的属性
     *
     * @param target     the target
     * @param source     the source
     * @param ignoreList the ignore list
     */
    private void copySameProperties(Object target, Object source, List<String> ignoreList) {

        //获取目标对象属性信息
        PropertyDescriptor[] targetPds = ClassUtils.getPropertyDescriptors(target.getClass());

        for (PropertyDescriptor targetPd : targetPds) {

            if (targetPd.getWriteMethod() == null || (ignoreList != null && ignoreList.contains(targetPd.getName()))) {

                continue;
            }

            PropertyDescriptor sourcePd = ClassUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());

            if (sourcePd != null && sourcePd.getReadMethod() != null) {

                Method readMethod = sourcePd.getReadMethod();
                Object value = ClassUtils.invokeMethod(readMethod, source);
                if (value == null) {
                    continue;
                }
                Method writeMethod = targetPd.getWriteMethod();

                //自定义转换
                value = typeConvert(sourcePd.getPropertyType(), targetPd.getPropertyType(), targetPd.getName(), value);
                ClassUtils.invokeMethod(writeMethod, target, value);
            }
        }
    }

    /**
     * 拷贝
     *
     * @param target
     * @param source
     * @param ignoreList
     */
    private static void copyExtensionProperties(Object target, Object source, List<String> ignoreList) {

        if (!(target instanceof Model) || !(source instanceof Model)) {
            return;
        }

        Map<String, Object> extensionProperties = ((Model) source).getProperties();
        if (extensionProperties == null) {
            return;
        }
        for (Map.Entry<String, Object> entry : extensionProperties.entrySet()) {
            if (ignoreList != null && ignoreList.contains(entry.getKey())) {
                continue;
            }
            ((Model) target).addProperty(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 用户自定义转换
     *
     * @param sourcePropertyType the source property type
     * @param targetPropertyType the target property type
     * @param value              the value
     * @return the object
     */
    private Object typeConvert(Class<?> sourcePropertyType, Class<?> targetPropertyType, String fileName, Object value) {

        if (value == null || sourcePropertyType == targetPropertyType || (this.typeConverters == null || this.typeConverters.isEmpty())) {
            return value;
        }

        for (TypeConverter typeConverter : this.typeConverters) {
            if (typeConverter.isSupport(sourcePropertyType, targetPropertyType, fileName)) {
                return typeConverter.convert(sourcePropertyType, targetPropertyType, value);
            }
        }
        return value;
    }

    /**
     * 注册转换器
     *
     * @param converter the converter
     */
    public BeanKitInst registerConverter(TypeConverter converter) {
        this.typeConverters.add(converter);
        return this;
    }

    /**
     * 移除注册的转换器
     *
     * @param converter the converter
     * @return the bean kit inst
     */
    public BeanKitInst unregisterConverter(TypeConverter converter) {
        this.typeConverters.remove(converter);
        return this;
    }

    /**
     * 清空注册的转换器
     */
    public BeanKitInst clearConverter() {
        this.typeConverters.clear();
        return this;
    }

    /**
     * 获取注册的转换器
     *
     * @return the converters
     */
    public List<TypeConverter> getConverters() {
        return this.typeConverters;
    }


}
