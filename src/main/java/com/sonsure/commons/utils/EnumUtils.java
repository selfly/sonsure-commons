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
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

/**
 * 枚举辅助类
 * <p>
 * User: liyd
 * Date: 14-1-25
 * Time: 上午10:17
 */
public final class EnumUtils {

    private static final String GET_CODE_METHOD = "getCode";

    /**
     * 转换
     * osgi
     *
     * @param <T>      the type parameter
     * @param enumType the enum type
     * @param source   the source
     * @return i enum
     */
    public static <T extends Enum> T getIEnum(Class<T> enumType, String source) {
        final Method getCode = ClassUtils.getMethod(enumType, GET_CODE_METHOD, new Class<?>[]{});
        final T[] enumConstants = enumType.getEnumConstants();
        for (T enumConstant : enumConstants) {
            final String value = (String) ClassUtils.invokeMethod(getCode, enumConstant);
            if (StringUtils.equals(source, value)) {
                return enumConstant;
            }
        }
        return null;
    }

    /**
     * Gets i enum val.
     *
     * @param enumType  the enum type
     * @param enumValue the enum value
     * @return the i enum val
     */
    public static String getIEnumVal(Class<?> enumType, Object enumValue) {
        final Method getCode = ClassUtils.getMethod(enumType, GET_CODE_METHOD, new Class<?>[]{});
        return (String) ClassUtils.invokeMethod(getCode, enumValue);
    }

    /**
     * Is i enum type boolean.
     * osgi
     *
     * @param thisType the target type
     * @return the boolean
     */
    public static boolean isIEnumType(Class<?> thisType) {
        return ClassUtils.isTargetType(thisType, IEnum.class);
    }

}
