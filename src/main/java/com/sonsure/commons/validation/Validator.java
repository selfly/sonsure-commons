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
 * @author liyd
 * @date 17/1/23
 */
public interface Validator {

    String PREFIX = "ss.";

    /***
     * 值校验
     *
     * @param value the value
     * @param validateName the validate name
     * @return boolean boolean
     */
    ValidatorResult validate(Object value, String validateName);
}
