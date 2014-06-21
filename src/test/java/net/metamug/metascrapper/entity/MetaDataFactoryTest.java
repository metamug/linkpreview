/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.metamug.metascrapper.entity;

import net.metamug.metascrapper.util.MetaExtract;
import org.junit.After;
import org.junit.AfterClass;
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
    
    @Test
    public void testProduct()
    {
//        String url = "http://www.flipkart.com/moto-e/p/itmdvuwsybgnbtha?pid=MOBDVHC6XKKPZ3GZ&otracker=hp_mod_electronics_bestseller_prd_img";
//        String url = "http://www.flipkart.com/htc-desire-210/p/itmdvzg8bnph9xja?pid=MOBDVZ6TDYZCKRDC&icmpid=reco_pp_same_mobile_1&ppid=MOBDVHC6XKKPZ3GZ";

        String url = "http://www.amazon.in/gp/product/0984756302/ref=s9_simh_gw_p14_d1_i3?pf_rd_m=A1VBAL9TL5WCBF&pf_rd_s=center-2&pf_rd_r=1365DR4RT60MNYXBNC97&pf_rd_t=101&pf_rd_p=402519107&pf_rd_i=1320006031";

        ProductMetaData p = (ProductMetaData) MetaDataFactory.create(url);
        
        System.out.println(p.getName());
        System.out.println(p.getPrice());
        System.out.println(p.getPriceCurrency());
        System.out.println(p.getRatingCount());
        System.out.println(p.getRatingValue());
        System.out.println(p.getReviewCount());
    }
    
//    @Test
    public void testImage()
    {
        String url = "https://www.flickr.com/photos/spoutnik53/14454170711/in/explore-2014-06-19";
        ImageMetaData i = (ImageMetaData) MetaDataFactory.create(url);

        System.out.println(i.getTitle());
        System.out.println(i.getAuthor());
//        System.out.println(i.getViews());
    }
    
}
