/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.metamug.metascrapper.entity;

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

    @Test
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
        String url = "http://www.flipkart.com/moto-e/p/itmdvuwsybgnbtha?pid=MOBDVHC6XKKPZ3GZ&otracker=hp_mod_electronics_bestseller_prd_img";
        ProductMetaData p = (ProductMetaData) MetaDataFactory.create(url);
        
        System.out.println(p.getName());
        System.out.println(p.getPrice());
        System.out.println(p.getPriceCurrency());
        System.out.println(p.getRatingCount());
//        System.out.println(p.getRatingValue());
        System.out.println(p.getReviewCount());
    }
    
}
