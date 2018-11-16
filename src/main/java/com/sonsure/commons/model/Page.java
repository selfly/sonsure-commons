package com.sonsure.commons.model;

import java.util.List;

/**
 * 分页对象
 *
 * @param <T>
 */
public class Page<T> {

    private List<T> list;

    private Pagination pagination;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
