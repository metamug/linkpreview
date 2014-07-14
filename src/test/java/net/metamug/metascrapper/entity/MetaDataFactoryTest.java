/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.entity;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author deepak
 */
public class MetaDataFactoryTest {

    public MetaDataFactoryTest() {
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
     * Test of create method, of class MetaDataFactory.
     */
//    @Test
    public void testArticle() {
//        System.out.println("create");
        String url = "http://stackoverflow.com/questions/5963269/how-to-make-a-great-r-reproducible-example";

        WebMetaData result = MetaDataFactory.create(url);
//        System.out.println(result.getDescription());       
//        System.out.println(result.getThumbnail().getUrl());
    }

//    @Test
    public void testProduct() {

        /*The following test data will be generated from an external file in future*/
        List<String> testData = new ArrayList<>();
        testData.add("http://www.flipkart.com/moto-e/p/itmdvuwsybgnbtha?pid=MOBDVHC6XKKPZ3GZ&otracker=hp_mod_electronics_bestseller_prd_img");
//        testData.add("http://www.flipkart.com/htc-desire-210/p/itmdvzg8bnph9xja?pid=MOBDVZ6TDYZCKRDC&icmpid=reco_pp_same_mobile_1&ppid=MOBDVHC6XKKPZ3GZ");
        testData.add("http://www.flipkart.com/apple-16gb-ipad-2-wi-fi/p/itmdfyjgphytdyfe?pid=TABDEWZ3Q9PRYFZH&srno=b_1&ref=338cc5dd-cd83-41f4-bd97-c63f95373622");
//        testData.add("http://www.amazon.in/gp/product/0981770371/ref=s9_simh_gw_p14_d3_i1?pf_rd_m=A1VBAL9TL5WCBF&pf_rd_s=center-2&pf_rd_r=1W1G1M2R4VGWVAR1K067&pf_rd_t=101&pf_rd_p=402519107&pf_rd_i=1320006031");

        testData.add("http://www.snapdeal.com/product/samsung-galaxy-s5-gold/435578567");

        for (String url : testData) {
            ProductMetaData p = (ProductMetaData) MetaDataFactory.create(url);
            System.out.println("===== Product Metadata =====");
            System.out.println("Product Name: " + p.getName());
            System.out.println("Product Price: " + p.getPrice());
            System.out.println("Product Price Currency: " + p.getPriceCurrency());
            System.out.println("Product Rating Count: " + p.getRatingCount());
            System.out.print("Product Rating: ");

            //To print stars. "\u00BD" for printing half stars :)
            for (int i = 0; i < p.getRatingValue(); i++) {
                System.out.print("\u2605");
            }
            System.out.println("(" + p.getRatingValue() + ")");
            System.out.println("Product Review Count: " + p.getReviewCount());
            System.out.println("Thumnnail src: " + p.getPicture());
        }
    }

//    @Test
    public void testImage() {
        String url = "https://www.flickr.com/photos/spoutnik53/14454170711/in/explore-2014-06-19";
        ImageMetaData i = (ImageMetaData) MetaDataFactory.create(url);

        System.out.println(i.getTitle());
        System.out.println(i.getAuthor());
//        System.out.println(i.getViews());
    }

    @Test
    public void testWebGeneric() {
        /*The following test data will be generated from an external file in future*/
        List<String> testData = new ArrayList<>();
//        testData.add("http://facebook.com/");
        
//        testData.add("http://venturebeat.com/2014/06/24/how-to-know-if-youre-learning-the-right-programming-lan/guage/");
//        testData.add("http://opennews.kzhu.io/map-disputes/?_ga=1.48243875.420098949.1397702661");
//        testData.add("http://snapdeal.com");
//        testData.add("http://www.laurencegellert.com/2013/05/pros-and-cons-of-rds-vs-ec2-for-mysql-with-aws/");
//        testData.add("http://www.gujaratuniversity.org.in/web/index.asp");
        testData.add("http://www.fool.com/ecap/the_motley_fool/homerun-warren-buffett-tells-you-how-to-turn-40-2/?paid=7283&psource=esatab7410860091&waid=7284&wsource=esatabwdg0860079&utm_source=taboola&utm_medium=referral");
//        testData.add("http://locust.io/");
//        testData.add("http://www.theserverside.com/feature/Practical-Challenges-of-Profiler-Integration-with-Java-J2EE-Applications");
//        testData.add("http://cssdeck.com/labs/google-fonts-css-longshade-icon");
//        testData.add("http://pmindia.nic.in/details123.php");
//        testData.add("http://pmindia.nic.in/");
//        testData.add("http://mvnrepository.com/artifact/com.google.zxing/core/2.0");
//        testData.add("http://rc3.org/2011/12/02/using-hmac-to-authenticate-web-service-requests/");
//        testData.add("https://auth0.com/blog/2014/01/07/angularjs-authentication-with-cookies-vs-token/");
//        testData.add("http://www.menslifestylenetwork.com/5-movies-that-make-even-a-real-man-cry");
        testData.add("https://discussions.apple.com/message/16597459");
//        testData.add("http://www.quora.com/Computer-Keyboards/Why-is-the-keyboard-layout-Q-W-E-R-T-Y-and-not-simply-A-B-C-D-E-F");
        for (String url : testData) {
            WebMetaData p = (WebMetaData) MetaDataFactory.create(url);
            
            System.out.println("===== Generic Web Metadata =====");
            System.out.println("Title: " + p.getTitle());
            System.out.println("Description: " + p.getDescription());
            System.out.println("Thumbnail URL: " + p.getPicture());
        }
    }

//    @Test
    public void testWikipedia() {
        /*The following test data will be generated from an external file in future*/
        List<String> testData = new ArrayList<>();
        testData.add("http://en.wikipedia.org/wiki/Scraper_site");
        testData.add("http://en.wikipedia.org/wiki/Quick_Heal");
        for (String url : testData) {
            WebMetaData p = (WebMetaData) MetaDataFactory.create(url);
            System.out.println("===== Wikipedia Metadata =====");
            System.out.println("Wikipedia Title: " + p.getTitle());
            System.out.println("Wikipedia Description: " + p.getDescription());
            System.out.println("Wikipedia Thumbnail URL: " + p.getPicture());
        }
    }

//    @Test
    public void testAudio() {
        /*The following test data will be generated from an external file in future*/
        List<String> testData = new ArrayList<>();
        testData.add("http://newmuz.net/uploads/files/1339874603_maroon5-onemorenight.mp3");
        for (String url : testData) {
            AudioMetaData p = (AudioMetaData) MetaDataFactory.create(url);
            System.out.println("===== Audio Metadata =====");
            System.out.println("Audio Title: " + p.getTitle());
            System.out.println("Audio Description: " + p.getDescription());
            System.out.println("Audio Thumbnail URL: " + p.getPicture());
        }
    }

//    @Test
    public void testPDF() {
        /*The following test data will be generated from an external file in future*/
        List<String> testData = new ArrayList<>();
        testData.add("http://www.dadsgarage.com/~/media/Files/example.ashx");
        for (String url : testData) {
            AudioMetaData p = (AudioMetaData) MetaDataFactory.create(url);
            System.out.println("===== PDF Metadata =====");
            System.out.println("PDF Title: " + p.getTitle());
            System.out.println("PDF Description: " + p.getDescription());
            System.out.println("Audio Thumbnail URL: " + p.getPicture());
        }
    }

}
