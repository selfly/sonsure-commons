/*
 * Copyright (c) 2020. www.sonsure.com Inc. All rights reserved.
 * You may obtain more information at
 *
 *   http://www.sonsure.com
 *
 * Designed By Selfly Lee (selfly@live.com)
 */

package com.sonsure.commons.validation;

import com.sonsure.commons.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liyd
 * @date 17/2/13
 */
public class StringValidator implements Validator {

    public static final String[] NOT_BLANK = {PREFIX + "not.blank", "不能为空"};

    public static final String[] NOT_EMPTY = {PREFIX + "not.empty", "不能为空"};

    public static final String[] MUST_EQ = {PREFIX + "must.eq", "必须相同"};

    public static final String[] MUST_EQ_IGNORE_CASE = {PREFIX + "must.eq.ignore.case", "必须相同"};

    public static final String[] NOT_EQ = {PREFIX + "not.eq", "不能等于{0}"};

    public static final String[] MIN_LENGTH = {PREFIX + "min.length", "允许最小长度为{0}"};

    public static final String[] MAX_LENGTH = {PREFIX + "max.length", "允许最大长度为{0}"};

    public static final String[] EQ_LENGTH = {PREFIX + "eq.length", "长度必须为{0}"};


    private final String type;

    public StringValidator(String type) {
        this.type = type;
    }

    @Override
    public ValidatorResult validate(Object value, String validateName) {

        ValidatorResult validatorResult = new ValidatorResult(true);
        if (StringUtils.equals(type, NOT_BLANK[0])) {
            validatorResult.setSuccess(StringUtils.isNotBlank((String) value));
            validatorResult.setCode(NOT_BLANK[0]);
            validatorResult.setMessage(validateName + NOT_BLANK[1]);
            return validatorResult;
        } else if (StringUtils.equals(type, NOT_EMPTY[0])) {
            validatorResult.setSuccess(StringUtils.isNotEmpty((String) value));
            validatorResult.setCode(NOT_EMPTY[0]);
            validatorResult.setMessage(validateName + NOT_EMPTY[1]);
            return validatorResult;
        } else if (StringUtils.equals(type, MUST_EQ[0])) {
            Object[] values = (Object[]) value;
            validatorResult.setSuccess(StringUtils.equals((String) values[0], (String) values[1]));
            validatorResult.setCode(MUST_EQ[0]);
            validatorResult.setMessage(validateName + MUST_EQ[1]);
            return validatorResult;
        } else if (StringUtils.equals(type, MUST_EQ_IGNORE_CASE[0])) {
            Object[] values = (Object[]) value;
            validatorResult.setSuccess(StringUtils.equalsIgnoreCase((String) values[0], (String) values[1]));
            validatorResult.setCode(MUST_EQ_IGNORE_CASE[0]);
            validatorResult.setMessage(validateName + MUST_EQ_IGNORE_CASE[1]);
            return validatorResult;
        } else if (StringUtils.equals(type, MIN_LENGTH[0])) {
            Object[] values = (Object[]) value;
            validatorResult.setSuccess(StringUtils.length((String) values[0]) >= (Integer) values[1]);
            validatorResult.setCode(MIN_LENGTH[0]);
            validatorResult.setMessage(validateName + MIN_LENGTH[1]);
            return validatorResult;
        } else if (StringUtils.equals(type, MAX_LENGTH[0])) {
            Object[] values = (Object[]) value;
            validatorResult.setSuccess(StringUtils.length((String) values[0]) <= (Integer) values[1]);
            validatorResult.setCode(MAX_LENGTH[0]);
            validatorResult.setMessage(validateName + MAX_LENGTH[1]);
            return validatorResult;
        } else if (StringUtils.equals(type, EQ_LENGTH[0])) {
            Object[] values = (Object[]) value;
            validatorResult.setSuccess(StringUtils.length((String) values[0]) == (Integer) values[1]);
            validatorResult.setCode(EQ_LENGTH[0]);
            validatorResult.setMessage(validateName + EQ_LENGTH[1]);
            return validatorResult;
        } else if (StringUtils.equals(type, NOT_EQ[0])) {
            Object[] values = (Object[]) value;
            validatorResult.setSuccess(!StringUtils.equals((String) values[0], (String) values[1]));
            validatorResult.setCode(NOT_EQ[0]);
            validatorResult.setMessage(validateName + NOT_EQ[1]);
            return validatorResult;
        } else {
            throw new ValidationException("不支持的校验");
        }
    }
}
