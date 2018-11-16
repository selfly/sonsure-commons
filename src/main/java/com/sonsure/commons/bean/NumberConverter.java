package com.sonsure.commons.bean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liyd on 16/4/27.
 */
public class NumberConverter implements TypeConverter {

    private List<Class<?>> supportClasses;

    public NumberConverter() {
        supportClasses = Arrays.asList(new Class<?>[]{BigDecimal.class, Long.class, Integer.class});
    }


    @Override
    public boolean isSupport(Class<?> sourceClass, Class<?> targetClass) {
        if (supportClasses.contains(sourceClass) && supportClasses.contains(targetClass)) {
            return true;
        }
        return false;
    }

    @Override
    public Object convert(Class<?> sourceClass, Class<?> targetClass, Object value) {

        //只做最常见的几种转换
        if (sourceClass == BigDecimal.class) {
            BigDecimal bigDecimal = (BigDecimal) value;
            if (targetClass == Integer.class) {
                return bigDecimal.intValue();
            } else if (targetClass == Long.class) {
                return bigDecimal.longValue();
            } else if (targetClass == Double.class) {
                return bigDecimal.doubleValue();
            } else if (targetClass == Float.class) {
                return bigDecimal.floatValue();
            }
        } else if (sourceClass == Long.class) {
            if (targetClass == Integer.class) {
                return ((Long) value).intValue();
            }
        } else if (sourceClass == Integer.class) {

            if (targetClass == Long.class) {
                return Long.valueOf((Integer) value);
            }
        }
        return value;
    }
}
