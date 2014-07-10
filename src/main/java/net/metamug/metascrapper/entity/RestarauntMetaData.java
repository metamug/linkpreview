/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.entity;

import com.google.gson.annotations.Expose;

/**
 *
 * @author deepak
 * The metadata class confers to http://schema.org and the variable names
 * are itemprop attributes.
 */
public class RestarauntMetaData extends LocalBusinessMetadata{
    @Expose
    String name;
    @Expose
    String acceptsReservations;
    @Expose
    String servesCuisine;
}
