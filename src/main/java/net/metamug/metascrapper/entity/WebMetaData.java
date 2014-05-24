/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.entity;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.imgscalr.Scalr;
import static org.imgscalr.Scalr.OP_ANTIALIAS;
import static org.imgscalr.Scalr.OP_BRIGHTER;
import static org.imgscalr.Scalr.resize;

//flipkart,snapdeal

/**
 *
 * @author bhatt
 */
public class WebMetaData implements MetaData {

    private String title;
    private MetaImage domain;
    private String type;
    private String description;
    private MetaImage thumbnail;
    
    
    public WebMetaData() {
    }
    
    public WebMetaData(WebMetaData webMetaData) {
        this.title = webMetaData.title;
        this.description = webMetaData.description;
        this.thumbnail = webMetaData.thumbnail;
        this.type = webMetaData.type;
        this.domain = webMetaData.domain;
    }

    public WebMetaData(String title, String faviconURL, String type, String description, String thumbnail) {
        this.title = title;
        setDomain(faviconURL);
        this.type = type;
        this.description = description;
        setThumbnail(thumbnail);
    }

    @Override
    public String toString() {
        String metaString = "";

        metaString += "Title: " + title;
        metaString += "\nFaviconURL: " + domain;
        metaString += "\nType: " + type;
        metaString += "\nDescription: " + description;
        metaString += "\nThumbnailURL: " + thumbnail.getUrl();

        return metaString;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = StringEscapeUtils.escapeHtml4(title);
    }

    /**
     * @return the faviconURL
     */
    public MetaImage getDomain() {
        return domain;
    }

    /**
     * @param faviconURL the faviconURL to set
     */
    public void setDomain(MetaImage faviconURL) {
        this.domain = faviconURL;
    }
    
    public void setDomain(String faviconURL) {
        this.domain = new MetaImage("0");
        this.domain.setUrl(faviconURL);
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

    public MetaImage getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnailUrl) {
        MetaImage im = new MetaImage();
        im = new MetaImage();
        //im.setId(RandomStringUtils.randomAlphanumeric(20) + "_" + RandomStringUtils.randomAlphanumeric(20));
//        im.setId("00000000000000000000" + "_" + "00000000000000000000");
        im.setId("0");
        im.setUrl(thumbnailUrl);
        this.thumbnail = im;
    }
    
    public void setThumbnail(MetaImage im) {
        this.thumbnail = im;
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



