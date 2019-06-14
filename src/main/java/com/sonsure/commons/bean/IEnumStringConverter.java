package com.sonsure.commons.bean;


import com.sonsure.commons.enums.IEnum;

/**
 * IEnum与String互转
 * <p>
 * Created by liyd on 8/10/14.
 */
public class IEnumStringConverter implements TypeConverter {


    @Override
    public boolean isSupport(Class<?> sourceClass, Class<?> targetClass, String fieldName) {
        if (IEnum.class.isAssignableFrom(sourceClass) && targetClass == String.class) {
            return true;
        }
        if (sourceClass == String.class && IEnum.class.isAssignableFrom(targetClass)) {
            return true;
        }
        return false;
    }

    public Object convert(Class<?> sourceClass, Class<?> targetClass, Object value) {

        if (IEnum.class.isAssignableFrom(sourceClass) && targetClass == String.class) {
            return ((IEnum) value).getCode();
        }
        if (sourceClass == String.class && IEnum.class.isAssignableFrom(targetClass)) {
            IEnum[] iEnums = (IEnum[]) targetClass.getEnumConstants();
            for (IEnum iEnum : iEnums) {
                if (iEnum.getCode().equals(String.valueOf(value))) {
                    return iEnum;
                }
            }
        }
        return value;
    }
}
