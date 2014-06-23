/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.strategy;

import net.metamug.metascrapper.entity.MetaData;
import net.metamug.metascrapper.entity.ProductMetaData;
import net.metamug.metascrapper.entity.WebMetaData;
import net.metamug.metascrapper.util.MetaExtract;
import net.metamug.metascrapper.util.MetaScrapperUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.HashMap;

/**
 *
 * @author deepak This strategy pulls product metadata from a webpage provided
 * the page confers to http://schema.org/Product specifications
 */
public class ProductMetaStrategy extends WebMetaStrategy {

    Element productBlock;
    HashMap<String, HashMap> csk = new HashMap<>();
    HashMap<String, String> flipkart = new HashMap<>();
    HashMap<String, String> amazon = new HashMap<>();

    String site;

    public ProductMetaStrategy(Document doc, String url, Element metablock) {
        super(doc, url);
        this.productBlock = metablock;

        site = MetaScrapperUtil.getBaseDomain(url);

        flipkart.put("name","[itemprop=name]");
        flipkart.put("price","span.pprice");
        flipkart.put("priceCurrency","[itemprop=priceCurrency]");
        flipkart.put("ratingCount","[itemprop=ratingCount");
        flipkart.put("reviewCount","[itemprop=reviewCount]");
        flipkart.put("ratingValue", "[itemprop=ratingValue]");
        flipkart.put("thumbnail", getThumbnail("div.visible-image-small"));
        csk.put("flipkart.com", flipkart);


        amazon.put("name","div.buying > h1.parseasinTitle > span#btAsinTitle > span");
        amazon.put("price","span#actualPriceValue > b.priceLarge > span");
        amazon.put("priceCurrency", "span#actualPriceValue > b.priceLarge > span > span.currencyINRFallback");
        amazon.put("ratingCount", "");
        amazon.put("ratingValue", "");
        amazon.put("reviewCount", "");
        amazon.put("thumbnail",getThumbnail("td#prodImageCell > a"));
        csk.put("amazon.in",amazon);
    }


    @Override
    public WebMetaData getMeta() {


        ProductMetaData meta = new ProductMetaData(super.getMeta());
        meta.setType(MetaData.PRODUCT);

        String a = "";

        if(!(a = (String) csk.get(site).get("name")).isEmpty())
        {
            meta.setName(MetaExtract.getSchemaPropery(productBlock, (String) csk.get(site).get("name")));
        }

        if(!(a = (String) csk.get(site).get("price")).isEmpty())
        {
//            meta.setPrice(Double.parseDouble(MetaExtract.getSchemaPropery(productBlock, (String) csk.get(site).get("price")).substring()));
            String temp = MetaExtract.getSchemaPropery(productBlock, (String) csk.get(site).get("price"));
            meta.setPrice(Double.parseDouble(temp.substring(temp.indexOf(" "))));
        }

        if(!(a = (String) csk.get(site).get("priceCurrency")).isEmpty())
        {
            meta.setPriceCurrency(MetaExtract.getSchemaPropery(productBlock, (String) csk.get(site).get("priceCurrency")));
        }

        if(!(a = (String) csk.get(site).get("ratingCount")).isEmpty())
        {
            meta.setRatingCount(Integer.parseInt(MetaExtract.getSchemaPropery(productBlock, (String) csk.get(site).get("ratingCount"))));
        }

        if(!(a = (String) csk.get(site).get("ratingValue")).isEmpty())
        {
            meta.setRatingValue((int)Math.ceil(Double.parseDouble(MetaExtract.getSchemaPropery(productBlock, (String) csk.get(site).get("ratingValue")))));
        }

         if(!(a = (String) csk.get(site).get("reviewCount")).isEmpty())
        {
            meta.setReviewCount(Integer.parseInt(MetaExtract.getSchemaPropery(productBlock, (String) csk.get(site).get("reviewCount"))));
        }

        if(!(a = (String) csk.get(site).get("thumbnail")).isEmpty())
        {
            meta.setThumbnail((String) csk.get(site).get("thumbnail"));
        }

        return meta;
    }

    public String getThumbnail(String a)
    {
        String thumbURL = null;
        Element metaElement;

        metaElement = productBlock.select(a).first();

        if(metaElement != null)
        {
            metaElement = metaElement.select("img").first();
            thumbURL = metaElement.absUrl("src");
        }
        System.out.println(thumbURL);

        return thumbURL;
    }
}
