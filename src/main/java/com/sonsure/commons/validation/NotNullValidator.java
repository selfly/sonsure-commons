/*
 * Copyright (c) 2020. www.sonsure.com Inc. All rights reserved.
 * You may obtain more information at
 *
 *   http://www.sonsure.com
 *
 * Designed By Selfly Lee (selfly@live.com)
 */

package com.sonsure.commons.validation;

/**
 * Created by liyd on 17/1/23.
 */
public class NotNullValidator implements Validator {

    public boolean validate(Object obj) {
        return obj != null;
    }

    public String validateCode() {
        return "not.null";
    }

    public String validateMsg(Object value, String validateName) {
        return validateName + "不能为空";
    }
}
