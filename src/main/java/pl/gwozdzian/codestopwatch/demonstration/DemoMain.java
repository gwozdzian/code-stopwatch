/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gwozdzian.codestopwatch.demonstration;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pl.gwozdzian.codestopwatch.CodeStopwatch;
import pl.gwozdzian.codestopwatch.swing.JCodeStopwatchComponent;
import pl.gwozdzian.codestopwatch.utils.StopwatchElement;
import pl.gwozdzian.utils.JStarterFrame;
import pl.gwozdzian.utils.Tools;

/**
 *
 * @author user
 */
public class DemoMain extends JStarterFrame {

    private JScrollPane scrollPaneForJSON;
    private JTextArea textAreaForJSON;
    private JPanel buttonsPane;
    private JScrollPane scrollPaneForCodeStopwatch;
    private JButton saveBtn;
    private JButton openBtn;

    @Override
    protected void init() {
        this.setPreferredSize(this.getSmallestScreenDimension());
        this.setSize(this.getSmallestScreenDimension());
        this.getContentPane().setPreferredSize(this.getSmallestScreenDimension());
        
        JStarterFrame.setVerticalLayouting(this.getContentPane());
        
        textAreaForJSON = new JTextArea("...");
        textAreaForJSON.setBorder(BorderFactory.createTitledBorder("JSON Object Displayer"));
        scrollPaneForJSON = JStarterFrame.createJScrollPane(textAreaForJSON, (int) (this.getSmallestScreenDimension().width*0.9), (int) (this.getSmallestScreenDimension().height*0.25));
        
        buttonsPane = new JPanel();
        buttonsPane.setBorder(BorderFactory.createTitledBorder("Control Buttons"));
        JStarterFrame.setHorizontalLayouting(buttonsPane);

        saveBtn = createSaveButton(null);
        openBtn = createOpenButton(null);
        
        buttonsPane.add(saveBtn);
        buttonsPane.add(openBtn);
        buttonsPane.add(new JButton("Przycisk 1"));
        buttonsPane.add(new JButton("Przycisk 2"));
        
        
        
        
        scrollPaneForCodeStopwatch = JStarterFrame.createJScrollPane(null, (int) (this.getSmallestScreenDimension().width*0.9), (int) (this.getSmallestScreenDimension().height*0.35));
        scrollPaneForCodeStopwatch.setBorder(BorderFactory.createTitledBorder("Code Stopwatch Displayer"));
        
        
        
        this.getContentPane().add(scrollPaneForJSON);
        this.getContentPane().add(buttonsPane);
        this.getContentPane().add(scrollPaneForCodeStopwatch);
        
        
        
    }
    
    
    
    
    
    
    
    
    
    
   public JButton createSaveButton(JButton btn){
        JButton button;
        if(btn==null){
            button = new JButton("Save");
        }else{
            button = btn;
        }
        
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createSaveFileChooser(textAreaForJSON.getText());
                } catch (IOException ex) {
                    Logger.getLogger(DemoMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }


        });

        return button;
    }    
    
    
    
   
   
   public JButton createOpenButton(JButton btn){
        JButton button;
        if(btn==null){
            button = new JButton("Open");
        }else{
            button = btn;
        }
        
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JSONArray openedJSON = createOpenFileChooser();
                    
                    
                    List<StopwatchElement> swElemList = CodeStopwatch.jsonArrayToSwElemList(openedJSON);
                    
                    Tools.trace("Otwarty JSON ma "+openedJSON.length()+" długości");
                    for(int i=0; i<openedJSON.length() ; i++){
                        Tools.trace("<<<"+i+">>>  -> "+openedJSON.get(i));
                    }
                    //openedJSON.get(WIDTH)
                    
                    //textAreaForJSON.setText(openedJSON.);
                    textAreaForJSON.setText(openedJSON.toString(1));
                    JCodeStopwatchComponent jStopwatchComponent = CodeStopwatch.getJStopwatchComponent(CodeStopwatch.jsonArrayToSwElemList(openedJSON));
                    
                               
                    displayCodeStopwatchComponent(jStopwatchComponent);
                    
                } catch (IOException ex) {
                    Logger.getLogger(DemoMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(DemoMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

 


        });

        return button;
    }     

    protected void displayCodeStopwatchComponent(JCodeStopwatchComponent jCodeStopwatchComponent) {
        scrollPaneForCodeStopwatch.getViewport().removeAll();
        scrollPaneForCodeStopwatch.getViewport().add(jCodeStopwatchComponent);
        SwingUtilities.getWindowAncestor(scrollPaneForCodeStopwatch).pack();
    }
   
   
   
   
   
   
   


    private void createSaveFileChooser(String strToSave) throws IOException  {//throws HeadlessException
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files", "json");
        jfc.setFileFilter(filter);


        jfc.setDialogTitle("Choose a directory to save your file: ");
        jfc.setDialogType(JFileChooser.SAVE_DIALOG);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
//         jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//         jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

//         jfc.setCurrentDirectory(null);

//int result = jFileChooser.showOpenDialog(new JFrame());


        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            if(!selectedFile.getName().endsWith(".json")){
                    selectedFile = new File(selectedFile.getCanonicalFile()+".json");

            }
            CodeStopwatch.writeStopwatchJsonToJsonFile(selectedFile.toPath(), strToSave);
//            Tools.trace("Wybrano plik do zapisu: "+selectedFile.getAbsolutePath());
//            Path path = selectedFile.toPath();
//            byte[] strToBytes = str.getBytes();
//               
//            Files.write(path, strToBytes);


        }
    }   

   
   
    
    
    
    
    
    private JSONArray createOpenFileChooser() throws IOException, Exception  {//throws HeadlessException
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files", "json");
        jfc.setFileFilter(filter);


        jfc.setDialogTitle("Choose a directory to save your file: ");
        jfc.setDialogType(JFileChooser.OPEN_DIALOG);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
//         jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//         jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

//         jfc.setCurrentDirectory(null);

//int result = jFileChooser.showOpenDialog(new JFrame());


        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            return CodeStopwatch.readStopwatchJsonFromJsonFile(selectedFile.toPath());


        }
        throw new Exception("Something went wrong during file been opened");
    }   

       
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new DemoMain();
        // TODO code application logic here
    }
    
}
