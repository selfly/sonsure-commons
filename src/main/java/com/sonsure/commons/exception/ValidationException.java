/*
 * Copyright (c) 2020. www.sonsure.com Inc. All rights reserved.
 * You may obtain more information at
 *
 *   http://www.sonsure.com
 *
 * Designed By Selfly Lee (selfly@live.com)
 */

package com.sonsure.commons.exception;

import com.sonsure.commons.enums.IEnum;

/**
 * Created by liyd on 17/7/10.
 */
public class ValidationException extends SonsureException {

    private static final long serialVersionUID = 116371972019482410L;

    public ValidationException(IEnum e) {
        super(e);
    }

    public ValidationException(String message, Throwable e) {
        super(message, e);
    }

    public ValidationException(IEnum msgEnum, Throwable e) {
        super(msgEnum, e);
    }

    public ValidationException(Throwable e) {
        super(e);
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String code, String message) {
        super(code, message);
    }

}
