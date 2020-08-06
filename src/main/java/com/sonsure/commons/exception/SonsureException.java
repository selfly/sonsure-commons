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
import com.sonsure.commons.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 自定义异常类
 * <p/>
 *
 * @author liyd
 * @date 6/27/14
 */
public class SonsureException extends RuntimeException {

    private static final long serialVersionUID = 3731036212843506314L;

    /**
     * 生成错误码前缀
     */
    private static final String ERROR_CODE_PREFIX = "sonsure.error.";

    /**
     * Exception code
     */
    protected String resultCode = "UN_KNOWN_EXCEPTION";

    /**
     * Exception message
     */
    protected String resultMsg = "未知异常";

    /**
     * Instantiates a new KtanxException.
     *
     * @param e the e
     */
    public SonsureException(IEnum e) {
        this(e.getCode(), e.getDesc());
    }

    public SonsureException(String message, Throwable e) {
        this(null, message, e);
    }

    public SonsureException(IEnum iEnum, Throwable e) {
        this(iEnum.getCode(), iEnum.getDesc(), e);
    }

    /**
     * Instantiates a new KtanxException.
     *
     * @param e the e
     */
    public SonsureException(Throwable e) {
        this(null, e.getMessage(), e);
    }

    /**
     * Constructor
     *
     * @param message the message
     */
    public SonsureException(String message) {
        this(null, message);
    }

    /**
     * Constructor
     *
     * @param code    the code
     * @param message the message
     */
    public SonsureException(String code, String message) {
        super(message);
        this.resultCode = StringUtils.isBlank(code) ? new StringBuilder(ERROR_CODE_PREFIX).append(UUIDUtils.getUUID16(message.getBytes())).toString() : code;
        this.resultMsg = message;
    }

    /**
     * Constructor
     *
     * @param code    the code
     * @param message the message
     * @param e       the e
     */
    public SonsureException(String code, String message, Throwable e) {
        super(message, e);
        this.resultCode = StringUtils.isBlank(code) ? new StringBuilder(ERROR_CODE_PREFIX).append(UUIDUtils.getUUID16(message.getBytes())).toString() : code;
        this.resultMsg = message;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

}
