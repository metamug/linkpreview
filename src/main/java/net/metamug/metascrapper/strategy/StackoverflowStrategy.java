/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.metamug.metascrapper.strategy;

import org.jsoup.nodes.Document;
import net.metamug.metascrapper.entity.WebMetaData;
import static net.metamug.metascrapper.util.MetaScrapperUtil.getHost;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 *
 * @author cskksc
 */
public class StackoverflowStrategy extends WebMetaStrategy
{

    Element metablock;
    
    public StackoverflowStrategy(Document doc, String url, Element metablock) {
        super(doc, url);
        this.metablock = metablock;
    }
    
    @Override
    public WebMetaData getMeta() {
        WebMetaData meta = new WebMetaData();
        meta.setTitle(getTitle());
        meta.setDomain(getHost(url));
        meta.setType("text/html");
        meta.setDescription(getStackoverflowD());
        meta.setThumbnail(getStackoverflowT());
        return meta;
    }
    
    public String getStackoverflowD()
    {
        
        String desc;
        Element element = metablock.select("div.question").first();
  
        if ((desc = getFirstText(element, "div.post-text")) != null) {
        }
        return desc;
        
    }
    
    public String getStackoverflowT()
    {
        return new String ("http://cdn.sstatic.net/stackoverflow/img/apple-touch-icon.png");
    }
    
}


