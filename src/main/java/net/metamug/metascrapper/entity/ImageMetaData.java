/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.metamug.metascrapper.entity;

/**
 *
 * @author cskksc
 */
public class ImageMetaData extends WebMetaData implements MetaData {
    
    private int height;
    private int width;
    private String author;
    private int views;
    
    public ImageMetaData() {
    }
    
    public ImageMetaData(WebMetaData webMetaData) {
        super(webMetaData);
    }

    public ImageMetaData(String title, String faviconURL, String type, String description, String thumbnail) {
        super(title, faviconURL, type, description, thumbnail);
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public void setHeight(int height)
    {
        this.height = height;
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public void setWidth(int width){
        this.width = width;
    }
    
    public String getAuthor(){
        return author;
    }
    
    public void setAuthor(String author){
        this.author = author;
    }
    
    public int getViews(){
        return views;
    }
    
    public void setViews(int views){
        this.views = views;
    }
    
    @Override
    public String toString(){
        return "ImageMetaData = { height = "
                + height + ", width = " + width + ", author = " + author + ", views = " + views ;
    }
	
}
