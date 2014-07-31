/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.scrapper.strategy;

import net.metamug.scrapper.entity.MetaData;
import net.metamug.scrapper.entity.WebMetaData;

/**
 *
 * @author deepak
 */
public interface MetaStrategy {
    public MetaData getMeta();
}
