package cycling;

import java.io.Serializable;

public class IntermediateSprintSegment extends Segment implements Serializable {
    //<editor-fold desc="_________________________________Constructor_________________________________">
    public IntermediateSprintSegment(Double segmentLocationInStage) {
        super(segmentLocationInStage, SegmentType.SPRINT);
    }

    //</editor-fold>

}
