/*
 * Copyright (c) 2020. www.sonsure.com Inc. All rights reserved.
 * You may obtain more information at
 *
 *   http://www.sonsure.com
 *
 * Designed By Selfly Lee (selfly@live.com)
 */

package com.sonsure.commons.bean;


import com.sonsure.commons.utils.EnumUtils;

/**
 * IEnum与String互转
 * <p>
 *
 * @author liyd
 * @date 8/10/14
 */
public class IEnumStringConverter implements TypeConverter {


    @Override
    public boolean isSupport(Class<?> sourceClass, Class<?> targetClass, String fieldName) {
        if (EnumUtils.isIEnumType(sourceClass) && targetClass == String.class) {
            return true;
        }
        if (sourceClass == String.class && EnumUtils.isIEnumType(targetClass)) {
            return true;
        }
        return false;
    }

    @Override
    public Object convert(Class<?> sourceClass, Class<?> targetClass, Object value) {

        if (EnumUtils.isIEnumType(sourceClass) && targetClass == String.class) {
            return EnumUtils.getIEnumVal(sourceClass, value);
        }
        if (sourceClass == String.class && EnumUtils.isIEnumType(targetClass)) {
            return EnumUtils.getIEnum((Class<? extends Enum>) targetClass, (String) value);
        }
        return value;
    }

}
