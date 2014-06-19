/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.strategy;

import net.metamug.metascrapper.entity.ProductMetaData;
import net.metamug.metascrapper.entity.WebMetaData;
import net.metamug.metascrapper.util.MetaExtract;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author deepak This strategy pulls product metadata from a webpage provided
 * the page confers to http://schema.org/Product specifications
 */
public class ProductMetaStrategy extends WebMetaStrategy {

    Element productBlock;

    public ProductMetaStrategy(Document doc, String url, Element metablock) {
        super(doc, url);
        this.productBlock = metablock;
    }

    @Override
    public WebMetaData getMeta() {

        ProductMetaData meta = new ProductMetaData(super.getMeta());
        
        meta.setName(MetaExtract.getSchemaPropery(productBlock, "[itemprop=name]"));
        
//         meta.setPrice(Double.parseDouble(MetaExtract.getSchemaPropery(productBlock,
//                 "span.pprice").substring(3)));
//
//         meta.setPrice(Double.parseDouble(MetaExtract.getFirstText(productBlock,
//                 "[itemprop=price]")));

//         meta.setPriceCurrency(MetaExtract.getSchemaPropery(productBlock,
//                 "[itemprop=priceCurrency]"));
//
//         meta.setType(MetaData.PRODUCT);
        
//         meta.setRatingValue(Float.parseFloat(
//                 MetaExtract.getSchemaPropery(productBlock,
//                 "[itemprop=ratingValue]")));

//         String temp;
//        
//         if ((temp = MetaExtract.getSchemaPropery(productBlock, "[itemprop=ratingCount]")) != null) {
//             meta.setRatingCount(Integer.parseInt(temp));
//         }
//
//         if ((temp = MetaExtract.getSchemaPropery(productBlock, "[itemprop=reviewCount]")) != null) {
//             meta.setReviewCount(Integer.parseInt(temp));
//         }
		
        return meta;
    }
}
