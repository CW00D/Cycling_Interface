package cycling;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Stage {
    //<editor-fold desc="__________________________________Atributes__________________________________">
    //static instances counter
    private static int count = 0;
    //stage's id
    private Integer stageId;
    //stage's name
    private String stageName;
    //stage's description
    private String stageDescription;
    //stage's length
    private double stageLength;
    //stage's start time
    private LocalDateTime stageStartTime;
    //type of stage - an enum
    private StageType stageType;
    //state of stage - might be an enum (whether waiting for results, eacing etc)
    private StageState stageState = StageState.PREPARING_STAGE;       //this might be an enum type lets discuss. I dont really understand enums so Jake can explain.
    //stage rider results
    private ArrayList<LocalDateTime> riderResults = new ArrayList<LocalDateTime>();
    //stage segments
    private ArrayList<Segment> stageSegments = new ArrayList<Segment>();//split to 2 segments

    //</editor-fold>

    //<editor-fold desc="_________________________________Constructor_________________________________">
    public  Stage(String stageName, String stageDescription, double stageLength, LocalDateTime stageStartTime, StageType stageType){
        this.stageName = stageName;
        this.stageDescription = stageDescription;
        this.stageLength = stageLength;
        this.stageStartTime = stageStartTime;
        this.stageType = stageType;
        this.stageId=count++;
    }

    //</editor-fold>

    //<editor-fold desc="_____________________________Getters And Setters_____________________________">
    public Integer getStageId() {
        return stageId;
    }
    public String getStageName() { return stageName; }
    public double getStageLength() { return stageLength; }
    public StageState getStageState() { return stageState; }
    public StageType getStageType() { return stageType; }

    //</editor-fold>

    //<editor-fold desc="___________________________________Methods___________________________________">
    //getLength
    //deleteStage
    //addSegment - adds a climb segment or a sprint segment to a stage
    public void addSegment(Segment newSegment) {
        stageSegments.add(newSegment);
    }
    //setState
    //getSegments - returns the list of segment ids ordered from first to last according to location in stage
    public int[] getSegmentIds() {
        int[] idList =  new int[stageSegments.size()];
        for (int i=0; i<stageSegments.size(); i++){
            idList[i] = (stageSegments.get(i).getSegmentId());
        }
        return idList;
    }
    //recordResult - adds a rider and their time into the riderResults

    //</editor-fold>

}