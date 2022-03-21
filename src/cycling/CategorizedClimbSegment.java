package cycling;

import java.io.Serializable;

public class CategorizedClimbSegment extends Segment implements Serializable {
    //<editor-fold desc="__________________________________Atributes__________________________________">
    //segment's average gradient
    private Double segmentAverageGradient;
    //segment's length
    private Double segmentLength;

    //</editor-fold>

    //<editor-fold desc="_________________________________Constructor_________________________________">
    public CategorizedClimbSegment(Double segmentLocationInStage, Enum<SegmentType> segmentType, Double segmentAverageGradient, Double segmentLength) {
        super(segmentLocationInStage, segmentType);
        this.segmentAverageGradient = segmentAverageGradient;
        this.segmentLength = segmentLength;
    }

    //</editor-fold>

}
