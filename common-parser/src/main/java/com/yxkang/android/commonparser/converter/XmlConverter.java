package com.yxkang.android.commonparser.converter;

import android.text.TextUtils;

import com.yxkang.android.commonparser.Converter;
import com.yxkang.android.commonparser.Reader;
import com.yxkang.android.commonparser.exc.XmlParseException;
import com.yxkang.android.commonparser.trace.Logger;
import com.yxkang.android.commonparser.util.DomUtils;
import com.yxkang.android.commonparser.util.ParserLogger;
import com.yxkang.android.commonparser.util.ParserUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by fine on 2016/6/11.
 * <p>this converter is the default xml converter, implemented by <code>Dom</code></p>
 */
public class XmlConverter implements Converter {

    private Logger logger;

    public XmlConverter() {
        logger = new ParserLogger();
    }

    @Override
    public <T> T convert(Class<T> clazz, String text) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(text.getBytes()));
            Element root = document.getDocumentElement();
            return fromXml(clazz, root);
        } catch (Exception e) {
            logger.error("convert Exception", e);
            return null;
        }
    }

    private <T> T fromXml(Class<T> clazz, final Element element) {
        if (element == null) {
            return null;
        }
        return Converters.convert(clazz, new Reader() {
            @Override
            public boolean contain(String name) {
                logger.debug("contain: name = %s", name);
                List<Element> elements = DomUtils.getChildElements(element, name);
                return elements != null;
            }

            @Override
            public Object getPrimitiveObject(String name) {
                logger.debug("getPrimitiveObject: name = %s", name);
                return DomUtils.getElementValue(element, name);
            }

            @Override
            public Object getObject(String name, Class<?> type) throws Exception {
                logger.debug("getObject: name = %s", name);
                Element childElement = DomUtils.getChildElement(element, name);
                if (childElement != null) {
                    Object object;
                    int childElementCount = DomUtils.getChildElementCount(childElement);
                    int annotationFieldCount = ParserUtils.getAnnotationFieldCount(type);
                    logger.info("getObject: childElementCount = %d, annotationFieldCount = %d",
                            childElementCount, annotationFieldCount);
                    if (childElementCount == 1 && annotationFieldCount == 0) {
                        logger.debug("the custom class has only one child element node and no annotation field, " +
                                "use the constructor with one String parameter type to instance an object");
                        Constructor constructor = type.getConstructor(String.class);
                        constructor.setAccessible(true);
                        object = constructor.newInstance(DomUtils.getChildElementValue(childElement));
                    } else {
                        object = fromXml(type, childElement);
                    }
                    return object;
                }
                return null;
            }

            @Override
            public List<?> getListObjects(String listName, String itemName, Class<?> subType) throws Exception {
                logger.debug("getObject: listName = %s, itemName = %s", listName, itemName);
                List<Element> elements;
                if (!TextUtils.isEmpty(listName)) {
                    Element childElement = DomUtils.getChildElement(element, listName);
                    if (childElement == null) {
                        logger.error("the listName has no childElement, listName = %s", listName);
                        return null;
                    } else {
                        if (TextUtils.isEmpty(itemName)) {
                            throw new XmlParseException("lack of itemName, listName = " + listName);
                        }
                        elements = DomUtils.getChildElements(childElement, itemName);
                    }
                } else {
                    logger.debug("the listName is empty, use the itemName to get elements");
                    elements = DomUtils.getChildElements(element, itemName);
                }
                if (elements != null) {
                    logger.info("getListObjects: elements size = %d", elements.size());
                    List<Object> list = new ArrayList<>();
                    for (Element e : elements) {
                        logger.debug("getListObjects: element name = %s ", e.getNodeName());
                        Object object;
                        if (ParserUtils.isPrimitiveType(subType)) {
                            object = DomUtils.getElementValue(e);
                        } else {              // subType is not primitive type, regard it as custom class
                            int childElementCount = DomUtils.getChildElementCount(e);
                            int annotationFieldCount = ParserUtils.getAnnotationFieldCount(subType);
                            logger.info("getListObjects: childElementCount = %d, annotationFieldCount = %d",
                                    childElementCount, annotationFieldCount);
                            if (childElementCount == 1 && annotationFieldCount == 0) {
                                logger.debug("the custom class has only one child element node and no annotation field, " +
                                        "use the constructor with one String parameter type to instance an object");
                                Constructor constructor = subType.getConstructor(String.class);
                                constructor.setAccessible(true);
                                object = constructor.newInstance(DomUtils.getChildElementValue(e));
                            } else {
                                object = fromXml(subType, e);
                            }
                        }
                        if (object != null) {
                            if (String.class.isAssignableFrom(subType)) {
                                object = String.valueOf(object);
                            } else if (Number.class.isAssignableFrom(subType)) {
                                if (Byte.class.isAssignableFrom(subType)) {
                                    object = Byte.valueOf(object.toString());
                                } else if (Short.class.isAssignableFrom(subType)) {
                                    object = Short.valueOf(object.toString());
                                } else if (Integer.class.isAssignableFrom(subType)) {
                                    object = Integer.valueOf(object.toString());
                                } else if (Long.class.isAssignableFrom(subType)) {
                                    object = Long.valueOf(object.toString());
                                } else if (Float.class.isAssignableFrom(subType)) {
                                    object = Float.valueOf(object.toString());
                                } else if (Double.class.isAssignableFrom(subType)) {
                                    object = Double.valueOf(object.toString());
                                }
                            } else if (Boolean.class.isAssignableFrom(subType)) {
                                object = Boolean.valueOf(object.toString());
                            }
                            list.add(object);
                        }
                    }
                    return list;
                }
                return null;
            }
        }, logger);
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
