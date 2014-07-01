/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.util;

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
        String result = StorageManager.uploadMugShot(testImage);
        assertNotNull(result);
        System.out.println(result);
    }
}
