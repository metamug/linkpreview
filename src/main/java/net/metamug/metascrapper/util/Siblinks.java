/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import net.metamug.metascrapper.entity.MetaDataFactory;
import net.metamug.metascrapper.entity.WebMetaData;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author deepak
 */
public class Siblinks {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        String url = "http://economictimes.indiatimes.com/slideshows/biz-entrepreneurship/five-cool-indian-startups-offering-drone-technology/drone-technology-startups/slideshow/33900000.cms";
//        String url = "http://stackoverflow.com/questions/9607903/get-domain-name-from-given-url";
//        String url = "http://thenextweb.com/dd/2014/07/04/mobile-advertising-isnt-huge-hyped-yet/";
//                String url = "http://www.leghumped.com/2008/11/03/java-matching-urls-with-regex-wildcards/";

//        List<Set<String>> list = new ArrayList<>();
        Map<String, String> pageLinks = new HashMap<>();

        for (String i : getUniqueOutLinks(url)) {
            pageLinks.put(i, "0");
        }
        for (String o : pageLinks.keySet()) {
            List<String> list = getOutLinks(o);
            for (String p : pageLinks.keySet()) {
                pageLinks.replace(p, "" + Collections.frequency(list, p));
            }
        }

        SortedMap<String, String> sortedData = new TreeMap<>(pageLinks);
        printMap(sortedData);

//        Set<String> union = new HashSet<>(set);
//        Set<String> intersection = new HashSet<>(set);
//        for (Set<String> s : list) {            
//            System.out.println("Start *************************");
//            for (String o : s) {
//                System.out.println(o);
//            }
//            union.addAll(s);
//            if (!s.isEmpty()) {
//                intersection.retainAll(s);
//            }
//        }
//        union.removeAll(intersection);
//        for (String o : intersection) {
//            System.out.println(o);
//        }
//        list.reta
//        int i=1;
//        list.addAll(s);
//        while(i<list.size()){
//            Set s1 = list.get(i);
//        }
//        Iterator<String> it = set.iterator();
//        while (it.hasNext()) {
//            String o = it.next();
//            if (StringUtils.isNotBlank(o) && getDomainName(url).equals(getDomainName(o))) {
//                WebMetaData meta = MetaDataFactory.create(o);
//                String thumb = meta.getThumbnail().getUrl();
//                String title = meta.getTitle();
//                String desc = meta.getDescription();
//                if (StringUtils.isNotBlank(title)
//                        && StringUtils.isNotBlank(thumb)
//                        && StringUtils.isNotBlank(desc)) {
//                    System.out.println("URL: " + o);
////                    System.out.println("THUMB: " + meta.getThumbnail().getUrl());
//                }
//            }
//        }
//            parent = new File(parent).getParent();
//            }
//        }
    }

    public static List<String> getOutLinks(String url) {
        List<String> list = new ArrayList();
        try {
            Document doc = StorageManager.getResponse(url).parse();
            Elements links = doc.select("a[href]");
            for (Element link : links) { //links
                String s = link.attr("abs:href");
                if (StringUtils.isNotBlank(s) && getDomainName(url).equals(getDomainName(s))) {
                    list.add(s);
                }
            }
        } catch (Exception e) {

        }
//        list.add(url);
        return list;
    }

    public static Set<String> getUniqueOutLinks(String url) {
        Set<String> list = new HashSet();
        try {
            Document doc = StorageManager.getResponse(url).parse();
            Elements links = doc.select("a[href]");
            for (Element link : links) { //links
                String s = link.attr("abs:href");
//                String s = link.attr("abs:href").split("\\?")[0].split("#")[0];
                if (StringUtils.isNotBlank(s) && getDomainName(url).equals(getDomainName(s))) {
                    list.add(s);
                }
            }
        } catch (Exception e) {

        }
//        list.add(url);
        return list;
    }

    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    public static void printMap(Map<String, String> map) {
        for (Map.Entry entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey() + " Value : "
                    + entry.getValue());
        }
    }
}
