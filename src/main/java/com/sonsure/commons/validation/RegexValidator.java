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
 * Created by liyd on 17/1/24.
 */
public class RegexValidator implements Validator {

    @Override
    public boolean validate(Object value) {
        String[] values = (String[]) value;
        return values[0].matches(values[1]);
    }

    @Override
    public String validateCode() {
        return "regex.error";
    }

    @Override
    public String validateMsg(Object value, String validateName) {
        return validateName + "格式不正确";
    }
}
