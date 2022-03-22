package cycling;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.time.LocalTime;

public class Rider implements Serializable {
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
    //total elapsed times for race
    private HashMap<Integer, LocalTime> totalElapsedTimeForRace = new HashMap<Integer, LocalTime>();

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

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Rider.count = count;
    }

    //</editor-fold>

    //<editor-fold desc="___________________________________Methods___________________________________">
    /**
     * This method checks if a stages result is already set
     *
     * @param stage the stage which we want to check has results
     */
    public boolean hasResult(Stage stage){
        return riderStageResults.containsKey(stage);
    }

    /**
     * This method adds a stages result to the stage results hash map
     *
     * @param stageId the id of the Stage instance to add
     * @param checkpoints the time for the stage
     */
    public void addResultsToHashMap(Integer stageId, LocalTime[] checkpoints) {
        riderStageResults.put(stageId,  checkpoints);
        riderStageElapsedTimes.put(stageId, calculateElapsedTimeForGivenStage(stageId));
        riderAdjustedStageElapsedTimes.put(stageId,  calculateElapsedTimeForGivenStage(stageId));
    }

    /**
     * This method returns the elapsed time for a given stage
     *
     * @param stageId  the id of the stage which we want to get the elapsed time for
     */
    public LocalTime getElapsedTimeForGivenStage(Integer stageId){
        return riderStageElapsedTimes.get(stageId);
    }

    /**
     * This method returns the checkpoint times for a given stage
     *
     * @param stageId the id of the stage which we want to get results for
     */
    public LocalTime[] getResultsForGivenStage(Integer stageId) {
        if (riderStageResults.get(stageId) == null) {
            return new LocalTime[0];
        }
        return riderStageResults.get(stageId);
    }

    /**
     * This method returns the finish time for a given stage
     *
     * @param stageId the id of the stage which we want to get the finish time for
     */
    public LocalTime getFinishTimeForStage(Integer stageId) {
        return riderStageResults.get(stageId)[riderStageResults.get(stageId).length-1];
    }

    /**
     * This method removes a stage's results from the rider
     *
     * @param stageId the id of the stage which we want to remove the results from
     */
    public void removeResultsForStage(Integer stageId){
        riderStageResults.remove(stageId);
    }

    /**
     * This method calculates the elapsed time for a stage
     *
     * @param stageId the id of the stage which we want to calculate the elasped time for
     */
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

    /**
     * This method gets the adjusted elapsed time for a stage
     *
     * @param stageId the id of the stage which we wish to get the adjusted elapsed time for
     */
    public LocalTime getAdjustedElapsedTime(int stageId){
        return riderAdjustedStageElapsedTimes.get(stageId);
    }

    /**
     * This method sets the adjusted elapsed time for a stage
     *
     * @param stageId the id of the stage whose adjusted elapsed time we want to set
     * @param time the adjusted elapsed time for the given stage
     */
    public void setAdjustedElapsedTime(int stageId, LocalTime time){
        riderAdjustedStageElapsedTimes.replace(stageId, time);
    }

    /**
     * This method gets the time for a segment
     *
     * @param stage an instance of Stage to which contains the segment
     * @param segment an instance of Segment which is the segment to get the time for
     */
    public LocalTime getTimeForSegmentOfStage(Stage stage, Segment segment){
        return riderStageResults.get(stage.getStageId())[stage.getStageSegments().indexOf(segment)+1];
    }

    /**
     * This method gets the total elapsed time for a given race
     *
     * @param raceId the id of the race oto get the total elapsed time for
     */
    public LocalTime getTotalElapsedTimeForGivenRace(int raceId){
        return totalElapsedTimeForRace.get(raceId);
    }

    /**
     * This method adds the total elapsed time for a race
     *
     * @param raceId the id of the race we want to add the total elapsed time to
     * @param totalElapsedTime the total elapsed time we want to set
     */
    public void addTotalElapsedTimeToRace(int raceId, LocalTime totalElapsedTime){
        totalElapsedTimeForRace.put(raceId, totalElapsedTime);
    }

    //</editor-fold>

}