/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.entity;

import java.net.URL;
import java.util.Date;

/**
 *
 * @author deepak
 */
public class LocalBusinessMetadata extends WebMetaData implements MetaData{
    Address address;
    String priceRange;
    String paymentAccepted;
    URL url;
}

class Address{
    String address;
    String addressLocality;
    String addressCountry;
}

class Review{
    Date dateReviewed;
    String description;
    float rating;
}
