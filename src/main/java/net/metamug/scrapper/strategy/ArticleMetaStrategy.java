package net.metamug.scrapper.strategy;

import java.util.Date;
import org.jsoup.nodes.Document;

/**
 *
 * @author deepak
 */
public class ArticleMetaStrategy extends WebMetaStrategy {

    public ArticleMetaStrategy(Document doc, String url) {
        super(doc, url);
    }

    public String generateAuthor() {
        String author = null;
        return author;
    }

    public Date generateDatePublished() {
        return null;
    }

    public String[] generateArticleTags() {
        return null;
    }

}

/**
 * detecting wordpress articles 
 * class="single single-post postid-19057 single-format-standard header-image sidebar-content-sidebar"
 * Wordpress article details
 * <div class="post-info"><span class="date published time"
 * title="2014-07-09T03:05:59+00:00">Wednesday, July 9, 2014</span>
 * | <span class="author vcard"><span class="fn"><a
 * href="http://mac360.com/author/kate-mackenzie/" rel="author">Kate
 * MacKenzie</a></span></span>
 * <span class="categories">Posted In <a
 * href="http://mac360.com/category/news-comment/" title="View all posts in News
 * and Comment" rel="category tag">News and Comment</a></span></div>
 */
