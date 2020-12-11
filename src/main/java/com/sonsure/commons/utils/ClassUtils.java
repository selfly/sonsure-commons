/*
 * Copyright (c) 2020. www.sonsure.com Inc. All rights reserved.
 * You may obtain more information at
 *
 *   http://www.sonsure.com
 *
 * Designed By Selfly Lee (selfly@live.com)
 */

package com.sonsure.commons.utils;

import com.sonsure.commons.bean.BeanFieldCache;
import com.sonsure.commons.bean.IntrospectionCache;
import com.sonsure.commons.exception.SonsureBeanException;
import com.sonsure.commons.exception.SonsureException;
import com.sonsure.commons.model.Model;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类辅助
 * <p/>
 *
 * @author liyd
 * @date : 2/12/14
 */
public class ClassUtils {


    /**
     * 返回JavaBean所有属性的<code>PropertyDescriptor</code>
     *
     * @param beanClass the bean class
     * @return the property descriptor [ ]
     */
    public static PropertyDescriptor[] getSelfPropertyDescriptors(Class<?> beanClass) {

        return getPropertyDescriptors(beanClass, beanClass.getSuperclass());
    }

    /**
     * 返回JavaBean所有属性的<code>PropertyDescriptor</code>
     *
     * @param beanClass the bean class
     * @return the property descriptor [ ]
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> beanClass) {

        return getPropertyDescriptors(beanClass, null);
    }


    /**
     * 返回JavaBean所有属性的<code>PropertyDescriptor</code>
     *
     * @param beanClass the bean class
     * @param stopClass the stop class
     * @return the property descriptor [ ]
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> beanClass, Class<?> stopClass) {

        IntrospectionCache introspectionCache = IntrospectionCache.forClass(beanClass, stopClass);
        return introspectionCache.getPropertyDescriptors();
    }

    /**
     * 返回JavaBean给定JavaBean给定属性的 <code>PropertyDescriptors</code>
     *
     * @param beanClass    the bean class
     * @param propertyName the name of the property
     * @return the corresponding PropertyDescriptor, or <code>null</code> if none
     */
    public static PropertyDescriptor getPropertyDescriptor(Class<?> beanClass, String propertyName) {

        IntrospectionCache introspectionCache = IntrospectionCache.forClass(beanClass);
        return introspectionCache.getPropertyDescriptor(propertyName);
    }


    /**
     * 返回JavaBean的所有field
     *
     * @param beanClass the bean class
     * @return the fields
     */
    public static Field[] getSelfFields(Class<?> beanClass) {

        return getBeanFields(beanClass, beanClass.getSuperclass());
    }

    /**
     * 返回JavaBean的所有field
     *
     * @param beanClass the bean class
     * @return the fields
     */
    public static Field[] getBeanFields(Class<?> beanClass) {

        return getBeanFields(beanClass, null);
    }

    /**
     * 返回JavaBean的所有field
     *
     * @param beanClass the bean class
     * @return the fields
     */
    public static Map<String, Field> getBeanFieldMap(Class<?> beanClass) {

        Field[] beanFields = getBeanFields(beanClass, null);
        Map<String, Field> fieldMap = new HashMap<>();
        for (Field beanField : beanFields) {
            fieldMap.put(beanField.getName(), beanField);
        }
        return fieldMap;
    }


    /**
     * 返回JavaBean的所有field
     *
     * @param beanClass the bean class
     * @param stopClass the stop class
     * @return the fields
     */
    public static Field[] getBeanFields(Class<?> beanClass, Class<?> stopClass) {
        BeanFieldCache beanFieldCache = BeanFieldCache.forClass(beanClass, stopClass);
        return beanFieldCache.getBeanFields();
    }

    /**
     * 返回JavaBean给定名称的field
     *
     * @param beanClass the bean class
     * @param fieldName the name
     * @return the field, or <code>null</code> if none
     */
    public static Field getBeanField(Class<?> beanClass, String fieldName) {

        BeanFieldCache beanFieldCache = BeanFieldCache.forClass(beanClass);
        return beanFieldCache.getBeanField(fieldName);
    }

    /**
     * Sets bean field value.
     *
     * @param bean      the bean
     * @param fieldName the field name
     * @param value     the value
     */
    public static void setFieldValue(Object bean, String fieldName, Object value) {
        try {
            final Field beanField = getBeanField(bean.getClass(), fieldName);
            beanField.setAccessible(true);
            beanField.set(bean, value);
        } catch (IllegalAccessException e) {
            throw new SonsureBeanException("设置属性值失败", e);
        }
    }

    /**
     * 获取对象指定属性值
     *
     * @param obj       the obj
     * @param fieldName the field name
     * @return field value
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }
        try {
            final Field beanField = getBeanField(obj.getClass(), fieldName);
            beanField.setAccessible(true);
            return beanField.get(obj);
        } catch (IllegalAccessException e) {
            throw new SonsureBeanException("获取属性值失败", e);
        }
    }

    /**
     * 获取对象指定属性值
     *
     * @param obj       the obj
     * @param fieldName the field name
     * @return property value
     */
    public static Object getPropertyValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(obj.getClass(), fieldName);
        Method readMethod = propertyDescriptor.getReadMethod();
        return invokeMethod(readMethod, obj);
    }

    /**
     * Sets property value.
     *
     * @param obj       the obj
     * @param fieldName the field name
     * @param value     the value
     */
    public static void setPropertyValue(Object obj, String fieldName, Object value) {
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(obj.getClass(), fieldName);
        Method writeMethod = propertyDescriptor.getWriteMethod();
        invokeMethod(writeMethod, obj, value);
    }

    /**
     * Gets bean prop map.
     *
     * @param object           the object
     * @param ignoreAnnotation the ignore annotation
     * @param onlySelf         the only self
     * @return the bean prop map
     */
    public static Map<String, Object> getBeanPropMap(Object object, Class<? extends Annotation> ignoreAnnotation, boolean onlySelf) {
        Map<String, Object> propMap = new HashMap<String, Object>();
        PropertyDescriptor[] propertyDescriptors = onlySelf ? getSelfPropertyDescriptors(object.getClass()) : getPropertyDescriptors(object.getClass());
        if (propertyDescriptors == null) {
            return propMap;
        }
        for (PropertyDescriptor pd : propertyDescriptors) {
            Method readMethod = pd.getReadMethod();
            if (readMethod == null || (ignoreAnnotation != null && readMethod.getAnnotation(ignoreAnnotation) != null)) {
                continue;
            }

            Object value = invokeMethod(readMethod, object);
            propMap.put(pd.getName(), value);
        }
        return propMap;
    }

    /**
     * Gets bean prop map.
     *
     * @param object the object
     * @return the bean prop map
     */
    public static Map<String, Object> getBeanPropMap(Object object) {
        return getBeanPropMap(object, null, false);
    }

    /**
     * Gets bean prop map.
     *
     * @param object           the object
     * @param ignoreAnnotation the ignore annotation
     * @return the bean prop map
     */
    public static Map<String, Object> getBeanPropMap(Object object, Class<? extends Annotation> ignoreAnnotation) {
        return getBeanPropMap(object, ignoreAnnotation, false);
    }

    /**
     * bean属性转换为map
     *
     * @param object
     * @return
     */
    public static Map<String, Object> getSelfBeanPropMap(Object object, Class<? extends Annotation> ignoreAnnotation) {
        return getBeanPropMap(object, ignoreAnnotation, true);
    }

    public static Method getMethod(Class<?> beanClazz, String methodName, Class<?>[] paramTypes) {
        try {
            return beanClazz.getMethod(methodName, paramTypes);
        } catch (NoSuchMethodException e) {
            throw new SonsureException("获取Method失败:" + methodName, e);
        }
    }

    /**
     * invokeMethod
     *
     * @param method
     * @param bean
     * @param value
     */
    public static Object invokeMethod(Method method, Object bean, Object... value) {
        try {
            methodAccessible(method);
            Object[] parameters = new Object[value.length];
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < value.length; i++) {
                if (parameterTypes[i] == value[i].getClass()) {
                    parameters[i] = value[i];
                } else if (parameterTypes[i] == Boolean.class || parameterTypes[i] == boolean.class) {
                    parameters[i] = BooleanUtils.toBoolean(String.valueOf(value[i]));
                } else if (parameterTypes[i] == Integer.class || parameterTypes[i] == int.class) {
                    parameters[i] = Integer.valueOf(String.valueOf(value[i]));
                } else if (parameterTypes[i] == Long.class || parameterTypes[i] == long.class) {
                    parameters[i] = Long.valueOf(String.valueOf(value[i]));
                } else {
                    parameters[i] = value[i];
                }
            }
            return method.invoke(bean, parameters);
        } catch (Exception e) {
            throw new SonsureException("执行invokeMethod失败:" + (method == null ? "null" : method.getName()), e);
        }
    }

    /**
     * invokeMethod
     *
     * @param method
     * @param bean
     */
    public static Object invokeMethod(Method method, Object bean) {
        try {
            methodAccessible(method);
            return method.invoke(bean);
        } catch (Exception e) {
            throw new SonsureException("执行invokeMethod失败:" + (method == null ? "null" : method.getName()), e);
        }
    }

    /**
     * 设置method访问权限
     *
     * @param method
     */
    public static void methodAccessible(Method method) {
        if (!Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
            method.setAccessible(true);
        }
    }

    /**
     * 初始化实例
     *
     * @param clazz
     * @return
     */
    public static Object newInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new SonsureException("根据class创建实例失败:" + (clazz == null ? "null" : clazz.getName()), e);
        }
    }

    /**
     * 初始化实例
     *
     * @param clazz
     * @return
     */
    public static Object newInstance(String clazz) {

        try {
            Class<?> loadClass = getDefaultClassLoader().loadClass(clazz);
            return loadClass.newInstance();
        } catch (Exception e) {
            throw new SonsureException("根据class创建实例失败:" + clazz, e);
        }
    }

    /**
     * 加载类
     *
     * @param clazz
     * @return
     */
    public static Class<?> loadClass(String clazz) {
        try {
            Class<?> loadClass = getDefaultClassLoader().loadClass(clazz);
            return loadClass;
        } catch (Exception e) {
            throw new SonsureException("根据class名称加载class失败:" + clazz, e);
        }
    }

    /**
     * 将value的数据类型转换到实际目标类型
     *
     * @param value
     * @return
     */
    public static Object toTargetTypeValue(Object value, Class<?> targetType) {
        String typeName = targetType.getName();
        if (StringUtils.equals(typeName, boolean.class.getName())
                || StringUtils.equals(typeName, Boolean.class.getName())) {
            return Boolean.valueOf(value.toString());
        }
        if (StringUtils.equals(typeName, int.class.getName()) || StringUtils.equals(typeName, Integer.class.getName())) {
            return Integer.valueOf(value.toString());
        }
        if (StringUtils.equals(typeName, long.class.getName()) || StringUtils.equals(typeName, Long.class.getName())) {
            return Long.valueOf(value.toString());
        }
        if (StringUtils.equals(typeName, short.class.getName()) || StringUtils.equals(typeName, Short.class.getName())) {
            return Short.valueOf(value.toString());
        }
        if (StringUtils.equals(typeName, float.class.getName()) || StringUtils.equals(typeName, Float.class.getName())) {
            return Float.valueOf(value.toString());
        }
        if (StringUtils.equals(typeName, double.class.getName())
                || StringUtils.equals(typeName, Double.class.getName())) {
            return Double.valueOf(value.toString());
        }
        if (StringUtils.equals(typeName, byte.class.getName()) || StringUtils.equals(typeName, Byte.class.getName())) {
            return Byte.valueOf(value.toString());
        }
        return value;
    }

    /**
     * Is model type boolean.
     *
     * @param thisType the target type
     * @return the boolean
     */
    public static boolean isModelType(Class<?> thisType) {
        return isTargetType(thisType, Model.class);
    }

    /**
     * Is target type boolean. support osgi
     *
     * @param thisType   the this type
     * @param targetType the target type
     * @return the boolean
     */
    public static boolean isTargetType(Class<?> thisType, Class<?> targetType) {
        final ArrayList<Class<?>> types = new ArrayList<>();
        getSuperTypes(thisType, types);
        for (Class<?> tp : types) {
            if (targetType.getName().equals(tp.getName())) {
                return true;
            }
        }
        return false;
    }

    private static void getSuperTypes(Class<?> cls, List<Class<?>> superTypes) {
        getInterfaces(cls, superTypes);
        getSuperClasses(cls, superTypes);
    }

    private static void getSuperClasses(Class<?> cls, List<Class<?>> superClasses) {
        final Class<?> superclass = cls.getSuperclass();
        if (superclass != null) {
            getSuperClasses(superclass, superClasses);
            superClasses.add(superclass);
        }
    }

    public static void getInterfaces(Class<?> cls, List<Class<?>> interfaces) {
        final Class<?>[] ifs = cls.getInterfaces();
        if (ifs.length == 0) {
            return;
        }
        for (Class<?> anIf : ifs) {
            getInterfaces(anIf, interfaces);
            interfaces.add(anIf);
        }
    }

    /**
     * 当前线程的classLoader
     *
     * @return
     */
    public static ClassLoader getDefaultClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
