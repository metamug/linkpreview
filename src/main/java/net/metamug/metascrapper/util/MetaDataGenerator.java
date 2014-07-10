/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.metamug.metascrapper.util;

import net.metamug.metascrapper.entity.ArticleMetaData;
import net.metamug.metascrapper.entity.ProductMetaData;
import net.metamug.metascrapper.entity.WebMetaData;

/**
 *
 * @author deepak
 */
public class MetaDataGenerator {
    public String generate(WebMetaData wm){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if(wm instanceof ProductMetaData){
            ProductMetaData pm = (ProductMetaData)wm;
            sb.append("\"p\":").append(pm.getPrice());
        }
        
        if(wm instanceof ArticleMetaData){
            
        }
        
        sb.append("}");
        return sb.toString();
    }
}
