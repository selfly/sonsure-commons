/*
 * Copyright (c) 2020. www.sonsure.com Inc. All rights reserved.
 * You may obtain more information at
 *
 *   http://www.sonsure.com
 *
 * Designed By Selfly Lee (selfly@live.com)
 */

package com.sonsure.commons.bean;

import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.util.*;

public class BeanFieldCache {

    /**
     * 缓存的BeanInfo信息，一个Bean根据stopClass的不同可能存有多个BeanInfo
     * 子map根据stopClass进行缓存
     */
    public static final Map<Class<?>, Map<Class<?>, Object>> classCache = Collections
            .synchronizedMap(new WeakHashMap<Class<?>, Map<Class<?>, Object>>());

    /**
     * 类的属性信息，key为属性名
     */
    private final Map<String, Field> beanFieldCache;

    /**
     * Instantiates a new bean field cache.
     *
     * @param beanClass the bean class
     * @param stopClass the stop class
     */
    private BeanFieldCache(Class<?> beanClass, Class<?> stopClass) {

        List<Field> fieldList = new ArrayList<>();

        Class<?> parentClass = beanClass;
        while (parentClass != stopClass) {
            //当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(parentClass.getDeclaredFields()));
            parentClass = parentClass.getSuperclass();
        }
        this.beanFieldCache = new LinkedHashMap<>();
        for (Field field : fieldList) {
            beanFieldCache.put(field.getName(), field);
        }
    }


    /**
     * For class.
     *
     * @param beanClass the bean class
     * @return the field cache
     */
    public static BeanFieldCache forClass(Class<?> beanClass) {
        return forClass(beanClass, null);
    }

    /**
     * For class.
     *
     * @param beanClass the bean class
     * @param stopClass the stop class
     * @return the introspection cache
     */
    public static BeanFieldCache forClass(Class<?> beanClass, Class<?> stopClass) {

        BeanFieldCache beanFieldCache;

        Map<Class<?>, Object> map = classCache.get(beanClass);
        if (map == null) {
            map = new HashMap<>();
            classCache.put(beanClass, map);
        }
        Class<?> stopCls = stopClass == null ? beanClass : stopClass;
        Object value = map.get(stopCls);
        if (value instanceof Reference) {
            @SuppressWarnings("rawtypes")
            Reference ref = (Reference) value;
            beanFieldCache = (BeanFieldCache) ref.get();
        } else {
            beanFieldCache = (BeanFieldCache) value;
        }
        if (beanFieldCache == null) {
            beanFieldCache = new BeanFieldCache(beanClass, stopClass);
            map.put(stopCls, beanFieldCache);
        }
        return beanFieldCache;
    }

    /**
     * Get bean fields.
     *
     * @return the  bean fields [ ]
     */
    public Field[] getBeanFields() {
        Field[] fields = new Field[this.beanFieldCache.size()];
        int i = 0;
        for (Field field : this.beanFieldCache.values()) {
            fields[i] = field;
            i++;
        }
        return fields;
    }

    /**
     * Get bean field
     *
     * @param name the name
     * @return the property descriptor
     */
    public Field getBeanField(String name) {
        Field field = this.beanFieldCache.get(name);
        return field;
    }
}
