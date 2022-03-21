package cycling;

import java.time.temporal.ChronoUnit;
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
    //elapsed times for each stage
    private HashMap<Integer, LocalTime> riderStageElapsedTimes = new HashMap<Integer, LocalTime>();
    //adjusted times for each stage
    private HashMap<Integer, LocalTime> riderAdjustedStageElapsedTimes = new HashMap<Integer, LocalTime>();

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

    public HashMap<Integer, LocalTime[]> getRiderStageResults() {
        return riderStageResults;
    }

    //</editor-fold>

    //<editor-fold desc="___________________________________Methods___________________________________">
    //deleteRider - removes rider and all of their results from the system

    //checks if a stages result is already set
    public boolean hasResult(Stage stage){
        return riderStageResults.containsKey(stage);
    }

    //adds a stages result to the stage results hash map
    public void addResultsToHashMap(Integer stageId, LocalTime[] value) {
        riderStageResults.put(stageId,  value);
        riderStageElapsedTimes.put(stageId, calculateElapsedTimeForGivenStage(stageId));
        riderAdjustedStageElapsedTimes.put(stageId,  calculateElapsedTimeForGivenStage(stageId));
    }

    //returns the elapsed time for a given stage
    public LocalTime getElapsedTimeForGivenStage(Integer stageId){
        return riderStageElapsedTimes.get(stageId);
    }

    //returns the checkpoint times for a given stage
    public LocalTime[] getResultsForGivenStage(Integer stageId) {
        if (riderStageResults.get(stageId) == null) {
            return new LocalTime[0];
        }
        return riderStageResults.get(stageId);
    }

    //returns the finish time for a given stage
    public LocalTime getFinishTimeForStage(Integer stageId) {
        return riderStageResults.get(stageId)[riderStageResults.get(stageId).length-1];
    }

    //removes a stage's results from the rider
    public void removeResultsForStage(Integer stageId){
        riderStageResults.remove(stageId);
    }

    //calculates the elapsed time for a stage
    public LocalTime calculateElapsedTimeForGivenStage(int stageId){
        LocalTime start = riderStageResults.get(stageId)[0];
        LocalTime end = riderStageResults.get(stageId)[riderStageResults.get(stageId).length-1];
        long numberOfMillis = start.until(end, ChronoUnit.MILLIS);
        long millisInAnHour = 3600000L;
        long millisInAMinute = 60000L;
        long millisInASecond = 1000L;
        long hours = numberOfMillis / millisInAnHour;
        long minutes = (numberOfMillis % millisInAnHour) / millisInAMinute;
        long seconds = (numberOfMillis % millisInAMinute) / millisInASecond;
        long millis = numberOfMillis % millisInASecond;
        LocalTime elapsedTime = LocalTime.of((int)hours, (int)minutes, (int)seconds, (int)millis);
        return elapsedTime;
    }

    //gets the adjusted elapsed time for a stage
    public LocalTime getAdjustedElapsedTime(int stageId){
        return riderAdjustedStageElapsedTimes.get(stageId);
    }

    public void setAdjustedElapsedTime(int stageId, LocalTime time){
        riderAdjustedStageElapsedTimes.replace(stageId, time);
    }

    public LocalTime getTimeForSegmentOfStage(Stage stage, Segment segment){
        return riderStageResults.get(stage.getStageId())[stage.getStageSegments().indexOf(segment)+1];
    }

    //</editor-fold>

}