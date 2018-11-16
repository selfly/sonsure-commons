package com.sonsure.commons.bean;

import java.util.List;
import java.util.Map;

/**
 * Java Bean 对象转换器
 * <p/>
 * User: liyd
 * Date: 13-5-8 下午4:29
 * version $Id: BeanConverter.java, v 0.1 Exp $
 */
public class BeanKit {

    /**
     * 默认实例
     */
    private static final BeanKitInst INSTANCE = new BeanKitInst();

    public static BeanKitInst getInstance() {
        return new BeanKitInst();
    }

    /**
     * 拷贝属性值
     *
     * @param orig
     * @param dest
     */
    public static void copyFields(Object orig, Object dest) {
        INSTANCE.copyFields(orig, dest);
    }

    /**
     * map转为bean，key名为下划线命名方式
     *
     * @param <T>       the type parameter
     * @param mapList   the map list
     * @param beanClass the bean class
     * @return t list
     */
    public static <T> List<T> underlineKeyMapToBean(List<Map<String, Object>> mapList, Class<T> beanClass) {
        return INSTANCE.underlineKeyMapToBean(mapList, beanClass);
    }

    /**
     * map转为bean，key名为bean属性名
     *
     * @param <T>       the type parameter
     * @param mapList   the map list
     * @param beanClass the bean class
     * @return t list
     */
    public static <T> List<T> mapToBean(List<Map<String, Object>> mapList, Class<T> beanClass) {
        return INSTANCE.mapToBean(mapList, beanClass);
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
    public static <T> List<T> mapToBean(List<Map<String, Object>> mapList, Class<T> beanClass, Character delimiter) {
        return INSTANCE.mapToBean(mapList, beanClass, delimiter);
    }

    /**
     * map转为bean
     *
     * @param map       the map
     * @param beanClass the bean class
     * @return t
     */
    public static <T> T underlineKeyMapToBean(Map<String, Object> map, Class<T> beanClass) {
        return INSTANCE.underlineKeyMapToBean(map, beanClass);
    }

    /**
     * map转为bean
     *
     * @param map       the map
     * @param beanClass the bean class
     * @return t
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass) {
        return INSTANCE.mapToBean(map, beanClass);
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
    public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass, Character delimiter) {
        return INSTANCE.mapToBean(map, beanClass, delimiter);
    }

    /**
     * 拷贝属性
     *
     * @param clazz the clazz
     * @param list  the list
     * @return the page list
     */
    public static <T> List<T> copyProperties(Class<T> clazz, List<?> list) {
        return INSTANCE.copyProperties(clazz, list);
    }

    /**
     * 拷贝属性
     *
     * @param clazz            the clazz
     * @param list             the list
     * @param ignoreProperties the ignore properties
     * @return the page list
     */
    public static <T> List<T> copyProperties(Class<T> clazz, List<?> list, String[] ignoreProperties) {

        return INSTANCE.copyProperties(clazz, list, ignoreProperties);
    }

    /**
     * 单个对象拷贝
     *
     * @param target 目标对象
     * @param source 源对象
     * @return 转换后的目标对象
     */
    public static <T> T copyProperties(T target, Object source) {
        return INSTANCE.copyProperties(target, source);
    }

    /**
     * 单个对象转换
     *
     * @param target           目标对象
     * @param source           源对象
     * @param ignoreProperties 需要过滤的属性
     * @return 转换后的目标对象
     */
    public static <T> T copyProperties(T target, Object source, String[] ignoreProperties) {

        return INSTANCE.copyProperties(target, source, ignoreProperties);
    }
}
