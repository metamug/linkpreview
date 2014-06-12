/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.metamug.metascrapper.entity;

import net.metamug.metascrapper.strategy.WebMetaStrategy;
import org.jsoup.nodes.Document;
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
    public void testCreate() {
        System.out.println("create");
        String url = "http://stackoverflow.com/questions/8710619/java-operator";
        WebMetaData result = MetaDataFactory.create(url);
        System.out.println(result.getDescription());
        System.out.println(result.getThumbnail().getUrl());
    }
    
    
    /**
     * Test of getMetaStrategy method, of class MetaDataFactory.
     */
    //@Test
//    public void testGetMetaStrategy() {
//        System.out.println("getMetaStrategy");
//        Document doc = null;
//        String url = "";
//        WebMetaStrategy expResult = null;
//        WebMetaStrategy result = MetaDataFactory.getMetaStrategy(doc, url);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
