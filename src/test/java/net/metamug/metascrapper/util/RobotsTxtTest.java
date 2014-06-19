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
 * @author cskksc
 */
public class RobotsTxtTest {
    
    public RobotsTxtTest() {
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
     * Test of canAccess method, of class RobotsTxt.
     */
    @Test
    public void testCanAccess() {        
        
        // expected: false
        String url = "http://stackoverflow.com/search?q=jdk";
        RobotsTxt robo = new RobotsTxt(url);
        boolean expResult = false;
        boolean result = robo.canAccess(url);
        assertEquals(expResult, result);
        
        url="https://stackoverflow.com/users/login?returnurl=http%3a%2f%2fstackoverflow.com%2f";
        robo = new RobotsTxt(url);
        expResult = false;
        result=robo.canAccess(url);
        assertEquals(expResult, result);
        
        
        // expected: true
        url="http://stackoverflow.com/questions/11227809/why-is-processing-a-sorted-array-faster-than-an-unsorted-array";
        robo = new RobotsTxt(url);
        expResult = true;
        result=robo.canAccess(url);
        assertEquals(expResult, result);
        
       // vogella.com dosent have rabots.txt
        url="http://www.vogella.com/tutorials/ApacheHttpClient/article.html";
        robo = new RobotsTxt(url);
        expResult = true;
        result=robo.canAccess(url);
        assertEquals(expResult, result);
        
    }
    
}
