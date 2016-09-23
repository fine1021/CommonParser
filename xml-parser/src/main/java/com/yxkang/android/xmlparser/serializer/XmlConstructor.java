package com.yxkang.android.xmlparser.serializer;

import android.text.TextUtils;

import com.yxkang.android.xmlparser.Serializer;
import com.yxkang.android.xmlparser.annotation.Document;
import com.yxkang.android.xmlparser.common.Writer;
import com.yxkang.android.xmlparser.entry.XmlElement;
import com.yxkang.android.xmlparser.exc.XmlSerializeException;
import com.yxkang.android.xmlparser.trace.Logger;
import com.yxkang.android.xmlparser.util.DefaultLogger;
import com.yxkang.android.xmlparser.util.ParserUtils;
import com.yxkang.android.xmlparser.util.SerializerUtils;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by yexiaokang on 2016/9/22.
 */

public class XmlConstructor implements Serializer {

    private String crlf;
    private String encoding = "utf-8";
    private Boolean standalone;
    private Logger logger;

    public XmlConstructor() {
        setLogger(new DefaultLogger());
    }

    @Override
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public String getEncoding() {
        return encoding;
    }

    @Override
    public void setCRLF(String crlf) {
        this.crlf = crlf;
    }

    @Override
    public String getCRLF() {
        return crlf;
    }

    @Override
    public void setStandalone(Boolean standalone) {
        this.standalone = standalone;
    }

    @Override
    public Boolean getStandalone() {
        return standalone;
    }

    @Override
    public String toXmlString(Object bean) {
        if (bean.getClass().isAnnotationPresent(Document.class)) {
            Document document = bean.getClass().getAnnotation(Document.class);
            String rootElementName = document.name();
            if (TextUtils.isEmpty(rootElementName)) {
                rootElementName = bean.getClass().getSimpleName();
            }
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlSerializer xmlSerializer = factory.newSerializer();
                StringWriter writer = new StringWriter();
                xmlSerializer.setOutput(writer);
                xmlSerializer.startDocument(getEncoding(), getStandalone());
                if (!TextUtils.isEmpty(getCRLF())) {
                    xmlSerializer.text(getCRLF());
                }

                XmlElement xmlElement = SerializerUtils.getBeanElement(bean);
                Serializers.startTag(xmlSerializer, this, rootElementName, xmlElement);

                fromBean(bean, xmlSerializer, this);

                Serializers.endTag(xmlSerializer, this, rootElementName, xmlElement);
                xmlSerializer.endDocument();
                xmlSerializer.flush();
                return writer.toString();
            } catch (XmlPullParserException e) {
                logger.error("XmlPullParserException", e);
                return null;
            } catch (IOException e) {
                logger.error("IOException", e);
                return null;
            }
        } else {
            logger.error("convert：the bean should be annotated with Document annotation");
            return null;
        }
    }

    private void fromBean(Object bean, final XmlSerializer xmlSerializer, final Serializer serializer) {

        if (bean == null) {
            logger.debug("fromBean: bean == null");
            return;
        }

        Serializers.write(bean, new Writer() {
            @Override
            public void writePrimitiveElement(String elementName, String elementValue, XmlElement xmlElement) {
                logger.debug("writePrimitiveElement: elementName = %s, elementValue = %s", elementName, elementValue);
                Serializers.addTag(xmlSerializer, serializer, elementName, elementValue, xmlElement);
            }

            @Override
            public void writeElement(String elementName, String tagName, Object bean) {
                if (bean == null) {
                    logger.warn("writeElement: bean == null, elementName = %s", elementName);
                    return;
                }
                logger.debug("writeElement: elementName = %s, beanClass = %s", elementName, bean.getClass().getName());
                XmlElement xmlElement = SerializerUtils.getBeanElement(bean);
                logger.debug("writeElement: attributes size = %d", xmlElement.getAttributes().size());
                Serializers.startTag(xmlSerializer, serializer, elementName, xmlElement);
                if (TextUtils.isEmpty(tagName)) {
                    logger.debug("writeElement: tagName is empty, serialize this bean");
                    fromBean(bean, xmlSerializer, serializer);
                } else {
                    logger.debug("writeElement: tagName is not empty, element value will be obj.toString(), tagName = %s", tagName);
                    Serializers.addTag(xmlSerializer, serializer, tagName, String.valueOf(bean));
                }
                Serializers.endTag(xmlSerializer, serializer, elementName, xmlElement);
            }

            @Override
            public void writeElementList(String elementListName, String elementName, String itemName, List<?> list, Class<?> subType) throws XmlSerializeException {
                logger.debug("writeElementList: elementListName = %s, elementName = %s, itemName = %s",
                        elementListName, elementName, itemName);
                if (list == null || list.isEmpty()) {
                    logger.warn("writeElementList: list is null or empty, do nothing");
                    return;
                }
                boolean primitive = ParserUtils.isPrimitiveType(subType);
                logger.debug("writeElementList: subType primitive = " + primitive);
                if (primitive && TextUtils.isEmpty(itemName)) {
                    throw new XmlSerializeException("item element is primitive type value, but itemName is empty");
                }
                if (TextUtils.isEmpty(elementName)) {
                    logger.debug("writeElementList: elementName is empty, just use elementListName as loop tagName");
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        Object o = list.get(i);
                        if (primitive) {
                            logger.debug("writeElementList: item element value is primitive type value, just toString()");
                            Serializers.startTag(xmlSerializer, serializer, elementListName);
                            Serializers.addTag(xmlSerializer, serializer, itemName, String.valueOf(o));
                            Serializers.endTag(xmlSerializer, serializer, elementListName);
                        } else {
                            logger.debug("writeElementList: beanClass = %s", o.getClass().getName());
                            XmlElement xmlElement = SerializerUtils.getBeanElement(o);
                            logger.debug("writeElementList: attributes size = %d", xmlElement.getAttributes().size());
                            Serializers.startTag(xmlSerializer, serializer, elementListName, xmlElement);
                            if (TextUtils.isEmpty(itemName)) {
                                logger.debug("writeElementList: itemName is empty, serialize this bean");
                                // assume this is a bean
                                fromBean(o, xmlSerializer, serializer);
                            } else {
                                logger.debug("writeElementList: itemName is not empty, element value will be obj.toString()");
                                Serializers.addTag(xmlSerializer, serializer, itemName, String.valueOf(o));
                            }
                            Serializers.endTag(xmlSerializer, serializer, elementListName, xmlElement);
                        }
                    }
                } else {
                    Serializers.startTag(xmlSerializer, serializer, elementListName);
                    logger.debug("writeElementList: elementName is not empty, just use elementName as loop tagName");
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        Object o = list.get(i);
                        if (primitive) {
                            logger.debug("writeElementList: item element value is primitive type value, just toString()");
                            Serializers.startTag(xmlSerializer, serializer, elementName);
                            Serializers.addTag(xmlSerializer, serializer, itemName, String.valueOf(o));
                            Serializers.endTag(xmlSerializer, serializer, elementName);
                        } else {
                            logger.debug("writeElementList: beanClass = %s", o.getClass().getName());
                            XmlElement xmlElement = SerializerUtils.getBeanElement(o);
                            logger.debug("writeElementList: attributes size = %d", xmlElement.getAttributes().size());
                            Serializers.startTag(xmlSerializer, serializer, elementName, xmlElement);
                            if (TextUtils.isEmpty(itemName)) {
                                logger.debug("writeElementList: itemName is empty, serialize this bean");
                                // assume this is a bean
                                fromBean(o, xmlSerializer, serializer);
                            } else {
                                logger.debug("writeElementList: itemName is not empty, element value will be obj.toString()");
                                Serializers.addTag(xmlSerializer, serializer, itemName, String.valueOf(o));
                            }
                            Serializers.endTag(xmlSerializer, serializer, elementName, xmlElement);
                        }
                    }
                    Serializers.endTag(xmlSerializer, serializer, elementListName);
                }
            }
        }, logger);
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
