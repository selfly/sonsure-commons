package com.sonsure.commons.xml;

import com.sonsure.commons.exception.SonsureException;
import com.sonsure.commons.spring.ClassPathResource;
import com.sonsure.commons.spring.Resource;
import com.sonsure.commons.utils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by liyd on 17/9/6.
 */
public final class XmlParser {

    /**
     * 解析xml
     *
     * @param resource
     * @return
     */
    public static XmlNode parse(Resource resource, String propertyFile) {

        Map<String, String> properties = null;
        if (StringUtils.isNotBlank(propertyFile)) {
            properties = PropertyUtils.getProperties(propertyFile);
        }
        Document document = readXml(resource, properties);
        return parse(document.asXML(), properties);
    }

    /**
     * 解析xml
     *
     * @param resource
     * @return
     */
    public static XmlNode parse(Resource resource) {
        Document document = readXml(resource);
        Element rootElement = document.getRootElement();
        return parseElement(rootElement, null);
    }

    /**
     * 解析xml
     *
     * @param xmlContent
     * @return
     */
    public static XmlNode parse(String xmlContent) {

        return parse(xmlContent, null);
    }

    /**
     * 解析xml
     *
     * @param xmlContent
     * @return
     */
    public static XmlNode parse(String xmlContent, Map<String, String> properties) {
        String xml = xmlContent;
        if (properties != null) {
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                String value = StringUtils.replace(entry.getValue(), "&amp", "&");
                value = StringUtils.replace(value, "&", "&amp;");
                xml = StringUtils.replace(xml, String.format("${%s}", entry.getKey()), value);
            }
        }
        Document document = readXml(xml);
        Element rootElement = document.getRootElement();
        XmlNode xmlNode = parseElement(rootElement, properties);
        return xmlNode;
    }

    /**
     * 解析xml元素
     *
     * @param element
     * @return
     */
    public static XmlNode parseElement(Element element, Map<String, String> properties) {

        XmlNode xmlNode = new XmlNode(element.getName());

        Iterator<Attribute> iterator = element.attributeIterator();
        while (iterator.hasNext()) {
            Attribute attribute = iterator.next();
            xmlNode.addAttribute(attribute.getName(), attribute.getValue());
        }

        Iterator<Element> iter = null;
        if (StringUtils.equals("include", element.getName())) {
            Attribute attr = element.attribute("file");
            Document document = readXml(new ClassPathResource(attr.getValue()), properties);
            Element rootElement = document.getRootElement();
            iter = rootElement.elementIterator();
        } else {
            iter = element.elementIterator();
        }

        while (iter.hasNext()) {
            Element next = iter.next();
            xmlNode.addChildNode(parseElement(next, properties));
        }
        xmlNode.setText(element.getTextTrim());
        return xmlNode;
    }

    /**
     * 解析xml元素
     *
     * @param element
     * @return
     */
    public static XmlNode parseElement(Element element) {
        return parseElement(element, null);
    }

    /**
     * 解析xml
     *
     * @param resource
     * @return
     */
    public static Document readXml(Resource resource) {
        return readXml(resource, null);
    }

    /**
     * 解析xml
     *
     * @param resource   the resource
     * @param properties the properties
     * @return document
     */
    public static Document readXml(Resource resource, Map<String, String> properties) {

        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new InputSource(resource.getInputStream()));
            if (properties == null || properties.isEmpty()) {
                return document;
            }
            String xml = document.asXML();
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                String value = StringUtils.replace(entry.getValue(), "&amp", "&");
                value = StringUtils.replace(value, "&", "&amp;");
                xml = StringUtils.replace(xml, String.format("${%s}", entry.getKey()), value);
            }
            return readXml(xml);
        } catch (Exception e) {
            throw new SonsureException(e);
        }
    }

    /**
     * 读取xml流为doc
     *
     * @param content
     * @return
     */
    public static Document readXml(String content) {
        try {
            return DocumentHelper.parseText(content);
        } catch (DocumentException e) {
            throw new SonsureException("读取xml文件失败", e);
        }
    }
}
