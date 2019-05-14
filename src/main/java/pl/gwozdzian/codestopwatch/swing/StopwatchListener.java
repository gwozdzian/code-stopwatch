/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gwozdzian.codestopwatch.swing;

/**
 * The listener inferface for reciving <code>StopwatchModel</code> changes ( scale changes )
 * @author gwozdzian
 */
public interface StopwatchListener {

    /**
     * Invoke when an action occure
     * @param changedModel model object in whitch the change happened
     */
    public void modelChanged(StopwatchModel changedModel);
    
}
