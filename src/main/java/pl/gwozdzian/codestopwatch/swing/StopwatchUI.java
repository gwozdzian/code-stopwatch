/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gwozdzian.codestopwatch.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import pl.gwozdzian.codestopwatch.utils.StopwatchElement;
import pl.gwozdzian.utils.Tools;

/**
 *
 * @author user
 */
public class StopwatchUI extends ComponentUI{

    /**
     *  Height of single layer for StopwatchElement displaying
     */
    public static final int LAYER_HEIGHT = 18;

    /**
     *  Width of space to show pause in events recording
     */
    public static final int PAUSE_WIDTH = 35;

    private int TEXT_SPACE = 105;
    
    private List<StopwatchElement> findedSwElemsList = new ArrayList();
    private MouseEvent mouseEvent=null;
    private Font fontForDetailsBoard;
    private Font fontForStopwatchElement;
    private final Font fontForTimePicks;
    
    private Color DETAILS_BOARD_BACKGROUND_COLOR = Color.WHITE;
    private Color DETAILS_BOARD_BORDER_COLOR = Color.GRAY;
    private int BOARDER_MARGIN = 3;
    private int BOARDER_X_OFFSET = 15;
    private int BOARDER_ROUND_RADIUS = 5;
    private Color SW_ELEM_FILL = Color.WHITE;
    private Color SW_ELEM_BOARDER = Color.DARK_GRAY;
    private Color TIME_PICKS_COLOR = Color.LIGHT_GRAY;
    private Color SEPARATOR_COLOR = Color.RED.brighter();
    private NumberFormat numberFormat;
    private final DecimalFormat decimalFormat;
    
    /**
     *  UI delegate object for a swing <code>JCodeStopwatchComponent</code> component
     */
    public StopwatchUI() {
        fontForDetailsBoard = new Font(Font.SANS_SERIF, Font.PLAIN | Font.ITALIC, 12);
        fontForStopwatchElement = new Font(Font.DIALOG, Font.BOLD, 10);
        fontForTimePicks = new Font(Font.DIALOG, Font.TRUETYPE_FONT, 9);
        numberFormat = NumberFormat.getInstance();
        decimalFormat = new DecimalFormat("#.#");
        
        
    }

    /**
     * Returns an instance of the UI delegate for the JCodeStopwatchComponent component.
     * @param c instans of JComponent
     * @return new instance of StopwatchUI
     */
    public static ComponentUI createUI(JComponent c) {
        return new StopwatchUI();
    }  
    
    
    
    
    @Override
    public void installUI(JComponent c) {
        
        //TODO zrob dodanie listenera do słuchania zmiany skali
        super.installUI(c); //To change body of generated methods, choose Tools | Templates.
        JCodeStopwatchComponent swComp = (JCodeStopwatchComponent)c;
        
        c.addMouseWheelListener(new MouseWheelListener(){
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                mouseEvent = e;
                findedSwElemsList = new ArrayList();
                if(e.getWheelRotation()>0){
                    swComp.zoomIn();
                }else{
                    swComp.zoomOut();
                }   
            }
        });
        
        c.addMouseMotionListener(new MouseMotionListener(){
            @Override
            public void mouseDragged(MouseEvent e) {
                //Tools.trace(Tools.getMethodName());
                mouseEvent = e;
                findedSwElemsList = ((JCodeStopwatchComponent)c).getModel().findStopwatchElementsOnPoition(e.getX(), e.getY());
                c.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //Tools.trace(Tools.getMethodName());
            }
        });
        
        
        c.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseExited(MouseEvent e) {
                mouseEvent = e;
                findedSwElemsList = new ArrayList();
                c.repaint();
            }

            
            


            @Override
            public void mouseReleased(MouseEvent e) {
                //Tools.trace(Tools.getMethodName());
                mouseEvent = e;
                findedSwElemsList = new ArrayList();
                c.repaint();
            }
            
            
            
   
            
        });
        
        
        
        
        
    }    
    
    
    
    
    
    @Override
    public void paint(Graphics g, JComponent c) {
        Tools.trace(Tools.getMethodName());
        //super.paint(g, c); //To change body of generated methods, choose Tools | Templates.
        JCodeStopwatchComponent swComp = (JCodeStopwatchComponent)c;
        StopwatchModel model = swComp.getModel();
        Graphics2D g2d = (Graphics2D)g;
        
        paintTimePicks(g, g2d, swComp, model);
        //model.getScale()
        paintAllStopwatchElements(g, g2d, swComp, model);
        
        
        paintDetailsBoard(g, g2d,  swComp);
    }
    
    
    
    
    

    private void paintAllStopwatchElements(Graphics g,Graphics2D g2d ,  JCodeStopwatchComponent swComp, StopwatchModel model) {
        int groupStartX = 0;
        
        List<GroupStopwatchElement> groupStopwatchElementsList = model.getGroupStopwatchElementsList();
        for(int i=0; i<groupStopwatchElementsList.size(); i++){
            GroupStopwatchElement groupSwElem = groupStopwatchElementsList.get(i);
            groupSwElem.setGroupXOffset(groupStartX);
            //Tools.trace("GRUPA "+groupSwElem.getGroupNumber()+" ma "+groupSwElem.getStopwatchElementsList().size()+" elementów rozmieszczonych na "+groupSwElem.getLayerCount()+" warstwach i zaczyna się od x="+groupSwElem.getGroupXOffset());
            paintGroupSwElem(g, g2d, model,  groupSwElem, groupStartX);
            groupStartX+=(groupSwElem.getDurationTime()*model.getScale());
            groupSwElem.setGroupXEndOffset(groupStartX);
            groupStartX+=PAUSE_WIDTH;
            if(i<groupStopwatchElementsList.size()-1){
                drawGroupSeparator(g ,swComp ,groupSwElem);
            }
        }
    }
    
    

    private void paintTimePicks(Graphics g, Graphics2D g2d,  JCodeStopwatchComponent swComp, StopwatchModel model) {
        //TODO
        double scale = model.getScale();
        //if()
        double w10ms = scale*10;
        double w100ms = scale*100;
        double w1sek = scale*1000;
        
        double divider;
        double numMultiplier;
        String timeMeasure;
        if(scale<0.5){
            divider = w1sek;
            numMultiplier = 1;
            timeMeasure = "sek";
        }else if(scale<5){
            divider = w100ms;
            numMultiplier = 0.1;
            timeMeasure = "sek";
        }else{
            divider = w10ms;
            numMultiplier = 10;
            timeMeasure = "ms";
        }
        
        
        
        g2d.setColor(TIME_PICKS_COLOR);
        g2d.setFont(fontForTimePicks);
        for(int i=0; i<Math.ceil(swComp.getMinWidth()/divider); i++){
            int x = (int) (i*divider);
            g2d.drawLine(x, 0, x, swComp.getHeight());
            //long numToStr = Math.round((i*numMultiplier)*10)/10;
            g2d.drawString(decimalFormat.format(i*numMultiplier)+""+timeMeasure, x, swComp.getHeight());//i*numMultiplier
            
        } 
    }

    


    private void paintDetailsBoard(Graphics g, Graphics2D g2d, JCodeStopwatchComponent swComp) {


        FontMetrics fontMetricsForDetailsBoard = g.getFontMetrics(fontForDetailsBoard);
        fontMetricsForDetailsBoard.getHeight();
        
//        if(mouseEvent!=null){
//            GeneralPath gPath = new GeneralPath();
//            gPath.moveTo(mouseEvent.getX(), mouseEvent.getY());
//            gPath.quadTo(mouseEvent.getX(), mouseEvent.getY()-20, mouseEvent.getX()+30, mouseEvent.getY()-20);
//            g2d.draw(gPath);
//
//        }

        if(findedSwElemsList.size()>0){
            
            int boarderWidth = 0;
            int boarderHeight = 0;
            for(StopwatchElement swElem : findedSwElemsList){
                Rectangle strBounds = fontMetricsForDetailsBoard.getStringBounds(swElem.getNameFull(), g).getBounds();
                boarderWidth = (int) Math.max(strBounds.getBounds().getWidth(), boarderWidth);
                boarderHeight+= strBounds.getHeight();
            }
            boarderWidth+=2*BOARDER_MARGIN;
            boarderHeight+=2*BOARDER_MARGIN;
            
            
            int boarderX = mouseEvent.getX()+BOARDER_X_OFFSET;
            int boarderY = mouseEvent.getY()-(boarderHeight/2);
            
            if(boarderY<0){
                boarderY=0;
            }
            
            

            // creating and painting boarder and background

            RoundRectangle2D.Double roundedRect = new RoundRectangle2D.Double(boarderX, boarderY, boarderWidth, boarderHeight, BOARDER_ROUND_RADIUS, BOARDER_ROUND_RADIUS);
            //RoundRectangle2D.Double roundedRect = new RoundRectangle2D.Double(mouseEvent.getX(), mouseEvent.getY()-(boarderHeight/2), boarderWidth, boarderHeight, 5, 5);

            g2d.setColor(DETAILS_BOARD_BACKGROUND_COLOR);
            g2d.fill(roundedRect);
            g2d.setColor(DETAILS_BOARD_BORDER_COLOR);
            g2d.draw(roundedRect);            
            
            //g.drawRoundRect(mouseEvent.getX(), mouseEvent.getY()-(stringsHeight/2), stringsWidth, stringsHeight, 5, 5);
            
           
            
            // creating and painting arrow
            GeneralPath arrowShape = new GeneralPath();
            arrowShape.moveTo(mouseEvent.getX(), mouseEvent.getY());
            arrowShape.quadTo(boarderX, mouseEvent.getY(), boarderX, boarderY+BOARDER_ROUND_RADIUS);
            arrowShape.lineTo(boarderX, boarderY+boarderHeight-BOARDER_ROUND_RADIUS);
            arrowShape.quadTo(boarderX, mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
            
            
            
            g2d.setColor(DETAILS_BOARD_BACKGROUND_COLOR);
            g2d.fill(arrowShape);
            
            
            g2d.setColor(DETAILS_BOARD_BORDER_COLOR);
            g2d.draw(arrowShape); 
                        
            
            
            
            // drawing names
            
            int currY = boarderY+BOARDER_MARGIN;
            for(StopwatchElement swElem :findedSwElemsList){
                Rectangle strBounds = fontMetricsForDetailsBoard.getStringBounds(swElem.getNameFull(), g).getBounds();
                currY+=strBounds.getHeight();
                g.drawString(swElem.getNameFull(), boarderX+BOARDER_MARGIN, currY);
                
            }
            //g.drawString(str, mouseEvent.getX()+5, mouseEvent.getY()+5);
        }
        
        
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
  
    /////////////////*************///////////////
    
    
    private void drawGroupSeparator(Graphics g, JCodeStopwatchComponent swComp, GroupStopwatchElement groupSwElem) { 
        int xSeperator = groupSwElem.getGroupXEndOffset()+(PAUSE_WIDTH/2);
        Color tmpColor = g.getColor();
        g.setColor(SEPARATOR_COLOR);
        Tools.drawDashedLine(g, xSeperator,0,xSeperator,swComp.getHeight());
        g.setColor(tmpColor);
    }    
    
    
    
    private void paintGroupSwElem(Graphics g, Graphics2D g2d, StopwatchModel model, GroupStopwatchElement groupSwElem, int groupStartX) {
        
        
        
        FontMetrics fontMetrics = g.getFontMetrics(fontForStopwatchElement);
        
        
        int marginSwElem = 1;
        int roundSwElem = 2;
        for(StopwatchElement swElem : groupSwElem.getStopwatchElementsList()){
            int xElem = groupSwElem.getGroupXOffset()+ (int)(model.getScale()* ( swElem.getStartTime()-groupSwElem.getStartTime())) ;
            int yElem = swElem.getLayerNumber()*LAYER_HEIGHT;
            int wElem =(int) (swElem.getDurationTime()*model.getScale());
            int hElem = LAYER_HEIGHT;
            
            swElem.setX(xElem);
            swElem.setY(yElem);
            swElem.setWidth(wElem);
            swElem.setHeight(hElem);
            
            //Tools.trace(swElem.getName()+" na warstwie "+swElem.getLayerNumber()+" x="+xElem+" y="+yElem+" w="+wElem+" h="+hElem);

            g.setColor(Color.red);
            if(wElem>2*marginSwElem+2*roundSwElem){
                RoundRectangle2D.Double roundedRect = new RoundRectangle2D.Double(xElem, yElem+marginSwElem, wElem, hElem-(2*marginSwElem), roundSwElem, roundSwElem);
                g2d.setColor(SW_ELEM_FILL);
                g2d.fill(roundedRect);
                g2d.setColor(SW_ELEM_BOARDER);
                g2d.draw(roundedRect);
                //g.drawRoundRect(xElem, yElem+marginSwElem, wElem, hElem-(2*marginSwElem), roundSwElem, roundSwElem);
            }else if(wElem>2*marginSwElem+2){
                Rectangle2D.Double rect = new Rectangle2D.Double(xElem, yElem+marginSwElem, wElem, hElem-(2*marginSwElem));
                g2d.setColor(SW_ELEM_FILL);
                g2d.fill(rect);
                g2d.setColor(SW_ELEM_BOARDER);
                g2d.draw(rect);
                //g.drawRect(xElem, yElem+marginSwElem, wElem, hElem-(2*marginSwElem));
            }else if(wElem>0){
                Rectangle2D.Double rect = new Rectangle2D.Double(xElem, yElem+marginSwElem, wElem, hElem-2*marginSwElem);
                g2d.setColor(SW_ELEM_FILL);
                g2d.fill(rect);
                g2d.setColor(SW_ELEM_BOARDER);
                g2d.draw(rect);
                //g.drawRect(xElem, yElem+marginSwElem, wElem, hElem-2*marginSwElem);
            }else{
                g2d.setColor(SW_ELEM_BOARDER);
                g2d.drawLine(xElem, yElem, xElem, yElem+hElem);
                continue;
            }
            
            
  
            String stringFull = swElem.getNameFull(); // = swElem.getName()+" - "+durationTimeStr;
            String stringNormal = swElem.getName();
            String stringMin = swElem.getNameShort();

            
            int stringFullWidth = fontMetrics.stringWidth(stringFull);
            int stringNormalWidth = fontMetrics.stringWidth(stringNormal);
            int stringMinWidth = fontMetrics.stringWidth(swElem.getNameShort());
            Rectangle2D boundsFull = fontMetrics.getStringBounds(stringFull, g);
            Rectangle2D boundsNormal = fontMetrics.getStringBounds(stringNormal, g);
            Rectangle2D boundsMin = fontMetrics.getStringBounds(stringMin, g);
            
            g.setFont(fontForStopwatchElement);
            g.setColor(Color.GRAY);
            
            if(wElem>(stringFullWidth*2)){

                for(int i=0; i<Math.floor(wElem/(stringFullWidth+TEXT_SPACE)); i++){
                    g2d.drawString(stringFull, xElem+ ((stringFullWidth+TEXT_SPACE)*i), (int) ((yElem+hElem) - (hElem-boundsFull.getHeight())/2));
                }   
            }else if(wElem>stringFullWidth){
                g2d.drawString(stringFull, xElem, (int) ((yElem+hElem) - (hElem-boundsFull.getHeight())/2));
            }else if(wElem>=stringNormalWidth){   
                g2d.drawString(stringNormal, xElem, (int) ((yElem+hElem) - (hElem-boundsFull.getHeight())/2));
            }else if(wElem>stringMinWidth){
                g2d.drawString(stringMin, xElem, (int) ((yElem+hElem) - (hElem-boundsFull.getHeight())/2));
                //g2d.drawString(swElem.getName(), xElem, yElem+hElem);
            }
        }
        
    }    









    
}
