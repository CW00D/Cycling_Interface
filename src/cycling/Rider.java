package cycling;

import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalTime;

public class Rider{
    //<editor-fold desc="__________________________________Atributes__________________________________">
    //static instances counter
    private static int count = 0;
    //rider's id
    private Integer riderId;
    //rider's name
    private String riderName;
    //rider's year of birth
    private Integer riderYOB;
    //riders results maybe a double instead of a localdatetime
    private HashMap<Integer, LocalTime[]> riderStageResults = new HashMap<Integer, LocalTime[]>();

    //</editor-fold>

    //<editor-fold desc="_________________________________Constructor_________________________________">
    public Rider(String riderName, Integer riderYOB){
        this.riderName = riderName;
        this.riderYOB = riderYOB;
        this.riderId=count++;
    }

    //</editor-fold>

    //<editor-fold desc="_____________________________Getters And Setters_____________________________">
    public Integer getRiderId () {
        return riderId;
    }

    //</editor-fold>

    //<editor-fold desc="___________________________________Methods___________________________________">
    //deleteRider - removes rider and all of their results from the system

    //checks if a stages result is already set
    public boolean hasResult(Stage stage){
        return riderStageResults.containsKey(stage);
    }

    //adds a stages result to the stage results hash map
    public void addResultToHashMap(Integer key, LocalTime[] value) {
        riderStageResults.put(key,  value);
    }

    //returns the checkpoint times for a given stage
    public LocalTime[] getResultsForGivenStage(Integer key){
        if (riderStageResults.get(key) == null) {
            return new LocalTime[0];
        }
        return  riderStageResults.get(key);
    }

    //removes a stage's results from the rider
    public void removeResultsForStage(Integer key){
        riderStageResults.remove(key);
    }

    //</editor-fold>

}