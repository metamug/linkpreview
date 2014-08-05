/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.scrapper.entity;

import com.google.gson.annotations.Expose;
import java.util.Date;

/**
 *
 * @author deepak
 */
public class ArticleMetaData extends WebMetaData implements MetaData {

    //<meta property="article:published_time" content="2014-03-19T13:59:14+05:30">
    @Expose
    private Date datePublished;
    @Expose
    private String region;
    //<link rel="publisher" href="https://plus.google.com/111378663671814920979">
    //<a href="/author/arun" alt="arun" title="arun" rel="author">Arun George</a>
    @Expose
    private String author;

    @Expose
    private String[] tags;

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

}
