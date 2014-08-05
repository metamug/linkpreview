package net.metamug.scrapper.strategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.metamug.scrapper.entity.ArticleMetaData;
import net.metamug.scrapper.entity.WebMetaData;
import net.metamug.scrapper.util.ScrapperUtil;
import static net.metamug.scrapper.util.StrategyHelper.getFirstText;
import static net.metamug.scrapper.util.StrategyHelper.getMetaTagContent;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;

/**
 *
 * @author deepak
 */
public class ArticleMetaStrategy extends WebMetaStrategy {

    public ArticleMetaStrategy(Document doc, String url) {
        super(doc, url);
    }

    @Override
    public WebMetaData getMeta() {
        ArticleMetaData meta = new ArticleMetaData();
        try {
            meta.setDatePublished(generateDatePublished());
        } catch (ParseException ex) {
            meta.setDatePublished(null);
        }
        meta.setAuthor(generateAuthor());
        meta.setTitle(getTitle());
        meta.setPicture(getThumbnailURL());
        meta.setType(WebMetaData.ARTICLE);
        meta.setDomain(ScrapperUtil.getHost(url));

        return meta;
    }

    public String generateAuthor() {
        String author = null;
        if (StringUtils.isNotBlank(author = getMetaTagContent(doc, "meta[property=article:author]"))) {
            
        } else if (StringUtils.isNotBlank(author = getMetaTagContent(doc, "meta[name=sailthru.author]"))) {
            //<meta name="sailthru.author" content="<a href=&quot;/author/josh-constine/&quot; title=&quot;Posts by Josh Constine&quot; onclick=&quot;s_objectID='river_author';&quot; rel=&quot;author&quot;>Josh Constine</a> <span class=&quot;twitter-handle&quot;>(<a href=&quot;https://twitter.com/joshconstine&quot; rel=&quot;external&quot;>@joshconstine</a>)</span>">
        } else if (StringUtils.isNotBlank(author = getFirstText(doc, "div[itemprop*=author]"))) { //since description itemprop is multivalued
        }
        return author;
    }

    public Date generateDatePublished() throws ParseException {
        String publishedDateString = null;
        Date publishedDate = null;
        if (StringUtils.isNotBlank(publishedDateString = getMetaTagContent(doc, "meta[property=article:published_time]"))) {
            //2014-07-25T14:15:03+00:00
            publishedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(publishedDateString);
        } else if (StringUtils.isNotBlank(publishedDateString = getMetaTagContent(doc, "meta[http-equiv=Last-Modified]"))) {
            //<meta http-equiv="Last-Modified" content="03:27:49 PM Tuesday, August 05, 2014">
            publishedDate = new SimpleDateFormat("HH:mm:ss EEE','").parse(publishedDateString);
        }
        return publishedDate;
    }

    public String[] generateArticleTags() {
        String[] tags;
        String data;
        if (StringUtils.isNotBlank(data = getMetaTagContent(doc, "meta=[name=sailthru.tags]"))) {
            //<meta name="sailthru.tags" content="Standalone Apps, Facebook, Dropbox, Twitter, instagram">

        }
        return null;
    }

//
//    public String getPublisher() {
//        String publisher = null;
//        if (StringUtils.isNotBlank(publisher = getMetaTagContent(doc, "link[rel=publisher]"))) {
//            //<link rel="publisher" href="https://plus.google.com/117150671992820587865">
//        } else {
//
//        }
//
//        return publisher;
//    }
}

/**
 * detecting wordpress articles class="single single-post postid-19057
 * single-format-standard header-image sidebar-content-sidebar" Wordpress
 * article details
 * <div class="post-info"><span class="date published time"
 * title="2014-07-09T03:05:59+00:00">Wednesday, July 9, 2014</span>
 * | <span class="author vcard"><span class="fn"><a
 * href="http://mac360.com/author/kate-mackenzie/" rel="author">Kate
 * MacKenzie</a></span></span>
 * <span class="categories">Posted In <a
 * href="http://mac360.com/category/news-comment/" title="View all posts in News
 * and Comment" rel="category tag">News and Comment</a></span></div>
 */
