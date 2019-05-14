/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gwozdzian.codestopwatch;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import pl.gwozdzian.codestopwatch.utils.StopwatchComparator;
import pl.gwozdzian.codestopwatch.utils.StopwatchElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.joining;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pl.gwozdzian.utils.Tools;
import pl.gwozdzian.codestopwatch.swing.JCodeStopwatchComponent;


/**
 * Class used by clients to save selected events for further visualize in graphics component or other analize.
 * <p>How to use.</p>
 * Insert {@link #start() } at the begining and {@link #stop() } on the end of measuring part of code within the same method.
 * If you want measured event occured within diffrent methods or in any other case , use  {@link #start(java.lang.String)  } and {@link #stop(java.lang.String)  } with exactly the same event id pased as a parameter.
 * To visualized recorded events use {@link #getJStopwatchComponent(boolean)  }
 * To get recorded events as a JSON object use {@link #getStopwatchJSON()  }
 * To get raw list of events as a list of {@link StopwatchElement } use {@link #getStopwatchElementsList()  }
 * 
 * 
 * @author gwozdzian
 */
public class CodeStopwatch {
    
    private static Map<String, Long> timesMap = new HashMap();
    private static List<StopwatchElement> stopwatchElementsList = new ArrayList();
    private static boolean traceToConsole = false;

    /**
     * The minimum time of not occurrence of any event after which the following events fall into the next group
     */
    public static final long STOPWATCH_PAUSE_TIME = 1000;

    /**
     *
     * @return
     */
    public static boolean isTraceToConsole() {
        return traceToConsole;
    }

    /**
     *
     * @param traceToConsole
     */
    public static void setTraceToConsole(boolean traceToConsole) {
        CodeStopwatch.traceToConsole = traceToConsole;
    }

    /**
     * Insert if you want start measuring some event (code execution) time
     * This method gets the name of the class and name of the method inside which the time is measured as the event identifier.
     * WARNING Use only with {@link #stop() } in the same method body.
     * @return
     */
    public static String start(){
        return start(Tools.getMethodName(3));
    }
    
    /**
     * Insert if you want start measuring some event (code execution) time.
     * WARNING Use only with {@link #stop(java.lang.String)  }. Notice you can use it within different method body but ALWAYS with the same identifier of starting and ending the same event measuring.
     * @param idTime event identifier 
     * @return
     */
    public static String start(String idTime){
        timesMap.put(idTime, System.currentTimeMillis());
        if(traceToConsole) System.out.println("Rozpocząłem mierzenie czasu dla "+idTime);
        return idTime;
    }
    
    /**
     * Insert if you want finish measuring some event (code execution) time.
     * WARNING Use only with {@link #start() } in the same method body.
     * @return
     */
    public static long stop(){
        return stop(Tools.getMethodName(3));
    }
    
    /**
     * Insert if you want start measuring some event (code execution) time.
     * WARNING Use only with {@link #start(java.lang.String)  }. Notice you can use it within different method body but ALWAYS with the same identifier of starting and ending the same event measuring.
     * @param idTime event identifier
     * @return
     */
    public static long stop(String idTime){
        if(timesMap.containsKey(idTime)){
            long startTime = timesMap.remove(idTime);
            long endTime = System.currentTimeMillis();
            StopwatchElement stopwatchElement = new StopwatchElement(idTime, startTime, endTime);
            long durationTime = stopwatchElement.getDurationTime();
            if(traceToConsole)  System.out.println("Czas dla "+idTime+" wynosi "+durationTime+ "ms");
            stopwatchElementsList.add(stopwatchElement);
            return durationTime;
        }else{
            return 0;
        }
    }  
    
    /**
     * List of measured events
     * @return {@link  StopwatchElement}'s list
     */
    public static List<StopwatchElement> getStopwatchElementsList() {
        return getStopwatchElementsList(false);
    }

    public static void setStopwatchElementsList(List<StopwatchElement> stopwatchElementsList) {
        CodeStopwatch.stopwatchElementsList = stopwatchElementsList;
        recalculateStopwatchElements(stopwatchElementsList);
        
    }
    
    public static List<StopwatchElement> returnAndClearStopwatchElementList(){
        List<StopwatchElement> tmpList = stopwatchElementsList;
        stopwatchElementsList = new ArrayList();
        return tmpList;
    }

    /**
     * List of measured events
     * @param doClearList true if you delete the list you downloaded after downloading, false if not  
     * @return {@link  StopwatchElement}'s list
     */
    public static List<StopwatchElement> getStopwatchElementsList(boolean doClearList) {
        
        
        recalculateStopwatchElements(stopwatchElementsList);
        
        if(doClearList){
            return returnAndClearStopwatchElementList();
        }else{
            return stopwatchElementsList;
        }
        
    }

    /**
     * It generate Swing component to visualize collected data.
     * @param doClearList true if you delete the list you downloaded after downloading, false if not
     * @return
     */
    public static JCodeStopwatchComponent getJStopwatchComponent(boolean doClearList){
        return new JCodeStopwatchComponent(getStopwatchElementsList(doClearList));
    }
    
    

    public static JCodeStopwatchComponent getJStopwatchComponent(List<StopwatchElement> swElemList){
        return new JCodeStopwatchComponent(swElemList);
    }    
    
    
    /**
     * It generate JSON object of collected data
     * @return JSON object containing measured events.
     */
    public static JSONArray getStopwatchJSON() {
        return getStopwatchJSON(false);
    }
    
    /**
     * It generate JSON object of collected data
     * @param doClearList true if you delete the list you downloaded after downloading, false if not
     * @return JSON object containing measured events.
     */
    public static JSONArray getStopwatchJSON(boolean doClearList) {  
        return CodeStopwatch.swElemListToJsonArray(getStopwatchElementsList(doClearList));
    }
    
    public static void writeStopwatchJsonToJsonFile(Path pathToSave, boolean doClearList) throws IOException{
        JSONArray stopwatchJsonArr = getStopwatchJSON(doClearList);
        writeStopwatchJsonToJsonFile(pathToSave, stopwatchJsonArr);
    }

    public static void writeStopwatchJsonToJsonFile(Path pathToSave, JSONArray stopwatchJsonArr) throws IOException{
        
        writeStopwatchJsonToJsonFile(pathToSave, stopwatchJsonArr.toString(1));
        
    }

    
    public static void writeStopwatchJsonToJsonFile(Path pathToSave, String strToWrite) throws IOException{
        
        
            File selectedFile = pathToSave.toFile();
            if(!selectedFile.getName().endsWith(".json")){
                    selectedFile = new File(selectedFile.getCanonicalFile()+".json");

            }
            Path path = selectedFile.toPath();
            byte[] strToBytes = strToWrite.getBytes();
               
            Files.write(path, strToBytes);
        
    }
    
    
    
    public static JSONArray readStopwatchJsonFromJsonFile(Path pathToRead) throws IOException{
        
        if(!pathToRead.toFile().getCanonicalPath().endsWith(".json")) throw new IOException("Wrong file extension. Is "+pathToRead.toFile().getCanonicalPath());
        List<String> allReadLines = reaAllLines(pathToRead);        
        StringBuilder sb = new StringBuilder("");
        for(int i = 0; i<allReadLines.size(); i++){
            sb.append(allReadLines.get(i));
            sb.append(Tools.newLine);
        }
        String jsonStr = sb.toString();
        //Tools.trace("JSON_STRING"+Tools.newLine+jsonStr+"end of JSON_STRING");
        JSONArray stopwatchElementsJsonArray = new JSONArray(jsonStr);
        return stopwatchElementsJsonArray;
    }

    public static List<String> reaAllLines(Path pathToRead) throws IOException {
        //String result = Files.toString(pathToRead.toFile(), Charsets.UTF_8);
        List<Charset>charsets = new ArrayList();
        charsets.add(StandardCharsets.UTF_8);
        charsets.add(StandardCharsets.ISO_8859_1);
        charsets.add(StandardCharsets.UTF_16);
        charsets.add(StandardCharsets.US_ASCII);        
        charsets.add(StandardCharsets.UTF_16LE);
        charsets.add(StandardCharsets.UTF_16BE);

        
        for(Charset charset:charsets){
            
            try {
                List<String> readLines = Files.readAllLines(pathToRead, charset);
                Tools.trace("Charset: "+charset.displayName()+" , "+charset.name());
                return readLines;
            } catch (IOException ex) {
                //Logger.getLogger(CodeStopwatch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        throw new IOException("Undefined Charset");
         
    }
    
    
    
    public static List<StopwatchElement> jsonArrayToSwElemList(JSONArray openedJSON) throws JSONException {
        List<StopwatchElement> resultList = new ArrayList();
        for (int i = 0; i < openedJSON.length(); i++) {
            JSONObject currJSON = openedJSON.getJSONObject(i);
            StopwatchElement currSwElem = new StopwatchElement(currJSON) ;
            resultList.add(currSwElem);
        }
        
        recalculateStopwatchElements(resultList);
        
        return resultList;
    }

    public static JSONArray swElemListToJsonArray(List<StopwatchElement> swElemList) {
        JSONArray jsonArray = new JSONArray();
        swElemList.stream().map((swElem) -> {
            return swElem.getJSONObject();
        }).forEachOrdered((currObj) -> {
            jsonArray.put(currObj);
        });

        return jsonArray;
    }
            
    
    

    /**
     * It calculate addition values helpfull for further displaying.
     * @param swElementsList {@link  StopwatchElement}'s list
     */
    
    public static void recalculateStopwatchElements(List<StopwatchElement> swElementsList) {
        swElementsList.sort(new StopwatchComparator(StopwatchComparator.SORT_BY_STARTTIME));
        computeLayerNumbers(swElementsList);
        computeGroupByEmptySpace(swElementsList);
    }
    

    protected static void computeLayerNumbers(List<StopwatchElement> swElemList) {
        for (int i = 0; i < swElemList.size(); i++) {
            swElemList.get(i).setLayerNumber(i);
            //System.out.println("Element nr:" + i + "ma ustawiony tymczasowy layerNumber i bublinguj\u0119 ..");
            bubblingLayer(i, swElemList);
        }
    }

    private static void bubblingLayer(int i, List<StopwatchElement> swElemList) {
        for (int levNr = i - 1; levNr >= 0; levNr--) {
            //System.out.println("bublinguj\u0119 dla levNr=" + levNr + " i=" + i);
            StopwatchElement prevElem = swElemList.get(levNr);
            StopwatchElement currElem = swElemList.get(i);
            //System.out.println("Bublinguj\u0119 " + currElem.getName() + " i por\u00f3wnuj\u0119 do " + prevElem.getName());
            if (hasTimeCollision(prevElem, currElem)) {
                currElem.setLayerNumber(prevElem.getLayerNumber() + 1);
                //System.out.println(currElem.getName() + "ma ostatecznie " + currElem.getLayerNumber() + " layerNumber");
                return;
            } else {
                //System.out.println(currElem.getName() + "ma obni\u017cony do " + levNr + " levelNr");
                currElem.setLayerNumber(levNr);
            }
        }
    }
    
    private static boolean hasTimeCollision(StopwatchElement beforeElem, StopwatchElement currElem) {
        return !(beforeElem.getEndTime() <= currElem.getStartTime());
    }    


    protected static void computeGroupByEmptySpace(List<StopwatchElement> swElemList) {
        List<List<StopwatchElement>> resultSplitList = new ArrayList();
        if (swElemList.size() < 1) {
            return;
        }
        int groupNr = 0;
        //addNewList(resultSplitList);
        long maxEnd = 0; // = swElemList.get(0).getEndTime();
        for (int i = 1; i < swElemList.size(); i++) {
            StopwatchElement prevElem = swElemList.get(i - 1);
            StopwatchElement currElem = swElemList.get(i);
            maxEnd = Math.max(maxEnd, prevElem.getEndTime());
            prevElem.setGroupNumber(groupNr); //addToEnd(resultSplitList, prevElem);
            if (currElem.getStartTime() >= maxEnd + CodeStopwatch.STOPWATCH_PAUSE_TIME) {
                groupNr++; //addNewList(resultSplitList);
            }
            if (i == swElemList.size() - 1) {
                currElem.setGroupNumber(groupNr); //addToEnd(resultSplitList, currElem);
            }
        }
    }


    
    

    
    
    
}
