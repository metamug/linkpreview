/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.scrapper.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Jsoup Helper Functions
 *
 * @author deepak
 */
public class StrategyHelper {

    public static String getSchemaPropery(Element element, String query) {
        String content;
        if ((content = getFirstText(element, query)) == null) {
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

    /**
     * Get value of a meta tag present in head of the document
     *
     * @param doc
     * @param metaName
     * @return
     */
    public static String getMetaTagContent(Document doc, String metaName) {

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

    /**
     * Check if an element exists.
     *
     * @param element
     * @param query
     * @return
     */
    public static boolean exists(Element element, String query) {
        Elements elements = element.select(query);
        if (elements == null || elements.isEmpty()) {
            return false;
        }

        return true;
    }

    // Elements with inner html.
    public static String getFirstText(Document doc, String selectionPattern) {
        String content = null;
        Elements metaElements;
        Element metaElement;

        metaElements = doc.select(selectionPattern);

        if (metaElements != null && !metaElements.isEmpty()) {
            metaElement = metaElements.first();
            content = metaElement.text();
        }
        if (content != null) {
            return content.isEmpty() ? null : content;
        }

        return content;
    }

    /**
     * Get First string from tag with minimum length.
     *
     * @param doc
     * @param selectionPattern the tag to search text.
     * @param length minimum length for which the text should be returned
     * @return the returned text
     */
    public static String getFirstLongText(Document doc, String selectionPattern, int length) {
        String content = "";
        Elements metaElements;

        metaElements = doc.select(selectionPattern);

        for (Element e : metaElements) {
            if (e.text().length() > length) {
                content = e.text();
                break;
            }
        }

        if (content != null) {
            return content.isEmpty() ? null : content;
        }

        return content;
    }

    public static String getFirstAttributeValue(Document doc, String selectionPattern, String attribute) {
        String content = null;
        Elements metaElements;
        Element metaElement;

        metaElements = doc.select(selectionPattern);

        if (metaElements != null && !metaElements.isEmpty()) {
            metaElement = metaElements.first();
            content = metaElement.attr(attribute);
        }
        if (content != null) {
            return content.isEmpty() ? null : content;
        }

        return content;
    }
}
