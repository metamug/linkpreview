/*
 * 
 */
package net.metamug.scrapper.entity;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author bhatt
 */
public class WebMetaData implements MetaData {
    

    private String title;
    private String domain;
    private String type;
    private String description;
    private String picture;
    

    public WebMetaData() {
    }

    public WebMetaData(String title, String domain, String type, String description, String picture) {
        this.title = title;
        this.domain = domain;
        this.type = type;
        this.description = description;
        this.picture = picture;
    }

    public WebMetaData(WebMetaData webMetaData) {
        this.title = webMetaData.title;
        this.description = webMetaData.description;
        this.picture = webMetaData.picture;
        this.type = webMetaData.type;
        this.domain = webMetaData.domain;
    }

    @Override
    public String toString() {
        String metaString = "";

        metaString += "Title: " + title;
        metaString += "\nFaviconURL: " + domain;
        metaString += "\nType: " + type;
        metaString += "\nDescription: " + description;
//        for (String p : pictures) {
        metaString += "\nPictures: " + picture;
//        }

        return metaString;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = StringEscapeUtils.escapeHtml4(description);
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

}
