/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.util;

import net.metamug.metascrapper.entity.MetaImage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author deepak
 */
public class ImageTest {

    public ImageTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testProfilePicture() {
        String testImage = "http://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png";
        String imageId = StorageManager.uploadPublisherPicture(testImage);
        assertNotNull(imageId);
        String uploadedImageURL = StorageManager.IMAGE_SITE + StorageManager.PUBLISHER_MUGSHOT_FOLDER
                + imageId;
        System.out.println(uploadedImageURL);
        uploadedImageURL = StorageManager.IMAGE_SITE + StorageManager.PUBLISHER_MUGSHOT_TILE_FOLDER
                + imageId;
        System.out.println(uploadedImageURL);
    }

    @Test
    public void testEditProfilePicture() {
        String testImage = "http://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png";
        MetaImage mi = new MetaImage();
        mi.setUrl(testImage);
        int size=160;
        mi.setHeight(size);
        mi.setWidth(size);
        String imageId = StorageManager.uploadPublisherImage(mi, 92, 21);
        assertNotNull(imageId);
        String uploadedImageURL = StorageManager.IMAGE_SITE + StorageManager.PUBLISHER_MUGSHOT_FOLDER
                + imageId;
        System.out.println(uploadedImageURL);
        uploadedImageURL = StorageManager.IMAGE_SITE + StorageManager.PUBLISHER_MUGSHOT_TILE_FOLDER
                + imageId;
        System.out.println(uploadedImageURL);
    }

    @Test
    public void testPostImage() {
        String testImage = "http://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png";

        MetaImage mi = new MetaImage();
        mi.setUrl(testImage);

        String uploadedImageURL = StorageManager.IMAGE_SITE + StorageManager.POST_MUGSHOT_FOLDER
                + StorageManager.uploadPostImage(mi).getId();
        assertNotNull(uploadedImageURL);
        System.out.println(uploadedImageURL);
    }

    @Test
    public void testFavicon() {
        String testImage = "http://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png";
        MetaImage image = StorageManager.uploadFavicon(testImage);
        String uploadedImageURL = StorageManager.IMAGE_SITE + StorageManager.FAVICON_IMAGE_FOLDER
                + image.getId();
        assertNotNull(uploadedImageURL);
        System.out.println(uploadedImageURL);
    }
}
