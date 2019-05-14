/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gwozdzian.codestopwatch.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import pl.gwozdzian.codestopwatch.utils.StopwatchElement;
import pl.gwozdzian.utils.Tools;



/**
 * Swing widget JCodeStopwatchComponent
 * <p>It visualizes the time dependencies of previously recorded events.
 * We suggest using the {@link pl.gwozdzian.codestopwatch.CodeStopwatch} class to generate swing component that visualize recorded events </p>
 * @see Stopwatch

 * @author gwozdzian
 */
public class JCodeStopwatchComponent extends JComponent implements StopwatchListener{
    
    /**
     *  Model to keep buisness logic of component 
     */
    protected StopwatchModel model;
    
    /**
     * <code>JStopwatchComponent</code> constructor. This constructor build model based on passed events({@link StopwatchElement}) list
     * @param swElemList list of {@link StopwatchElement}
     */
    public JCodeStopwatchComponent(List<StopwatchElement> swElemList) {
        this(new StopwatchModel(swElemList));
    }       

    /**
     * <code>JStopwatchComponent</code> constructor.
     * @param model 
     */
    public JCodeStopwatchComponent(StopwatchModel model) {
        init(model);
    }
    
    private void init(StopwatchModel m) {
        this.model = m;
        model.addStopwatchListener(this);
        setKeyboardMaps();
        this.setPreferredSize(new Dimension(getMinWidth(), getHeight()));
        updateUI();
    }

    @Override
    public void updateUI() {
        setUI(new StopwatchUI()); 
        invalidate();
    }

    @Override
    public String getUIClassID() {
        //TODO sprawdź czy wychodzi co ma wychodzić :)
        return StopwatchUI.class.getCanonicalName();// JCodeStopwatchComponent.class.getCanonicalName();
    }
    
    /**
     * Returns the model that this component represents.
     * @return the <code>model</code> property
     */
    public StopwatchModel getModel() {
        return model;
    }

    /**
     * Sets the model that this component represents.
     * @param model the new <code>StopwatchModel</code>
     */
    public void setModel(StopwatchModel model) {
        this.model = model;
    }

    /**
     * Getter for Scale
     * @return scale of displayed information
     */
    public double getScale() {
        return model.getScale();
    }

    /**
     * Setter for Scale
     * @param scale scale of displayed information
     */
    public void setScale(double scale) {
        model.setScale(scale);
        Tools.trace("Skala wynosi "+scale);
    }
    
    
    
    

    /**
     *  Increases the scale and zoom in the view
     */
    public void zoomIn() {
        double scale = getScale();
        if(scale<1){
            scale*=1.2;
        }else if(scale<4){
            scale*=1.4;
        }else if(scale<6){
            scale*=1.3;
        }else if(scale<10){
            scale*=1.2;
        }else{
            scale*=1.1;
        }
        setScale(scale);
    }

    /**
     *  Decreases the scale and zoom out the view
     */
    public void zoomOut() {
        double scale = getScale();
//        if(scale<0.2){
//            scale=0.8*scale;
//        }
//        else 
        if(scale<1){
            scale*=0.9;
        }else{
            scale*=0.75;
        }
        setScale(scale);
    }
    
    
    
    

    @Override
    public void modelChanged(StopwatchModel changedModel) {
        this.setPreferredSize(new Dimension(getMinWidth(), getHeight()));
        invalidate();
        repaint();
        SwingUtilities.getWindowAncestor(this).pack();
    }

    private void setKeyboardMaps() {
        //TODO
    }

    
    
    
    
    ////////////////////////////////////////////////////
    
    
    
    @Override
    public void revalidate() {
        //Tools.trace(Tools.getMethodName());
        super.revalidate(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void paint(Graphics g) {
        //Tools.trace(Tools.getMethodName());
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void repaint() {
        //Tools.trace(Tools.getMethodName());
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
    }



//    @Override
//    public int getWidth() {
//        int defaultW = super.getWidth();
//        int thisW = model.getWidth();
//        Tools.trace("Width według JComponent="+defaultW+"a według StopwatchModel="+thisW);
//        return Math.max(defaultW, thisW); 
//    }

    @Override
    public Dimension getPreferredSize() {
        Tools.trace(Tools.getMethodName());
        Dimension defaultPreferredSize = super.getPreferredSize();
        Dimension thisPreferredSize = new Dimension(getWidth(), getHeight());
        Tools.trace("Default Preferred Size="+defaultPreferredSize.width+","+defaultPreferredSize.height);
        Tools.trace("This Preferred Size="+thisPreferredSize.width+","+thisPreferredSize.height);
        //return thisPreferredSize;
        return super.getPreferredSize();
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    

    /**
     * Width
     * @return value of displayed information calculated on the base of scale and total duration of measuring event
     */
    @Override
    public int getWidth() {
        int superWidth = super.getWidth();
        int thisWidth=  getMinWidth();
        

        Tools.trace("Width według JComponent="+superWidth+"a według StopwatchModel="+thisWidth);
        //return thisWidth;
        return Math.max(superWidth, thisWidth);         
    }

    public int getMinWidth() {
        int thisWidth=  0;
        List<GroupStopwatchElement> groupStopwatchElementsList = model.getGroupStopwatchElementsList();
        for (GroupStopwatchElement groupSwElem : groupStopwatchElementsList) {
            thisWidth += groupSwElem.getDurationTime() * model.getScale();
        }        
        thisWidth += (groupStopwatchElementsList.size() - 1) * StopwatchUI.PAUSE_WIDTH;
        return thisWidth;
    }

    /**
     * Height
     * @return value of displayed information based on layers count
     */
    public int getHeight() {
        return (model.getLayerCount() + 2) * StopwatchUI.LAYER_HEIGHT;
    }
    
    
    
    
    
    
    

    

    
}
