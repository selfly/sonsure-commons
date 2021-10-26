/*
 * Copyright (c) 2020. www.sonsure.com Inc. All rights reserved.
 * You may obtain more information at
 *
 *   http://www.sonsure.com
 *
 * Designed By Selfly Lee (selfly@live.com)
 */

package com.sonsure.commons.validation;

import java.util.Collection;

/**
 * @author liyd
 * @date 17/1/24
 */
public class CollectionEachNotNullValidator implements Validator {

    public static final String[] COLLECTION_EACH_NOT_NULL = {PREFIX + "collection.each.not.null", "{0}的每个元素都不能为空"};

    @Override
    public ValidatorResult validate(Object value, String validateName) {
        if (value == null) {
            return false;
        }
        Collection<?> collection = (Collection<?>) value;
        for (Object obj : collection) {
            if (obj == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String validateCode() {
        return "collection.each.not.null";
    }

    @Override
    public String validateMsg(Object value, String validateName) {
        return validateName + "中的每个元素都不能为空";
    }
}
