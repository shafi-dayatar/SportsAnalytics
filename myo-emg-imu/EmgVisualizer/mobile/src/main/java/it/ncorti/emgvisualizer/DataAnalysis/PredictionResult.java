package it.ncorti.emgvisualizer.DataAnalysis;

import java.util.HashMap;

import it.ncorti.emgvisualizer.utils.ObservableHashMap;

public class PredictionResult {

    private HashMap<Stroke, Integer> strokeCount = null;

    private ObservableHashMap<String, Integer> strokeOutput = null;
    private static PredictionResult result = null;
    private final static Object gatekeeper = new Object();

    private PredictionResult(){

        strokeCount = new HashMap<Stroke, Integer>();
        strokeOutput = new ObservableHashMap();
    }

    public static PredictionResult getInstance(){
        if (result == null){
            synchronized (gatekeeper){
                if (result == null)
                    result =  new PredictionResult();
            }
        }
        return result;
    }
    public synchronized void addResult(String type){
        if (strokeOutput.containsKey(type))
            strokeOutput.put(type, strokeOutput.get(type) + 1);
        else
            strokeOutput.put(type, 1);
    }

    public int getNumber(String type){
        return strokeOutput.get(type);
    }

    public ObservableHashMap<String, Integer> getAllResults(){

        return strokeOutput;
    }

    public synchronized void addResult(Stroke type){
        if (strokeCount.containsKey(type))
            strokeCount.put(type, strokeCount.get(type) + 1);
        else
            strokeCount.put(type, 1);
    }

    public int getStrokeCount(Stroke type){
        return strokeCount.get(type);
    }

    public HashMap<Stroke, Integer> getAllStrokeCount(Stroke type){
        return strokeCount;
    }

}

