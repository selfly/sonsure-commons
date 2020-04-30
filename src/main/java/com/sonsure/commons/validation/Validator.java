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
public interface Validator {

    /***
     * 值校验
     *
     * @param value
     * @return
     */
    boolean validate(Object value);

    /**
     * 校验代码
     * 
     * @return
     */
    String validateCode();

    /**
     * 校验失败错误信息
     *
     * @param value
     * @param validateName
     * @return
     */
    String validateMsg(Object value, String validateName);
}
