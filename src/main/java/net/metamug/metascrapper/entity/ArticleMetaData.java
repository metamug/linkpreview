/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.entity;

import com.google.gson.annotations.Expose;
import java.util.Date;

// test import and push operatios
import java.util.LinkedList;

/**
 *
 * @author deepak
 */
public class ArticleMetaData extends WebMetaData implements MetaData{
    //<meta property="article:published_time" content="2014-03-19T13:59:14+05:30">
    @Expose
    private Date datePublished;
    @Expose
    private String region;
    //<link rel="publisher" href="https://plus.google.com/111378663671814920979">
    //<a href="/author/arun" alt="arun" title="arun" rel="author">Arun George</a>
    @Expose
    private String author;
    
}
