/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gwozdzian.codestopwatch;

import pl.gwozdzian.codestopwatch.utils.StopwatchElement;
import pl.gwozdzian.codestopwatch.CodeStopwatch;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import pl.gwozdzian.codestopwatch.swing.JCodeStopwatchComponent;

/**
 *
 * @author user
 */
public class StopwatchUserInterfaceTest extends JFrame{

    private JScrollPane scroolPane;

    public StopwatchUserInterfaceTest(){
        
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUI();
            }
        });        
        
    }
    
    


    protected void createGUI() {
        scroolPane = new JScrollPane();
        this.setSize(300, 200);
        this.setMinimumSize(new Dimension(300,200));
        this.setMaximumSize(new Dimension(500,300));

        
        
        createComponents();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
    }

    private void createComponents() {
        
        
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


        this.getContentPane().add(new JCodeStopwatchComponent(swElemList));
        
    }
        
  
    
    
    
    
    
    
    
    
    public static void main(String[] args) {
           CodeStopwatch.setTraceToConsole(false);
           new StopwatchUserInterfaceTest();
        
        
    }    
    
    
    
    
    
}
