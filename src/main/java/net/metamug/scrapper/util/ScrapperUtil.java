/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.scrapper.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.metamug.scrapper.strategy.WebMetaStrategy;

/**
 *
 * @author deepak
 */
public class ScrapperUtil {

    /**
     * Will take a url such as http://www.stackoverflow.com and return
     * www.stackoverflow.com
     *
     * @param url
     * @return
     */
    public static String getHost(String url) {
        if (url == null || url.length() == 0) {
            return "";
        }

        int doubleslash = url.indexOf("//");
        if (doubleslash == -1) {
            doubleslash = 0;
        } else {
            doubleslash += 2;
        }

        int end = url.indexOf('/', doubleslash);
        end = end >= 0 ? end : url.length();

        int port = url.indexOf(':', doubleslash);
        end = (port > 0 && port < end) ? port : end;

        return url.substring(doubleslash, end);
    }

    /**
     * Based on :
     * http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/2.3.3_r1/android/webkit/CookieManager.java#CookieManager.getBaseDomain%28java.lang.String%29
     * Get the base domain for a given host or url. E.g. mail.google.com will
     * return google.com
     *
     * @param host
     * @return
     */
    public static String getBaseDomain(String url) {
        String host = getHost(url);

        int startIndex = 0;
        int nextIndex = host.indexOf('.');
        int lastIndex = host.lastIndexOf('.');
        while (nextIndex < lastIndex) {
            startIndex = nextIndex + 1;
            nextIndex = host.indexOf('.', startIndex);
        }
        if (startIndex > 0) {
            return host.substring(startIndex);
        } else {
            return host;
        }
    }

    public static String extractString(String x) {
        return " ";
    }

    public static String makeAbsoluteURL(String url, String base) {
        final URI u;
        if (url == null || url.isEmpty()) {
            return "";
        }
        try {
            u = new URI(url);
            URL baseURL = new URL(base);
            if (u.isAbsolute()) {
                return url;
            } else if (u.toString().startsWith("//")) {
                return "http:" + url;
            } else {
                if (url.startsWith("/")) {
                    return baseURL.getProtocol() + "://" + baseURL.getHost() + url;
                } else {
                    return base + "/" + url;
                }
            }
        } catch (URISyntaxException ex) {
            return null;
        } catch (MalformedURLException ex) {
            Logger.getLogger(WebMetaStrategy.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static String getDomainName(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        } catch (URISyntaxException ex) {
            Logger.getLogger(StrategyHelper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
