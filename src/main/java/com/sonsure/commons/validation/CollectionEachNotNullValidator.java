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
 * Created by liyd on 17/1/24.
 */
public class CollectionEachNotNullValidator implements Validator {

    public boolean validate(Object value) {
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

    public String validateCode() {
        return "collection.each.not.null";
    }

    public String validateMsg(Object value, String validateName) {
        return validateName + "中的每个元素都不能为空";
    }
}
