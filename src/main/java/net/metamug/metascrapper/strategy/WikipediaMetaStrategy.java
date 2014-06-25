/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.strategy;

import net.metamug.metascrapper.entity.WebMetaData;
import static net.metamug.metascrapper.util.MetaScrapperUtil.getHost;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author deepak if (url.contains("wikipedia.org/wiki/")) { }
 */
public class WikipediaMetaStrategy extends WebMetaStrategy {

    Element metablock;

    public WikipediaMetaStrategy(Document doc, String url, Element metablock) {
        super(doc, url);
        this.metablock = metablock;
    }

    @Override
    public WebMetaData getMeta() {
        System.out.println("Wikipedia metadata ");
        WebMetaData meta = new WebMetaData();
        meta.setTitle(getTitle());
        meta.setDomain(getHost(url));
        meta.setType("text/html");
        meta.setDescription(getWikipediaDescription());
        meta.setThumbnail(getWikipediaThumbnail());
        return meta;
    }

    public String getWikipediaDescription() {
        String desc;
        //co ordinates is the only problem.
        //<p><span style="font-size: small;"><span id="coordinates">
        Element element = metablock.select("div.mw-content-ltr").first();
        if (exists(element, "p > span > span#coordinates")) {
            element.select("p").first().remove();
        }
        if (exists(element, "table.metadata")) {
            element.select("table.metadata").first().remove();
        }

        if ((desc = getFirstText(element, "p")) != null) {
            desc = desc.replaceAll("\\[\\d\\]", "");
        }
        return desc;
    }

    public String getWikipediaThumbnail() {
        String thumbURL = null;
        Elements metaElements;
        Element metaElement;

        metaElements = metablock.select("table.infobox");

        //Get the images from the section .. 
        if (metaElements != null && !metaElements.isEmpty()) {
            metaElement = metaElements.first();
            metaElements = metaElement.select("a.image>img");
        }

        //Image not found in infobox. Look for thumnails inside the page
        if (metaElements == null || metaElements.isEmpty()) {
            metaElements = metablock.select(".thumbinner");
            metaElement = metaElements.first();
            if (!metaElements.isEmpty()) {
                metaElements = metaElement.select("a.image>img");
            }
        }

        if (metaElements != null && !metaElements.isEmpty()) {
            metaElement = metaElements.first();
            thumbURL = metaElement.absUrl("src");
        }
        return thumbURL;
    }
}
