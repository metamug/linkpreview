/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.entity;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.metamug.metascrapper.strategy.StackoverflowStrategy;
import net.metamug.metascrapper.strategy.WebMetaStrategy;
import net.metamug.metascrapper.strategy.WikipediaMetaStrategy;
import static net.metamug.metascrapper.util.MetaScrapperUtil.getHost;
import net.metamug.metascrapper.util.RobotsTxt;
import net.metamug.metascrapper.util.StorageManager;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author deepak
 */
public class MetaDataFactory {

    public static WebMetaData create(String url) {
        
        RobotsTxt robo = new RobotsTxt(url);
        
        if(robo.canAccess(url))
        {
            //The factory must create the appropriate strategy
            Response response = StorageManager.getResponse(url);
            if (response == null || response.contentType() == null ) {
                return new WebMetaData("Error", "http://metamug.com/assets/img/symbols/alert-triangle-grey.png", "text/html", "Unable to reach the page " + url, "http://metamug.com/assets/img/symbols/alert-triangle-grey.png");
            }
            //System.out.println(response.body());
            if (response.contentType().startsWith("image")) {
                return new WebMetaData("Image " + WordUtils.capitalize(FilenameUtils.getBaseName(url).replaceAll("[-_]", " ")), getHost(url), "image", url, url.split("\\?")[0]);
            } else if (response.contentType().startsWith("application/pdf")) {
                return new WebMetaData("PDF", getHost(url), "pdf", url, "http://metamug.com/assets/img/symbols/pdf_symbol.png");
                //return new PDFMetaData("http://metamug.com/assets/img/symbols/pdf_symbol.png");
            } else if (response.contentType().startsWith("audio")) {
                return new WebMetaData("Audio", getHost(url), "audio", url, "http://metamug.com/assets/img/music-icon.png");
            } else {
                try {
                    Document document = response.parse();
                    WebMetaStrategy metaFinder;
                    metaFinder = getMetaStrategy(document, url);
                    return metaFinder.getMeta();
                    
                } catch (IOException ex) {
                    //keeping level severe halts execution
                    //Logger.getLogger(WebMetaStrategy.class.getName()).log(Level.INFO, null, ex);
                    //if (response.statusCode() / 100 == 4) {
                    return new WebMetaData("Error", "http://metamug.com/assets/img/symbols/alert-triangle-grey.png", "text/html",
                            "Unable to reach the page " + url, "http://metamug.com/assets/img/symbols/alert-triangle-grey.png");
                    //}
                    //return null;
                }
            }
        }
        else
        {
            return new WebMetaData("Error", "http://metamug.com/assets/img/symbols/alert-triangle-grey.png", "text/html",
                    "Unable to reach the page " + url, "http://metamug.com/assets/img/symbols/alert-triangle-grey.png");
        }
        
        
    }

    public static WebMetaStrategy getMetaStrategy(Document doc, String url) {
        WebMetaStrategy strategy = new WebMetaStrategy(doc, url);
        Element metablock;
        if (url.contains("wikipedia.org/wiki/") && (metablock = doc.select("body.mediawiki").first()) != null) {
            strategy = new WikipediaMetaStrategy(doc, url, metablock);
        } 
        else if(url.contains("stackoverflow") && (metablock = doc.select("body.question-page").first()) != null){
            strategy = new StackoverflowStrategy(doc, url, metablock);
        }
        else if ((metablock = doc.select("[itemtype=http://schema.org/Product]").first()) != null) {
            //strategy = new ProductMetaStrategy(doc, url, metablock);
        } else if ((metablock = doc.select("[itemtype=http://schema.org/Restaurant]").first()) != null) {
        }
        return strategy;
    }


}
