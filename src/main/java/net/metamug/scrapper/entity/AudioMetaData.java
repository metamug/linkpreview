/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.scrapper.entity;

import com.google.gson.annotations.Expose;
import java.util.Objects;

/**
 *
 * @author deepak Audio meta data according to ID3v1
 * http://en.wikipedia.org/wiki/ID3
 */
public class AudioMetaData extends WebMetaData{

    @Expose
    String artist; //30 characters of the artist
    @Expose
    String album; //30 characters of the album name
    @Expose
    int year; //A four-digit year
    

    public AudioMetaData() {
    }

    public AudioMetaData(WebMetaData webMetaData) {
        super(webMetaData);
    }

    
    public AudioMetaData(String artist, String album, int year, WebMetaData webMetaData) {
        super(webMetaData);
        this.artist = artist;
        this.album = album;
        this.year = year;
    }    

  
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


}
