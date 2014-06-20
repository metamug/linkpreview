/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.metamug.metascrapper.entity;

import net.metamug.metascrapper.strategy.WebMetaStrategy;
import net.metamug.metascrapper.util.MetaExtract;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author cskksc
 */
public class FlipkartStrategy extends WebMetaStrategy
{
    Element productBlock;

    public FlipkartStrategy(Document doc, String url, Element metablock) {
        super(doc, url);
        this.productBlock = metablock;
    }
    
    @Override
    public WebMetaData getMeta()
    {
        ProductMetaData meta = new ProductMetaData(super.getMeta());
        
        meta.setTitle(getTitle());
//        meta.setDomain(getHost(url));
        
        meta.setType(MetaData.PRODUCT);
        meta.setName(MetaExtract.getFirstText(productBlock,"[itemprop=name]"));        
        meta.setPrice(Double.parseDouble(MetaExtract.getFirstText(productBlock, "span.pprice").substring(3)));
        
        meta.setPriceCurrency(MetaExtract.getSchemaPropery(productBlock, "[itemprop=priceCurrency]"));
        
        meta.setRatingCount((Integer.parseInt(MetaExtract.getFirstText(productBlock, "[itemprop=ratingCount]"))));
//        meta.setRatingValue(Float.parseFloat(MetaExtract.getFirstText(productBlock, "[itemprop=ratingValue]")));
        meta.setReviewCount((Integer.parseInt(MetaExtract.getFirstText(productBlock, "[itemprop=reviewCount]"))));
       
        meta.setThumbnail(getProductThumbnail());  
        return meta;
    }
    
    
    public String getProductThumbnail()
    {
        String thumbURL = null;
        Elements metaElements;
        Element metaElement;
               
        metaElement = productBlock.select("div.visible-image-small").first();
       
        if(metaElement != null)
        {
            metaElement = metaElement.select("img").first();
            thumbURL = metaElement.absUrl("src"); 
        }       
        System.out.println(thumbURL);
        
        return thumbURL;
    }
}
