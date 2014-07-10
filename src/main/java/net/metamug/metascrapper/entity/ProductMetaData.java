/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.entity;

import com.google.gson.annotations.Expose;


/**
 *
 * @author deepak
 */
public class ProductMetaData extends WebMetaData implements MetaData {
    @Expose
    String name;
    @Expose
    float ratingValue;
    @Expose
    int ratingCount;
    @Expose
    int reviewCount;
    @Expose
    double price;
    @Expose
    String priceCurrency; //currency  
    @Expose
    String brand;
    @Expose
    String condition;

    public ProductMetaData() {
    }
    
    public ProductMetaData(WebMetaData webMetaData) {
        super(webMetaData);
    }

    public ProductMetaData(String title, String faviconURL, String type, String description, String thumbnail) {
        super(title, faviconURL, type, description, thumbnail);
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(float ratingValue) {
        this.ratingValue = ratingValue;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }
    
    public String getBrand()
    {
        return this.brand;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }
    @Override
    public String toString() {
        return "ProductMetaData{" + "name=" + name + ", ratingValue=" + ratingValue + ", ratingCount=" + ratingCount + ", reviewCount=" + reviewCount + ", price=" + price + ", priceCurrency=" + priceCurrency + '}';
    }
    
    
}
class Offer {
    @Expose
    double price;
    @Expose
    String priceCurrency; //currency  
}