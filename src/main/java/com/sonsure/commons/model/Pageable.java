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
