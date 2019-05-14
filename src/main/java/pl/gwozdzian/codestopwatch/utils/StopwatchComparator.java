/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gwozdzian.codestopwatch.utils;

import java.util.Comparator;

/**
 * Comparator for {@link StopwatchElement}
 * @author gwozdzian
 */
public class StopwatchComparator implements Comparator<StopwatchElement>{
    
    /**
     * Comparasion by start time.
     */
    public static final int SORT_BY_STARTTIME = 0;
    
    private int comparatorMode = SORT_BY_STARTTIME;

    /**
     * Default constructor
     */
    public StopwatchComparator() {
    }

    /**
     * Constructor with type of comparasion as a parameter
     * @param comparatorMode
     */
    public StopwatchComparator(int comparatorMode) {
        this.comparatorMode = comparatorMode;
    }
    
    
    



    @Override
    public int compare(StopwatchElement o1, StopwatchElement o2) {
        //TODO zrobić obsługę innych rodzajów sortowań
        
        long differenTime = o1.getStartTime()-o2.getStartTime();
        if(differenTime<0){
            return -1;
        }else if(differenTime>0){
            return 1;
        }else{
            return 0;
        }
    }
    
}
