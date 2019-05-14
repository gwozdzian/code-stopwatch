/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gwozdzian.codestopwatch.utils;

import org.json.JSONObject;

/**
 * Class describing single measuring event
 * @author gwozdzian
 */
public class StopwatchElement {
   
    private long startTime;
    private long endTime;
    private String name;
    
    private int layerNumber = 0;
    private int groupNumber = 0;
    
    
    private int x;
    private int y;
    
    private int width;
    private int height;

    /**
     *
     * @param name
     * @param startTime
     * @param endTime
     */
    public StopwatchElement(String name, long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
    }

    public StopwatchElement(JSONObject jsonObj) {
        this(jsonObj.getString("name"), jsonObj.getLong("startTime"), jsonObj.getLong("endTime"));
    }

    /**
     * Name of measuring event.
     * @return
     */
    public String getName() {
        return name;
    }


    /**
     * A long version of the name, containing the name of the event being measured and its duration.
     * @return
     */
    public String getNameFull() {
        return this.name+" "+getDurationTimeText();
    }

    /**
     * Short version of the name, containing seven beginning letters of name and three dots
     * @return
     */
    public String getNameShort() {
            String stringMin = "";
            if(name.length()>10){
                return name.substring(0, 7)+" ..";
            }else{
                return name;
            }
            
    }
    
    
    
    
    /**
     * End time of measuring event.
     * @return
     */
    public long getEndTime() {
        return endTime;
    }



    /**
     * Start time of measuring event
     * @return
     */
    public long getStartTime() {
        return startTime;
    }


    
    /**
     * Duration of measuring event (in miliseconds)
     * @return
     */
    public long getDurationTime(){
        return endTime-startTime;
    }
    
    /**
     * Duration time as a text expressed in miliseconds or seconds  
     * @return
     */
    public String getDurationTimeText(){
            long durationTime = this.getDurationTime();
            if(durationTime>1000){
                return (durationTime/1000)+" sek";
            }else{
                return durationTime+" ms";
            }
    }
    
    /**
     * The number of the layer on which the event should be inserted during display.
     * @return
     */
    public int getLayerNumber() {
        return layerNumber;
    }

    /**
     * Setters
     * @param layerNumber
     */
    public void setLayerNumber(int layerNumber) {
        this.layerNumber = layerNumber;
    }

    /**
     * the number of the group to which this event belongs.
     * @see GroupStopwatchElement
     * @return 
     */
    public int getGroupNumber() {
        return groupNumber;
    }

    /**
     *
     * @param groupNumber
     */
    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }   
    
    /**
     * Graphical height of this element.
     * Calculated by {@link StopwatchUI}
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     * Graphical height of this element.
     * Set by {@link StopwatchUI}
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Graphical width of this element.
     * Calculated by {@link StopwatchUI}
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * Graphical height of this element.
     * Set by {@link StopwatchUI}
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Graphical y of this element.
     * Calculated by {@link StopwatchUI}
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Graphical y of this element.
     * Set by {@link StopwatchUI}
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Graphical x of this element.
     * Calculated by {@link StopwatchUI}
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Graphical x of this element.
     * Set by {@link StopwatchUI}
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    
    public JSONObject getJSONObject(){
            return new JSONObject()
                    .put("name", getName())
                    .put("startTime", getStartTime())
                    .put("endTime", getEndTime());  
    }
 
}
