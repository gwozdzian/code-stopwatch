/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gwozdzian.codestopwatch.utils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import pl.gwozdzian.codestopwatch.swing.JCodeStopwatchComponent;

/**
 *
 * @author user
 */
public class StopwatchElementTest {

    String name1 = "name of event";
    long startTime1 = 990;
    long endTime1 = 1003;
    private StopwatchElement swElem1;
    
    String name2 = "name of event";
    long startTime2 = 1000;
    long endTime2 = 2010;
    private StopwatchElement swElem2;    
    
    public StopwatchElementTest() {
        

    }
    
    @BeforeClass
    public static void setUpClass() {

        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        swElem1 = new StopwatchElement(name1, startTime1, endTime1);
        swElem2 = new StopwatchElement(name2, startTime2, endTime2);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class StopwatchElement.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");

        assertEquals(name1, swElem1.getName());
        assertEquals(name2, swElem2.getName());

    }

//    /**
//     * Test of getNameFull method, of class StopwatchElement.
//     */
//    @Test
//    public void testGetNameFull() {
//        System.out.println("getNameFull");
//
//        assertEquals(name1+"", swElem1.getName());
//        assertEquals(name2, swElem2.getName());
//
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getNameShort method, of class StopwatchElement.
//     */
//    @Test
//    public void testGetNameShort() {
//        System.out.println("getNameShort");
//        StopwatchElement instance = null;
//        String expResult = "";
//        String result = instance.getNameShort();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getEndTime method, of class StopwatchElement.
//     */
//    @Test
//    public void testGetEndTime() {
//        System.out.println("getEndTime");
//        StopwatchElement instance = null;
//        long expResult = 0L;
//        long result = instance.getEndTime();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getStartTime method, of class StopwatchElement.
//     */
//    @Test
//    public void testGetStartTime() {
//        System.out.println("getStartTime");
//        StopwatchElement instance = null;
//        long expResult = 0L;
//        long result = instance.getStartTime();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDurationTime method, of class StopwatchElement.
//     */
//    @Test
//    public void testGetDurationTime() {
//        System.out.println("getDurationTime");
//        StopwatchElement instance = null;
//        long expResult = 0L;
//        long result = instance.getDurationTime();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDurationTimeText method, of class StopwatchElement.
//     */
//    @Test
//    public void testGetDurationTimeText() {
//        System.out.println("getDurationTimeText");
//        StopwatchElement instance = null;
//        String expResult = "";
//        String result = instance.getDurationTimeText();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}
