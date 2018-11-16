package com.sonsure.commons.model;


import com.sonsure.commons.exception.SonsureException;
import com.sonsure.commons.utils.ClassUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Model extends Pageable {

    private static final long serialVersionUID = 3793689719542455985L;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 名称
     */
    private String typeName;

    /**
     * 排序方式
     */
    private String typeSort;

    /**
     * 数据map
     */
    private Map<String, Object> properties;

    public Model() {
        properties = new LinkedHashMap<>();
    }

    /**
     * 放入数据
     *
     * @param name  the name
     * @param value the value
     */
    public void addProperty(String name, Object value) {
        properties.put(name, value);
    }

    /**
     * 放入数据
     *
     * @param propertyMap the map
     */
    public void addProperties(Map<String, ?> propertyMap) {
        if (propertyMap == null) {
            return;
        }
        properties.putAll(propertyMap);
    }

    public void removeProperty(String name) {
        this.properties.remove(name);
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public Object getProperty(String key) {
        return this.getProperty(key, Object.class);
    }

    /**
     * 获取数据
     *
     * @param <T>         the type parameter
     * @param name        the name
     * @param elementType the element type
     * @return t t
     */
    @SuppressWarnings("unchecked")
    public <T> T getProperty(String name, Class<T> elementType) {
        if (this.properties == null) {
            return null;
        }
        Object obj = this.properties.get(name);
        if (obj == null) {
            return null;
        }
        if (!elementType.isAssignableFrom(obj.getClass())) {
            throw new SonsureException(
                    "类型不匹配。expected:" + elementType.getName() + ",actual:" + obj.getClass());
        }
        return (T) obj;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return this.getProperty(key);
    }

    /**
     * 获取指定属性值
     *
     * @param fieldName
     * @return
     */
    public Object getFieldValue(String fieldName) {
        return ClassUtils.getFieldValue(this.getClass(), this, fieldName);
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeSort() {
        return typeSort;
    }

    public void setTypeSort(String typeSort) {
        this.typeSort = typeSort;
    }
}
