/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.metamug.metascrapper.strategy;

import net.metamug.metascrapper.entity.ImageMetaData;
import net.metamug.metascrapper.entity.WebMetaData;
import net.metamug.metascrapper.util.MetaExtract;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author cskksc
 */
public class FlickrStrategy extends WebMetaStrategy{
    Element metablock;

    public FlickrStrategy(Document doc, String url, Element metablock) {
        super(doc, url);
        this.metablock = metablock;
    }
    
    @Override
    public WebMetaData getMeta(){
        
        // location , height , width, author , views
        
        ImageMetaData meta = new ImageMetaData(super.getMeta());
        
        meta.setTitle(getTitle());
        meta.setPicture(getThumbnail());
        meta.setAuthor(MetaExtract.getFirstText(metablock, "a.owner-name"));
//        meta.setViews(Integer.parseInt(metablock.select("div.view-count > span.view-count-label").first().text()));
//        System.out.println(metablock.select("div.view-count > span.view-count-label").first().text());
        
        return meta;
    }
    
    
    public String getThumbnail()
    {
        String thumbURL = null;
        Elements metaElements;
        Element metaElement;
               
        metaElement = metablock.select("div.view").first();
       
        if(metaElement != null)
        {
            metaElement = metaElement.select("img").first();
            thumbURL = metaElement.absUrl("src"); 
        }       
        System.out.println(thumbURL);
        
        return thumbURL;
    }
    
}
