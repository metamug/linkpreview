package net.metamug.scrapper.factory;

import java.io.IOException;
import net.metamug.scrapper.entity.WebMetaData;
import net.metamug.scrapper.strategy.FlickrStrategy;
import net.metamug.scrapper.strategy.ProductMetaStrategy;
import net.metamug.scrapper.strategy.QnAStrategy;
import net.metamug.scrapper.strategy.WebMetaStrategy;
import net.metamug.scrapper.strategy.WikipediaMetaStrategy;
import net.metamug.scrapper.util.DownloadManager;
import static net.metamug.scrapper.util.ScrapperUtil.getHost;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Factory pattern used to extract meta data once URL is provided.
 *
 * @author deepak
 */
public class MetaDataFactory {

    /**
     * Get meta data from the supplied URL
     *
     * @param url URL supplied
     * @return meta data
     */
    public static WebMetaData create(String url) {

        Response response = DownloadManager.getResponse(url);

        if (response == null || response.contentType() == null) {
            return getErrorMetadata(url);
        }
        if (response.contentType().startsWith("image")) {
            return new WebMetaData("Image ", getHost(url), WebMetaData.IMAGE,
                    WordUtils.capitalize(FilenameUtils.getBaseName(url).replaceAll("[-_]", " ")),
                    url.split("\\?")[0]);
        } else if (response.contentType().startsWith("application/pdf")) {
            return new WebMetaData("PDF", getHost(url), WebMetaData.PDF, url, WebMetaData.SYMBOL_PDF);
        } else if (response.contentType().startsWith("audio")) {
            return new WebMetaData("Audio", getHost(url), WebMetaData.AUDIO, url, WebMetaData.SYMBOL_MUSIC);
        } else {
            return useStrategy(response, url);
        }
    }

    /**
     *
     * @param response response to be parsed
     * @param url URL of the response
     * @return meta data
     */
    public static WebMetaData useStrategy(Response response, String url) {
        try {
            Document document = response.parse();
            WebMetaStrategy metaFinder;
            metaFinder = getMetaStrategy(document, url);
            return metaFinder.getMeta();
        } catch (IOException ex) {
            //keeping level severe halts execution
            //(response.statusCode() / 100 == 4)
            return getErrorMetadata(url);
        }
    }

    /**
     * Suggest Strategy to extract meta data
     *
     * @param doc the HTML document for scraping
     * @param url the URL for the document
     * @return strategy to extract meta data
     */
    public static WebMetaStrategy getMetaStrategy(Document doc, String url) {
        WebMetaStrategy strategy = null;
        Element metablock;
        if (url.contains("wikipedia.org/wiki/") && (metablock = doc.select("body.mediawiki").first()) != null) {
            strategy = new WikipediaMetaStrategy(doc, url, metablock);
        } else if (url.contains("stackoverflow.com/") && (metablock = doc.select("body.question-page").first()) != null) {
            strategy = new QnAStrategy(doc, url, metablock);
        } else if (url.contains("flipkart.com/") && (metablock = doc.select("body.ProductPage").first()) != null) {
            strategy = new ProductMetaStrategy(doc, url, metablock);
        } else if (url.contains("amazon.in/") && (metablock = doc.select("bodydp").first()) != null) {
            strategy = new ProductMetaStrategy(doc, url, metablock);
        } else if (url.contains("snapdeal.com/") && (metablock = doc.select("body").first()) != null) {
            strategy = new ProductMetaStrategy(doc, url, metablock);
        } else if (url.contains("flickr.com/") && (metablock = doc.select("body").first()) != null) {
            strategy = new FlickrStrategy(doc, url, metablock);
        } else if ((metablock = doc.select("[itemtype=http://schema.org/Restaurant]").first()) != null) {

        } else {
            strategy = new WebMetaStrategy(doc, url);
        }

        return strategy;
    }

    public static WebMetaData getErrorMetadata(String url) {
        return new WebMetaData(url, getHost(url), WebMetaData.HTML,
                "", "");
    }

}
