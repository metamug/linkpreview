/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.strategy;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.metamug.metascrapper.entity.AudioMetaData;
import net.metamug.metascrapper.entity.MetaData;
import net.metamug.metascrapper.entity.MetaImage;
import net.metamug.metascrapper.entity.WebMetaData;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.nodes.Document;

/**
 *
 * @author deepak
 */
public class AudioMetaStrategy {

    Mp3File song;

    public AudioMetaStrategy(String url) {
        try {
            song = new Mp3File(url);
        } catch (IOException | UnsupportedTagException | InvalidDataException ex) {
            Logger.getLogger(AudioMetaStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public WebMetaData getMeta() throws IOException {
        AudioMetaData metadata = new AudioMetaData();
        if (song.hasId3v2Tag()) {

            ID3v2 id3v2tag = song.getId3v2Tag();
            byte[] imageData = id3v2tag.getAlbumImage();
//            MetaImage albumArt = new MetaImage();
//            String fileName = "metamug_audio_art" + RandomStringUtils.random(32) + "_" + RandomStringUtils.random(20) + ".png";
//            albumArt.setId(fileName);
//            albumArt.setUrl(StorageManager.upload(new ByteArrayInputStream(imageData), imageData.length, fileName));
            //converting the bytes to an image
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
//            albumArt.setHeight((short) img.getHeight());
//            albumArt.setWidth((short) img.getWidth());
            //metadata.setPicture(StorageManager.upload(img));

            metadata.setTitle(id3v2tag.getTitle());
            metadata.setAlbum(id3v2tag.getAlbum());
            metadata.setArtist(id3v2tag.getAlbumArtist());
            metadata.setDescription(id3v2tag.getComment() + " (c) " + id3v2tag.getCopyright());

        }
        return metadata;
    }

}
