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

    public Page() {

    }

    public Page(Pagination pagination) {
        this(null, pagination);
    }

    public Page(List<T> list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }

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
