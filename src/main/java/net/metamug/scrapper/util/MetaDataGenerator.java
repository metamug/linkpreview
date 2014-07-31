/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.metamug.scrapper.util;

import net.metamug.scrapper.entity.ArticleMetaData;
import net.metamug.scrapper.entity.ProductMetaData;
import net.metamug.scrapper.entity.WebMetaData;

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
