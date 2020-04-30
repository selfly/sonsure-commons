/*
 * Copyright (c) 2020. www.sonsure.com Inc. All rights reserved.
 * You may obtain more information at
 *
 *   http://www.sonsure.com
 *
 * Designed By Selfly Lee (selfly@live.com)
 */

package com.sonsure.commons.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 分页等常用信息存储
 * <p/>
 * Created by liyd on 6/26/14.
 */
public class Pageable implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4060766214127186912L;

    /**
     * 供某些情况下分页查询快速设置的值，代码中应避免setPageSize(Integer.MAX_VALUE)
     */
    public static final int NON_PAGE_CAREFUL_SIZE = 100;
    public static final int NON_PAGE_CASUAL_SIZE = 500;

    /**
     * 每页显示条数
     */
    private int pageSize = 20;

    /**
     * 当前页码
     */
    private int pageNum = 1;

    /**
     * 简化前端参数
     *
     * @param pageNum
     */
    public void setPn(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPs(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
