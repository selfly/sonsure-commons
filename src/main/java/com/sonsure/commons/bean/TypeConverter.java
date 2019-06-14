package com.sonsure.commons.bean;

/**
 * 数据类型转换接口
 * <p/>
 * User: liyd
 * Date: 13-5-9 下午12:04
 * version $Id: TypeConverter.java, v 0.1 Exp $
 */
public interface TypeConverter {


    /**
     * 是否支持转换
     *
     * @param sourceClass the source class
     * @param targetClass the target class
     * @param fieldName   the field name
     * @return boolean boolean
     */
    boolean isSupport(Class<?> sourceClass, Class<?> targetClass, String fieldName);

    /**
     * 转换操作
     *
     * @param sourceClass the source class
     * @param targetClass the target class
     * @param value       The input value to be converted
     * @return The converted value
     */
    Object convert(Class<?> sourceClass, Class<?> targetClass, Object value);

}
