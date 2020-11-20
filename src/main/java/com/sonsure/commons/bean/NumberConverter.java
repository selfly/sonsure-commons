/*
 * Copyright (c) 2020. www.sonsure.com Inc. All rights reserved.
 * You may obtain more information at
 *
 *   http://www.sonsure.com
 *
 * Designed By Selfly Lee (selfly@live.com)
 */

package com.sonsure.commons.bean;

import java.util.Arrays;
import java.util.List;

/**
 * @author liyd
 * @date 16/4/27
 */
public class NumberConverter implements TypeConverter {

    private static final List<Class<?>> SUPPORT_CLASSES = Arrays.asList(byte.class, short.class, int.class, long.class, float.class, double.class);

    @Override
    public boolean isSupport(Class<?> sourceClass, Class<?> targetClass, String fieldName) {
        return sourceClass != targetClass && (Number.class.isAssignableFrom(sourceClass) || SUPPORT_CLASSES.contains(sourceClass))
                && (Number.class.isAssignableFrom(targetClass) || SUPPORT_CLASSES.contains(targetClass));
    }

    @Override
    public Object convert(Class<?> sourceClass, Class<?> targetClass, Object value) {

        //只做最常见的几种转换
        final Number number = (Number) value;
        if (targetClass == Byte.class || targetClass == byte.class) {
            return number.byteValue();
        } else if (targetClass == Short.class || targetClass == short.class) {
            return number.shortValue();
        } else if (targetClass == Integer.class || targetClass == int.class) {
            return number.intValue();
        } else if (targetClass == Long.class || targetClass == long.class) {
            return number.longValue();
        } else if (targetClass == Float.class || targetClass == float.class) {
            return number.floatValue();
        } else if (targetClass == Double.class || targetClass == double.class) {
            return number.doubleValue();
        }
        return value;
    }
}
