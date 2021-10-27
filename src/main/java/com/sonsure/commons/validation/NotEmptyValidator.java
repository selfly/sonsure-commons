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
 * @date 17/1/23
 */
public class NotEmptyValidator implements Validator {

    private static final String[] NOT_EMPTY = {PREFIX + "not.empty", "不能为空"};

    @Override
    public ValidatorResult validate(Object obj, String validateName) {
        ValidatorResult validatorResult = new ValidatorResult(false);
        if (obj == null) {
            return validatorResult;
        }
        if (obj instanceof Collection) {
            Collection<?> cts = (Collection<?>) obj;
            validatorResult.setSuccess(!cts.isEmpty());
        } else if (obj.getClass().isArray()) {
            validatorResult.setSuccess(((Object[]) obj).length > 0);
        } else if (obj instanceof String) {
            validatorResult.setSuccess(!((String) obj).isEmpty());
        } else {
            throw new UnsupportedOperationException("不支持的参数类型");
        }
        validatorResult.setCode(NOT_EMPTY[0]);
        validatorResult.setMessage(validateName + NOT_EMPTY[1]);
        return validatorResult;
    }
}
