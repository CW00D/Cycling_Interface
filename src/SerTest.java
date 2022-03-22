import cycling.*;

import java.io.IOException;
import java.time.LocalDateTime;

public class SerTest {
    public static void main(String[] args) throws DuplicatedResultException, IDNotRecognisedException, IllegalNameException, InvalidCheckpointsException, InvalidLengthException, InvalidLocationException, InvalidNameException, InvalidStageStateException, InvalidStageTypeException, NameNotRecognisedException, IOException, ClassNotFoundException {
        System.out.println("The system compiled and started the execution...");
        CyclingPortalInterface portal = new CyclingPortal();
/*
        int team1Id = portal.createTeam("Team1","testing team1");
        int team2Id = portal.createTeam("Team2","testing team2");
        int team3Id = portal.createTeam("Team3","testing team3");
        System.out.println("Created teams");

        // Creating riders
        int rider1Id = portal.createRider(team1Id, "TestRider1",2001);
        int rider2Id = portal.createRider(team2Id, "TestRider2",2002);
        int rider3Id = portal.createRider(team3Id, "TestRider3",2003);
        int otherRider1Id = portal.createRider(team1Id, "otherRider1",2001);
        int otherRider2Id = portal.createRider(team2Id, "otherRider2",2002);
        int otherRider3Id = portal.createRider(team3Id, "otherRider3",2003);
        int otherRider4Id = portal.createRider(team3Id, "otherRider4",2004);
        System.out.println("Created riders");

        //Removing a rider
        portal.removeRider(otherRider4Id);

        // Creating races
        int race1 = portal.createRace("Race1","what is the race?");
        int race2 = portal.createRace("Race2","what is the race?");
        int race3 = portal.createRace("Race3","what is the race?");
        System.out.println("Created Races");

        // Removing Races
        portal.removeRaceById(race2);
        portal.removeRaceByName("Race3");
        System.out.println("Removed races");

        // Adding stages to race
        LocalDateTime testStageTime1 = LocalDateTime.of(2022,03,25,12,0,0);
        int stage1 = portal.addStageToRace(race1,"stage1","flat stage",6,testStageTime1,StageType.FLAT);

        LocalDateTime testStageTime2 = LocalDateTime.of(2022,03,26,12,0,0);
        int stage2 = portal.addStageToRace(race1,"stage2","medium mountain stage",6,testStageTime2,StageType.MEDIUM_MOUNTAIN);

        LocalDateTime testStageTime3 = LocalDateTime.of(2022,03,27,12,0,0);
        int stage3 = portal.addStageToRace(race1,"stage3","High mountain stage",6,testStageTime2,StageType.HIGH_MOUNTAIN);

        LocalDateTime testStageTime4 = LocalDateTime.of(2022,03,28,12,0,0);
        int stage4 = portal.addStageToRace(race1,"stage4","time trial mountain stage",6,testStageTime2,StageType.TT);
        System.out.println("added stages");

        // Adding segments to stage
        int segmentId1 = portal.addCategorizedClimbToStage(stage3, 5.0,SegmentType.C1,1.0,1.0);
        int segmentId2 = portal.addCategorizedClimbToStage(stage3, 2.0,SegmentType.C2,1.0,1.0);
        int segmentId3 = portal.addCategorizedClimbToStage(stage3, 4.5,SegmentType.C3,1.0,1.0);
        int segmentId4 = portal.addCategorizedClimbToStage(stage3, 4.0,SegmentType.C4,1.0,1.0);
        int segmentId5 = portal.addCategorizedClimbToStage(stage3, 1.0,SegmentType.HC,1.0,1.0);
        // !!! can add sprint with addCategorizedClimbToStage (ask if its up the user)
        int segmentId6 = portal.addIntermediateSprintToStage(stage3, 2.5);
        int segmentId7 = portal.addIntermediateSprintToStage(stage3, 3.5);
        int[] testSegmentIds = portal.getStageSegments(stage3);
        System.out.println("Segment Ids of segments in stage: "); // !!! unordered
        for(int i = 0; i<testSegmentIds.length; i++){System.out.println(+testSegmentIds[i]);}
        System.out.println("added segments");
        portal.saveCyclingPortal("SerTest.ser");*/
        portal.loadCyclingPortal("SerTest.ser");
    }
}
