/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.scrapper.strategy;

import java.util.HashMap;
import net.metamug.scrapper.entity.MetaData;
import net.metamug.scrapper.entity.ProductMetaData;
import net.metamug.scrapper.entity.WebMetaData;
import net.metamug.scrapper.util.ScrapperUtil;
import net.metamug.scrapper.util.StrategyHelper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author deepak This strategy pulls product metadata from a webpage provided
 * the page confers to http://schema.org/Product specifications
 */
public class ProductMetaStrategy extends WebMetaStrategy {

    Element productBlock;
    HashMap<String, HashMap> productSourceMap = new HashMap<>();
    HashMap<String, String> flipkart = new HashMap<>();
    HashMap<String, String> amazon = new HashMap<>();
    HashMap<String, String> snapdeal = new HashMap<>();

    String site;

    public ProductMetaStrategy(Document doc, String url, Element metablock) {
        super(doc, url);
        this.productBlock = metablock;
        site = ScrapperUtil.getBaseDomain(url);

        flipkart.put("name", "[itemprop=name]");
        flipkart.put("price", "span.pprice");
        flipkart.put("priceCurrency", "[itemprop=priceCurrency]");
        flipkart.put("ratingCount", "[itemprop=ratingCount");
        flipkart.put("reviewCount", "[itemprop=reviewCount]");
        flipkart.put("ratingValue", "[itemprop=ratingValue]");
        flipkart.put("thumbnail", getThumbnailURL("div.visible-image-small"));
        productSourceMap.put("flipkart.com", flipkart);

        amazon.put("name", "div.buying > h1.parseasinTitle > span#btAsinTitle > span");
        amazon.put("price", "span#actualPriceValue > b.priceLarge > span");
        amazon.put("priceCurrency", "span#actualPriceValue > b.priceLarge > span > span.currencyINRFallback");
        amazon.put("ratingCount", "");
        amazon.put("ratingValue", "");
        amazon.put("reviewCount", "");
        amazon.put("thumbnail", getThumbnailURL("td#prodImageCell > a"));
        productSourceMap.put("amazon.in", amazon);

        snapdeal.put("name", "[itemprop=name]");
//        snapdeal.put("price","[itemprop=price]");
        snapdeal.put("price", "");
        snapdeal.put("priceCurrency", "[itemprop=priceCurrency]");
        snapdeal.put("ratingCount", "");
        snapdeal.put("ratingValue", "");
        snapdeal.put("reviewCount", "");
        snapdeal.put("thumbnail", getThumbnailURL("div.product-main-image"));
        productSourceMap.put("snapdeal.com", snapdeal);

    }

    @Override
    public WebMetaData getMeta() {

        ProductMetaData meta = new ProductMetaData(super.getMeta());
        meta.setType(MetaData.PRODUCT);

        String a = "";

        if (!(a = (String) productSourceMap.get(site).get("name")).isEmpty()) {
            meta.setName(StrategyHelper.getSchemaPropery(productBlock, (String) productSourceMap.get(site).get("name")));
        }

        if (!(a = (String) productSourceMap.get(site).get("price")).isEmpty()) {
//            meta.setPrice(Double.parseDouble(MetaExtract.getSchemaPropery(productBlock, (String) productSourceMap.get(site).get("price")).substring()));
            String temp = StrategyHelper.getSchemaPropery(productBlock, (String) productSourceMap.get(site).get("price"));
            meta.setPrice(Double.parseDouble(temp.substring(temp.indexOf(" ")).replace(",", "")));
        }

        if (!(a = (String) productSourceMap.get(site).get("priceCurrency")).isEmpty()) {
            meta.setPriceCurrency(StrategyHelper.getSchemaPropery(productBlock, (String) productSourceMap.get(site).get("priceCurrency")));
        }

        if (!(a = (String) productSourceMap.get(site).get("ratingCount")).isEmpty()) {
            meta.setRatingCount(Integer.parseInt(StrategyHelper.getSchemaPropery(productBlock, (String) productSourceMap.get(site).get("ratingCount"))));
        }

        if (!(a = (String) productSourceMap.get(site).get("ratingValue")).isEmpty()) {
            meta.setRatingValue((int) Math.ceil(Double.parseDouble(StrategyHelper.getSchemaPropery(productBlock, (String) productSourceMap.get(site).get("ratingValue")))));
        }

        if (!(a = (String) productSourceMap.get(site).get("reviewCount")).isEmpty()) {
            meta.setReviewCount(Integer.parseInt(StrategyHelper.getSchemaPropery(productBlock, (String) productSourceMap.get(site).get("reviewCount"))));
        }

        if (!(a = (String) productSourceMap.get(site).get("thumbnail")).isEmpty()) {
            meta.setPicture((String) productSourceMap.get(site).get("thumbnail"));
        }

        if (site.equals("amazon.in")) {
            String b = StrategyHelper.getFirstAttributeValue(productBlock, "span.swSprite", "title");
            b = b.substring(0, b.indexOf(" "));
            meta.setRatingValue(Float.parseFloat(b));

            String c = StrategyHelper.getFirstText(productBlock, "span.asinReviewsSummary + a");
            c = c.substring(0, c.indexOf(" "));
            meta.setReviewCount(Integer.parseInt(c));

            meta.setRatingCount(Integer.parseInt(c));
        }

        if (site.contains("snapdeal")) {
            String b = StrategyHelper.getSchemaPropery(productBlock, "div.reviewBar > div.lfloat > div + div > span");
            meta.setRatingCount(Integer.parseInt(b.substring(0, b.indexOf(" "))));

            meta.setPrice(Double.parseDouble(StrategyHelper.getSchemaPropery(productBlock, "[itemprop=price]")));

            b = StrategyHelper.getFirstAttributeValue(productBlock, "div.reviewBar > div.lfloat > div", "ratings");
            meta.setRatingValue(Float.parseFloat(b));

            b = StrategyHelper.getSchemaPropery(productBlock, "span.showRatingTooltip");
            meta.setReviewCount(Integer.parseInt(b.substring(0, b.indexOf(" "))));

        }

        return meta;
    }

    public String getThumbnailURL(String a) {
        String thumbURL = null;
        Element metaElement;

        metaElement = productBlock.select(a).first();

        if (metaElement != null) {
            metaElement = metaElement.select("img").first();
            thumbURL = metaElement.absUrl("src");
        }
//        System.out.println(thumbURL);

        return thumbURL;
    }
}
