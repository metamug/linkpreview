/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.entity;

import java.util.Date;

/**
 *
 * @author deepak
 */
public class ArticleMetaData extends WebMetaData implements MetaData{
    //<meta property="article:published_time" content="2014-03-19T13:59:14+05:30">
    private Date datePublished;
    private String region;
    //<link rel="publisher" href="https://plus.google.com/111378663671814920979">
    //<a href="/author/arun" alt="arun" title="arun" rel="author">Arun George</a>
    private String author;
    
}
