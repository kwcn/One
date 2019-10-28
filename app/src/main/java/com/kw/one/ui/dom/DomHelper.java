package com.kw.one.ui.dom;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Kang Wei
 * @date 2019/9/2
 */
public class DomHelper {
    public static Document loadWithDom(String xmlFilePath) {
        try {
            File file = new File(xmlFilePath);
            if (!file.exists()) {
                throw new RuntimeException("not find file:" + xmlFilePath);
            } else {
                InputStream inputStream = new FileInputStream(file);
                DocumentBuilderFactory documentBuilderFactory =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(inputStream);
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return document;
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            return null;
        }
    }

}
