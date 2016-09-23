package com.yxkang.android.xmlparser;


import com.yxkang.android.xmlparser.trace.Recordable;

/**
 * Created by yexiaokang on 2016/6/1.
 * <p>
 * convert the java bean to xml string
 */
public interface Serializer extends Recordable {

    /**
     * set the xml encoding, this method should be called before {@link #toXmlString(Object)}
     *
     * @param encoding the xml encoding
     */
    void setEncoding(String encoding);

    /**
     * get the current xml serializer encoding
     *
     * @return the encoding
     */
    String getEncoding();

    /**
     * set the xml CRLF, this method should be called before {@link #toXmlString(Object)}
     *
     * @param crlf the xml CRLF
     */
    void setCRLF(String crlf);

    /**
     * get the current xml serializer CRLF
     *
     * @return the CRLF
     */
    String getCRLF();

    /**
     * set the xml standalone flag (if standalone not null), this method should be called before {@link #toXmlString(Object)}
     *
     * @param standalone the xml standalone
     */
    void setStandalone(Boolean standalone);

    /**
     * get the current xml serializer standalone flags
     *
     * @return the standalone flags
     */
    Boolean getStandalone();

    /**
     * convert a java bean to the xml string. the bean should be annotated
     * with {@link com.yxkang.android.xmlparser.annotation.Document}, otherwise it will return null
     *
     * @param bean java bean
     * @return the xml string, or null if some errors occured
     */
    String toXmlString(Object bean);
}
