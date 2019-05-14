/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gwozdzian.codestopwatch.swing;

import java.util.ArrayList;
import java.util.List;
import pl.gwozdzian.codestopwatch.CodeStopwatch;
import pl.gwozdzian.codestopwatch.utils.StopwatchElement;

/**
 * State model for JStopwatchComponent.
 * <p>
 * Model is build over list of {@link StopwatchElement} 
 * This list is also grouped by {@link GroupStopwatchElement} within different part of time happend. 
 * 
 * </p>
 * @author gwozdzian
 * 
 */
public class StopwatchModel {
    
    
    
    List<StopwatchElement>stopwatchElementsList;
    List<GroupStopwatchElement>groupStopwatchElementsList;
    List<StopwatchListener>listeners = new ArrayList<>();
    

    
    private double scale = 0.5;
    private int layerCount = -1;
    
    /**
     * Default model constructor
     * @param swElementsList
     */
    public StopwatchModel(List<StopwatchElement> swElementsList) {
        this.stopwatchElementsList = swElementsList;
        CodeStopwatch.recalculateStopwatchElements(this.stopwatchElementsList);

        groupStopwatchElementsList = GroupStopwatchElement.createListOfGroupSwElem(stopwatchElementsList);
         
    }

    /**
     * List of measured events
     * @return {@link  StopwatchElement}'s list
     */
    public List<StopwatchElement> getStopwatchElementsList() {
        return stopwatchElementsList;
    }

    /**
     * List of grouped events
     * @return {@link  GroupStopwatchElement}'s list
     */
    public List<GroupStopwatchElement> getGroupStopwatchElementsList() {
        return groupStopwatchElementsList;
    }

    /**
     * Scale
     * @return scale of displayed information
     */
    public double getScale() {
        return scale;
    }

    /**
     * Scale
     * @param scale scale of displayed information
     */
    public void setScale(double scale) {
        this.scale = scale;
        fireEvent();
    }
    
    
    /**
     * The number of layers 
     * @return number of layers on witch elements were distributed 
     */
    public int getLayerCount(){
        if(layerCount<0){
            for(GroupStopwatchElement groupSwElem : this.groupStopwatchElementsList){
                layerCount = Math.max(layerCount, groupSwElem.getLayerCount());
            }
        }
        return layerCount;
    }
    
    /**
     * Adds an <code>StopwatchListener</code> to the model.
     * @param l listener to add
     */
    public void addStopwatchListener(StopwatchListener l){
        listeners.add(l);
    }


    /**
     * Remove an <code>StopwatchListener</code> to the model.
     * @param l listener to be removed
     */    
    public void removeStopwatchListener(StopwatchListener l){
        listeners.remove(l);
    }    

    /**
     * Finds <code>StopwatchElement</code>'s be placed on given position
     * @param x  
     * @param y
     * @return list of finded elements. If no finded elements, empty list (not null) is returned.
     */
    public List<StopwatchElement> findStopwatchElementsOnPoition(int x, int y) {
        List<StopwatchElement> resultList = new ArrayList();
        for(GroupStopwatchElement currGroup : this.groupStopwatchElementsList){
            if(currGroup.getGroupXOffset()<=x && currGroup.getGroupXEndOffset()>=x){
                
                for(StopwatchElement swElem:currGroup.getStopwatchElementsList()){
                    if(x>=swElem.getX() && x<=swElem.getX()+swElem.getWidth()){
                        if(y>=swElem.getY() && y<=swElem.getY()+swElem.getHeight()){
                            resultList.add(swElem);
                            if(swElem.getWidth()>0) return resultList;
                        }
                    }
                }
                
            }
        }
        return resultList;
    }
    
    
    private void fireEvent() {
        for(StopwatchListener l : listeners){
            l.modelChanged(this);
        }
    }
    


    
    
    


    
    
    


    
    
    
    
}
