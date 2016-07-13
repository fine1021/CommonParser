package com.yxkang.android.cp.dom4j.converter;

import android.text.TextUtils;

import com.yxkang.android.commonparser.Converter;
import com.yxkang.android.commonparser.Reader;
import com.yxkang.android.commonparser.converter.Converters;
import com.yxkang.android.commonparser.exc.XmlParseException;
import com.yxkang.android.commonparser.trace.Logger;
import com.yxkang.android.commonparser.util.ParserLogger;
import com.yxkang.android.commonparser.util.ParserUtils;
import com.yxkang.android.cp.dom4j.util.Dom4jUtils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yexiaokang on 2016/7/6.
 * <p>this converter is implemented by <code>Dom4j</code></p>
 */
public class Dom4jConverter implements Converter {

    private Logger logger;

    public Dom4jConverter() {
        logger = new ParserLogger();
    }

    @Override
    public <T> T convert(Class<T> clazz, String text) {
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new ByteArrayInputStream(text.getBytes()));
            Element root = document.getRootElement();
            return fromXml(clazz, root);
        } catch (DocumentException e) {
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
                List<Element> elements = Dom4jUtils.getChildElements(element, name);
                return elements.size() > 0;
            }

            @Override
            public Object getPrimitiveObject(String name) {
                logger.debug("getPrimitiveObject: name = %s", name);
                return Dom4jUtils.getElementValue(element, name);
            }

            @Override
            public Object getObject(String name, Class<?> type) throws Exception {
                logger.debug("getObject: name = %s", name);
                Element childElement = Dom4jUtils.getChildElement(element, name);
                if (childElement != null) {
                    Object object;
                    int childElementCount = Dom4jUtils.getChildElementCount(childElement);
                    int annotationFieldCount = ParserUtils.getAnnotationFieldCount(type);
                    logger.info("getObject: childElementCount = %d, annotationFieldCount = %d",
                            childElementCount, annotationFieldCount);
                    if (childElementCount == 1 && annotationFieldCount == 0) {
                        logger.debug("the custom class has only one child element node and no annotation field, " +
                                "use the constructor with one String parameter type to instance an object");
                        Constructor constructor = type.getConstructor(String.class);
                        constructor.setAccessible(true);
                        object = constructor.newInstance(Dom4jUtils.getChildElementValue(childElement));
                    } else {
                        object = fromXml(type, childElement);
                    }
                    return object;
                }
                return null;
            }

            @Override
            public List<?> getListObjects(String listName, String itemName, Class<?> subType) throws Exception {
                logger.debug("getListObjects: listName = %s, itemName = %s", listName, itemName);
                List<Element> elements;
                if (!TextUtils.isEmpty(listName)) {
                    if (TextUtils.isEmpty(itemName)) {
                        logger.debug("the itemName is empty, use the listName to get elements");
                        elements = Dom4jUtils.getChildElements(element, listName);
                    } else {
                        Element childElement = Dom4jUtils.getChildElement(element, listName);
                        if (childElement == null) {
                            logger.error("the listName has no childElement, listName = %s", listName);
                            return null;
                        } else {
                            elements = Dom4jUtils.getChildElements(childElement, itemName);
                        }
                    }
                } else {
                    throw new XmlParseException("lack of listName, listName = " + listName + ", itemName = " + itemName);
                }
                if (elements != null) {
                    logger.info("getListObjects: elements size = %d", elements.size());
                    List<Object> list = new ArrayList<>();
                    for (Element e : elements) {
                        logger.debug("getListObjects: element name = %s ", e.getName());
                        Object object;
                        if (ParserUtils.isPrimitiveType(subType)) {
                            object = Dom4jUtils.getElementValue(e);
                        } else {              // subType is not primitive type, regard it as custom class
                            int childElementCount = Dom4jUtils.getChildElementCount(e);
                            int annotationFieldCount = ParserUtils.getAnnotationFieldCount(subType);
                            logger.info("getListObjects: childElementCount = %d, annotationFieldCount = %d",
                                    childElementCount, annotationFieldCount);
                            if (childElementCount == 1 && annotationFieldCount == 0) {
                                logger.debug("the custom class has only one child element node and no annotation field, " +
                                        "use the constructor with one String parameter type to instance an object");
                                Constructor constructor = subType.getConstructor(String.class);
                                constructor.setAccessible(true);
                                object = constructor.newInstance(Dom4jUtils.getChildElementValue(e));
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
