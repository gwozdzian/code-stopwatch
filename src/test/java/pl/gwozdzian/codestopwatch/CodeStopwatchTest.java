/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gwozdzian.codestopwatch;

import pl.gwozdzian.codestopwatch.CodeStopwatch;
import pl.gwozdzian.codestopwatch.utils.StopwatchElement;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import pl.gwozdzian.codestopwatch.swing.StopwatchModel;

/**
 *
 * @author user
 */
public class CodeStopwatchTest {
    
    public CodeStopwatchTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of start method, of class CodeStopwatch.
     */
    @Test
    public void testStart() {
        
//        List<String> lst = new ArrayList();
//        lst.add(Tools.getMethodName(0));
//        lst.add(Tools.getMethodName(1));
//        lst.add(Tools.getMethodName(2));
//        lst.add(Tools.getMethodName(3));
//        lst.add(Tools.getMethodName(4));
//        lst.add(Tools.getMethodName(5));
//        Tools.trace("Method names in testStart()");
//        Tools.trace(lst);
        
        
        String excepted = "CodeStopwatchTest.testStart()";
        String actual = CodeStopwatch.start();
        assertEquals(excepted, actual);
    }

    /**
     * Test of start method, of class CodeStopwatch.
     */
    @Test
    public void testStart_String() {
    }

    /**
     * Test of computeLayerNumbers method, of class StopwatchModel.
     */
    @Test
    public void testComputeLayerNumbers() {
        System.out.println("calculateLayerNumbers");
        List<StopwatchElement> swElemList = new ArrayList();
        //new StopwatchElement(String name, long startTime, long endTime);
        swElemList.add(new StopwatchElement("Elem_0", 0, 10000));
        swElemList.add(new StopwatchElement("Elem_1", 0, 3000));
        swElemList.add(new StopwatchElement("Elem_2", 2000, 4000));
        swElemList.add(new StopwatchElement("Elem_3", 3000, 3500));
        swElemList.add(new StopwatchElement("Elem_4", 5000, 7000));
        swElemList.add(new StopwatchElement("Elem_5", 7000, 8000));
        swElemList.add(new StopwatchElement("Elem_6", 7500, 8000));
        swElemList.add(new StopwatchElement("Elem_7", 8000, 11000));
        swElemList.add(new StopwatchElement("Elem_8", 12000, 13000));
        System.out.println("swElemList created");
        CodeStopwatch.computeLayerNumbers(swElemList);
        // TODO review the generated test code and remove the default call to fail.
        int layerCount = 0;
        for (StopwatchElement currElem : swElemList) {
            layerCount = Math.max(layerCount, currElem.getLayerNumber());
        }
        org.junit.Assert.assertEquals(3, layerCount);
        Assert.assertEquals(0, swElemList.get(0).getLayerNumber());
        assertEquals(1, swElemList.get(1).getLayerNumber());
        assertEquals(2, swElemList.get(2).getLayerNumber());
        assertEquals(3, swElemList.get(3).getLayerNumber());
        assertEquals(1, swElemList.get(4).getLayerNumber());
        assertEquals(1, swElemList.get(5).getLayerNumber());
        assertEquals(2, swElemList.get(6).getLayerNumber());
        assertEquals(1, swElemList.get(7).getLayerNumber());
        assertEquals(0, swElemList.get(8).getLayerNumber());
        //fail("The test case is a prototype.");
    }

    /**
     * Test of computeGroupByEmptySpace method, of class StopwatchModel.
     */
    @Test
    public void testComputeGroupByEmptySpace() {
        System.out.println("computeGroupByEmptySpace");
        List<StopwatchElement> swElemList = new ArrayList();
        //new StopwatchElement(String name, long startTime, long endTime);
        swElemList.add(new StopwatchElement("Elem_0", 0, 3000));
        swElemList.add(new StopwatchElement("Elem_1", 2000, 5000));
        swElemList.add(new StopwatchElement("Elem_2", 3000, 3500));
        swElemList.add(new StopwatchElement("Elem_3", 4000, 4500));
        swElemList.add(new StopwatchElement("Elem_4", 4300, 4500));
        swElemList.add(new StopwatchElement("Elem_5", 7000, 8000));
        swElemList.add(new StopwatchElement("Elem_6", 8000, 11000));
        swElemList.add(new StopwatchElement("Elem_7", 9000, 9500));
        swElemList.add(new StopwatchElement("Elem_8", 13000, 15000));
        swElemList.add(new StopwatchElement("Elem_9", 13500, 15000));
        swElemList.add(new StopwatchElement("Elem_10", 13500, 13800));
        System.out.println("swElemList created");
        
        CodeStopwatch.computeLayerNumbers(swElemList);
        assertEquals(0, swElemList.get(5).getLayerNumber());
        assertEquals(0, swElemList.get(6).getLayerNumber());
        assertEquals(2, swElemList.get(10).getLayerNumber());
        List<List<StopwatchElement>> expResult = null;
        CodeStopwatch.computeGroupByEmptySpace(swElemList);
        assertEquals(0, swElemList.get(0).getGroupNumber());
        assertEquals(0, swElemList.get(1).getGroupNumber());
        assertEquals(0, swElemList.get(2).getGroupNumber());
        assertEquals(0, swElemList.get(3).getGroupNumber());
        assertEquals(0, swElemList.get(4).getGroupNumber());
        assertEquals(1, swElemList.get(5).getGroupNumber());
        assertEquals(1, swElemList.get(6).getGroupNumber());
        assertEquals(1, swElemList.get(7).getGroupNumber());
        assertEquals(2, swElemList.get(8).getGroupNumber());
        assertEquals(2, swElemList.get(9).getGroupNumber());
        assertEquals(2, swElemList.get(10).getGroupNumber());
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
