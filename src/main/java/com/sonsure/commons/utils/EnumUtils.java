/*
 * Copyright (c) 2020. www.sonsure.com Inc. All rights reserved.
 * You may obtain more information at
 *
 *   http://www.sonsure.com
 *
 * Designed By Selfly Lee (selfly@live.com)
 */

package com.sonsure.commons.utils;

import com.sonsure.commons.enums.IEnum;
import com.sonsure.commons.exception.SonsureException;

/**
 * 枚举辅助类
 * <p>
 * User: liyd
 * Date: 14-1-25
 * Time: 上午10:17
 */
public final class EnumUtils {

    /**
     * 获取枚举的所有属性
     *
     * @param clazz
     * @return
     */
    public static IEnum[] getEnums(Class<?> clazz) {
        if (IEnum.class.isAssignableFrom(clazz)) {
            Object[] enumConstants = clazz.getEnumConstants();
            return (IEnum[]) enumConstants;
        }
        return null;
    }

    /**
     * 获取枚举的所有属性
     *
     * @param enumClass
     * @return
     */
    public static IEnum[] getEnums(String enumClass) {
        try {
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(enumClass);
            return getEnums(clazz);
        } catch (ClassNotFoundException e) {
            throw new SonsureException("加载枚举类失败", e);
        }
    }

    /**
     * 获取枚举的所有属性
     *
     * @param clazz the clazz
     * @param code  the code
     * @return enum
     */
    @SuppressWarnings("unchecked")
    public static <T> T getEnum(Class<T> clazz, String code) {

        T[] enumConstants = clazz.getEnumConstants();
        if (IEnum.class.isAssignableFrom(clazz)) {
            IEnum[] iEnums = (IEnum[]) enumConstants;
            for (IEnum ienum : iEnums) {
                if (ienum.getCode().equalsIgnoreCase(code)) {
                    return (T) ienum;
                }
            }
        } else {
            for (T enumConstant : enumConstants) {
                if (((Enum) enumConstant).name().equals(code)) {
                    return enumConstant;
                }
            }
        }
        return null;
    }

    /**
     * 获取枚举的所有属性
     *
     * @param clazzName the clazzName
     * @param code      the code
     * @return enum
     */
    @SuppressWarnings("unchecked")
    public static <T> T getEnum(String clazzName, String code) {
        Class<?> clazz = ClassUtils.loadClass(clazzName);
        if (!IEnum.class.isAssignableFrom(clazz)) {
            return null;
        }
        IEnum[] enumConstants = (IEnum[]) clazz.getEnumConstants();
        for (IEnum enumConstant : enumConstants) {
            if (enumConstant.getCode().equalsIgnoreCase(code)) {
                return (T) enumConstant;
            }
        }
        return null;
    }

    public static String getEnumVal(String clazzName, String code) {
        Object anEnum = getEnum(clazzName, code);
        return anEnum == null ? code : ((IEnum) anEnum).getDesc();
    }
}
