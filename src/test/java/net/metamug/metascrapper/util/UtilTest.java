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
 * permission is obtained from metamug.com
 */
package net.metamug.metascrapper.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.metamug.scrapper.util.ImageUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author deepak
 */
public class UtilTest {

    public UtilTest() {
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
    public void testLargestImage() {
// Connect to website. This can be replaced with your file loading implementation
        Document doc = null;
        try {
            doc = Jsoup.connect("http://topskin.co.in/index.php?route=product/product&path=20&product_id=98").get();
        } catch (IOException ex) {
            Logger.getLogger(UtilTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Get all img tags
        Elements img = doc.getElementsByTag("img");
        List<String> list = new ArrayList<>();
        // Loop through img tags
        for (Element el : img) {
            // If alt is empty or null, add one to counter
//            if (StringUtils.isNotBlank(el.attr("alt"))) {
            list.add(el.attr("abs:src"));
//            }
        }

        System.out.println(ImageUtil.getLargestImage(list));

    }
}
