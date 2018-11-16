package com.sonsure.commons.xml;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyd on 16/12/27.
 */
public class XmlNode {

    private String name;

    private String text;

    private Map<String, String> attributes;

    private List<XmlNode> childNodes;

    public XmlNode() {
        this(null);
    }

    public XmlNode(String nodeName) {
        this.name = nodeName;
        this.attributes = new HashMap<String, String>();
        this.childNodes = new ArrayList<XmlNode>();
    }

    /**
     * 添加属性
     *
     * @param name
     * @param value
     */
    public void addAttribute(String name, String value) {
        this.attributes.put(name, value);
    }

    /**
     * 获取属性
     *
     * @param attributeName
     * @return
     */
    public String getAttribute(String attributeName) {
        return attributes == null ? "" : attributes.get(attributeName);
    }

    /**
     * 获取子节点
     *
     * @param nodeName
     * @return
     */
    public XmlNode getSingleChildNode(String nodeName) {
        for (XmlNode childNode : this.childNodes) {
            if (StringUtils.equals(nodeName, childNode.getName())) {
                return childNode;
            }
        }
        return null;
    }

    /**
     * 获取子节点
     *
     * @param nodeName
     * @return
     */
    public List<XmlNode> getChildNodes(String nodeName) {

        List<XmlNode> nodes = new ArrayList<>();
        for (XmlNode childNode : this.childNodes) {
            if (StringUtils.equals(nodeName, childNode.getName())) {
                nodes.add(childNode);
            }
        }
        return nodes;
    }

    /**
     * 获取子节点内容
     *
     * @param nodeName
     * @return
     */
    public String getSingleChildNodeText(String nodeName) {
        XmlNode childNode = getSingleChildNode(nodeName);
        return childNode == null ? "" : childNode.getText();
    }

    /**
     * 添加子节点
     *
     * @param xmlNode
     */
    public void addChildNode(XmlNode xmlNode) {
        childNodes.add(xmlNode);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public List<XmlNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<XmlNode> childNodes) {
        this.childNodes = childNodes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
