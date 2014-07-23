/**
 * ***********************************************************************
 *
 * METAMUG.COM CONFIDENTIAL __________________
 *
 * [2013] - [2014] metamug.com All Rights Reserved.
 *
 * NOTICE: All information contained herein is, and remains the property of
 * metamug.com and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to metamug.com and its suppliers
 * and may be covered by Indian and Foreign Patents, patents in process, and are
 * protected by trade secret or copyright law. Dissemination of this information
 * or reproduction of this material is strictly forbidden unless prior written
 * permission is obtained from Adobe Systems Incorporated.
 */
package net.metamug.metascrapper.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author deepak
 */
public class DownloadManagerTest {

    public DownloadManagerTest() {
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

    /**
     * Test of getResponse method, of class DownloadManager.
     */
    @Test
    public void testGetResponse() {
        try {
            String url = "http://t.co/i5dE1K4vSs";
            Response response = Jsoup.connect(url).followRedirects(false).execute();
            System.out.println(response.header("location"));
        } catch (IOException ex) {
            Logger.getLogger(DownloadManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
