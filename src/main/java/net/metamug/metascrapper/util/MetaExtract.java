/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author deepak
 */
public class MetaExtract {

    public static String getSchemaPropery(Element element, String query) {
        String content;
        if((content = getFirstText(element, query)) == null){
            content = getFirstAttributeValue(element, query, "content");
        }
        return content;
    }

    public static String getFirstText(Element element, String selectionPattern) {
        String content = null;
        Elements metaElements;
        Element metaElement;

        metaElements = element.select(selectionPattern);

        if (metaElements != null && !metaElements.isEmpty()) {
            metaElement = metaElements.first();
            content = metaElement.text();
        }
        if (content != null) {
            return content.isEmpty() ? null : content;
        }

        return content;
    }

    public static String getFirstAttributeValue(Element element, String selectionPattern, String attribute) {
        String content = null;
        Elements metaElements;
        Element metaElement;

        metaElements = element.select(selectionPattern);

        if (metaElements != null && !metaElements.isEmpty()) {
            metaElement = metaElements.first();
            content = metaElement.attr(attribute);
        }
        
        if (content != null) {
            return content.isEmpty() ? null : content;
        }

        return content;
    }

    private static String getMetaTagContent(Document doc, String metaName) {

        String content = null;
        Elements metaElements;
        Element metaElement;

        metaElements = doc.select(metaName);

        if (metaElements != null && !metaElements.isEmpty()) {
            metaElement = metaElements.first();

            // Since meta elements are always block elements
            if (metaElement.isBlock()) {
                if ("meta".equals(metaElement.tagName())) {
                    content = metaElement.attr("content");
                } else if ("link".equals(metaElement.tagName())) {
                    content = metaElement.attr("abs:href");
                }
            }
        }

        if (content != null && !content.isEmpty()) {
            return content;
        } else {
            return null;
        }
    }
}
