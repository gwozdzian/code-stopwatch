/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gwozdzian.codestopwatch.swing;

import java.util.ArrayList;
import java.util.List;
import pl.gwozdzian.codestopwatch.utils.StopwatchElement;

/**
 * It contains and manages events group (as a list of {@link StopwatchElement}) that occured in one time sequence with no breaks between them.
 * Provide properities of this group
 * @author gwozdzian
 */
public class GroupStopwatchElement {




    private List<StopwatchElement> swElemList;
    private int groupXOffset;
    
    private int groupXEndOffset;

    /**
     *  Default constructor
     */
    public GroupStopwatchElement() {
        swElemList = new ArrayList();
    }   
    
    /**
     * Add StopwatchElement to this events group
     * @param swElem StopwatchElement to be added
     */
    public void addStopwatchElement(StopwatchElement swElem){
        swElemList.add(swElem);
    }

    /**
     * 
     * @return list of StopwatchElement belong to this events group
     */
    public List<StopwatchElement> getStopwatchElementsList() {
        return swElemList;
    }
    
    /**
     * Start time of this events group
     * @return 
     */
    public long getStartTime(){
        return swElemList.get(0).getStartTime();
    }
    
    /**
     * End time of this events group
     * @return
     */
    public long getEndTime(){
        long endTime=0;
        for(StopwatchElement swElem : swElemList){
            endTime = Math.max(endTime, swElem.getEndTime());
        }
        return endTime;
    }
    
    /**
     *  Duration time of this events group
     * @return
     */
    public long getDurationTime(){
        return getEndTime()-getStartTime();
    }  
    
    
    /**
     * The number of layers in this events group
     * @return number of layers on witch elements were distributed in this group
     */
    public int getLayerCount(){
        int layerCount = 0;
        for(StopwatchElement swElem:swElemList){
            layerCount = Math.max(layerCount, swElem.getLayerNumber());
        }
        return layerCount;
    }
    
    
    /**
     * Setter for x position (left) of group
     * @param groupXOffset
     */
    public void setGroupXOffset(int groupXOffset) {
        this.groupXOffset = groupXOffset;
    }
    
    /**
     * Getter for x position (left) of group
     * @return
     */
    public int getGroupXOffset() {
        return this.groupXOffset;
    }
    
    /**
     * Getter for x end position (right) of group
     * @return
     */
    public int getGroupXEndOffset() {
        return groupXEndOffset;
    }

    /**
     *Setter for x end position (right) of group
     * @param groupXEndOffset
     */
    public void setGroupXEndOffset(int groupXEndOffset) {
        this.groupXEndOffset = groupXEndOffset;
    }    

    /**
     * Group order number
     * @return
     */
    public int getGroupNumber() {
        return swElemList.get(0).getGroupNumber();
    }    
    
    
    /**
     * It groups events, detecting the time intervals between them
     * @param swElemList list of all events 
     * @return list of all event groups
     */
    public static List<GroupStopwatchElement>  createListOfGroupSwElem(List<StopwatchElement> swElemList) {
        List<GroupStopwatchElement> groupSwElem = new ArrayList();
        addNewGroup(groupSwElem);
        for(int i=1; i<swElemList.size(); i++){
            StopwatchElement prevElem = swElemList.get(i-1);
            StopwatchElement currElem = swElemList.get(i);
            addToLastGroup(groupSwElem, prevElem);
            if(prevElem.getGroupNumber()<currElem.getGroupNumber()){
                addNewGroup(groupSwElem);
            }
            
            if(i==swElemList.size()-1) addToLastGroup(groupSwElem, currElem);
        }
        
        return groupSwElem;
    }  
   
    private static void addNewGroup(List<GroupStopwatchElement> groupSwElem) {
        groupSwElem.add(new GroupStopwatchElement());
    }
    
    
    private static void addToLastGroup(List<GroupStopwatchElement> groupSwElem, StopwatchElement swElem) {
        groupSwElem.get(groupSwElem.size()-1).addStopwatchElement(swElem);
    }    




         

    
}




