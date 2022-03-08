package cycling;

public class Segment{
    //<editor-fold desc="__________________________________Atributes__________________________________">
    //static instances counter
    private static int count = 0;
    //segment's id
    private Integer segmentId;
    //segment's location in stage
    private Double segmentLocationInStage;
    //type of segment - an enum
    private Enum<SegmentType> segmentType;

    //</editor-fold>

    //<editor-fold desc="_________________________________Constructor_________________________________">
    public  Segment(Double segmentLocationInStage, Enum<SegmentType> segmentType){
        this.segmentLocationInStage = segmentLocationInStage;
        this.segmentType = segmentType;
        this.segmentId=count++;
    }

    //</editor-fold>

    //<editor-fold desc="_____________________________Getters And Setters_____________________________">
    public Integer getSegmentId() {
        return segmentId;
    }

    //</editor-fold>

    //<editor-fold desc="___________________________________Methods___________________________________">
    //deleteSegment

    //</editor-fold>

}