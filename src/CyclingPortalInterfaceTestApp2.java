import cycling.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This is a testing interface that checks that all the functions implemented in the CyclingPortal are executing as expected
 *
 * 
 * @author Christian  Wood and Jake Beeson
 * @version 1.0
 */
public class CyclingPortalInterfaceTestApp2 {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) throws DuplicatedResultException, IDNotRecognisedException, IllegalNameException, InvalidCheckpointsException, InvalidLengthException, InvalidLocationException, InvalidNameException, InvalidStageStateException, InvalidStageTypeException, NameNotRecognisedException, IOException {
		// stage 5 neeed to duplicate from stage 3 then delete.
		System.out.println("The system compiled and started the execution...");
		CyclingPortalInterface portal = new CyclingPortal();

		int team1Id = portal.createTeam("Team1","testing team1");
		int team2Id = portal.createTeam("Team2","testing team2");
		// team to be removed
		int team3Id = portal.createTeam("Team3","testing team3");
		System.out.println("Created teams");

		// Creating riders
		int rider1Id = portal.createRider(team1Id, "TestRider1",2001);
		int rider2Id = portal.createRider(team2Id, "TestRider2",2002);
		int rider3Id = portal.createRider(team3Id, "TestRider3",2003);
		int otherRider1Id = portal.createRider(team1Id, "otherRider1",2001);
		int otherRider2Id = portal.createRider(team2Id, "otherRider2",2002);
		int otherRider3Id = portal.createRider(team3Id, "otherRider3",2003);
		// rider to be removed
		int otherRider4Id = portal.createRider(team3Id, "otherRider4",2004);
		System.out.println("Created riders");


		// get team Ids
		int[] teamIds = portal.getTeams();
		assert teamIds.length==2;
		// get riderIds for team 1
		int[] riderIds = portal.getTeamRiders(team1Id);
		assert riderIds.length == 2;


		// Creating races
		int race1 = portal.createRace("Race1","what is the race?");
		int race2 = portal.createRace("Race2","what is the race?"); // race to be removed (must add results)
		int race3 = portal.createRace("Race3","what is the race?"); // race to be removed
		System.out.println("Created Races");


		// Adding stages to race
		// setting times
		LocalDateTime testStageTime1 = LocalDateTime.of(2022,03,25,12,0,0);
		LocalDateTime testStageTime2 = LocalDateTime.of(2022,03,26,12,0,0);
		LocalDateTime testStageTime3 = LocalDateTime.of(2022,03,27,12,0,0);
		LocalDateTime testStageTime4 = LocalDateTime.of(2022,03,28,12,0,0);
		LocalDateTime testStageTime5 = LocalDateTime.of(2022,03,28,12,0,0);

		int stage1 = portal.addStageToRace(race1,"stage1","flat stage",6,testStageTime1,StageType.FLAT);
		int stage2 = portal.addStageToRace(race1,"stage2","medium mountain stage",6,testStageTime2,StageType.MEDIUM_MOUNTAIN);
		int stage3 = portal.addStageToRace(race1,"stage3","High mountain stage",6,testStageTime2,StageType.HIGH_MOUNTAIN);
		int stage4 = portal.addStageToRace(race1,"stage4","time trial mountain stage",6,testStageTime2,StageType.TT);
		// stage to be removed (add segments)
		int stage5 = portal.addStageToRace(race1,"stage5","time trial mountain stage",6,testStageTime2,StageType.FLAT);
		System.out.println("added stages");

		// Adding segments to stage
		int segmentId1 = portal.addCategorizedClimbToStage(stage3, 1.0,SegmentType.C1,1.0,1.0);
		int segmentId2 = portal.addCategorizedClimbToStage(stage3, 2.0,SegmentType.C2,1.0,1.0);
		int segmentId3 = portal.addCategorizedClimbToStage(stage3, 3.0,SegmentType.C3,1.0,1.0);
		int segmentId4 = portal.addCategorizedClimbToStage(stage3, 4.0,SegmentType.C4,1.0,1.0);
		int segmentId5 = portal.addCategorizedClimbToStage(stage3, 5.0,SegmentType.HC,1.0,1.0);
		int segmentId6 = portal.addIntermediateSprintToStage(stage3, 2.5);

		// segments that would be removed
		int segmentId7 = portal.addIntermediateSprintToStage(stage5, 3.5);
		int segmentId8 = portal.addIntermediateSprintToStage(stage3, 3.5);
		System.out.println("added segments");

		// Removing segment
		portal.removeSegment(segmentId8);

		// Conclude Prep
		portal.concludeStagePreparation(stage1);
		portal.concludeStagePreparation(stage2);
		portal.concludeStagePreparation(stage3);
		portal.concludeStagePreparation(stage4);
		portal.concludeStagePreparation(stage5);

		// checking order of segments
		int[] testSegmentIds = portal.getStageSegments(stage3);
		System.out.println("Segment Ids of segments in stage: ");
		for(int i = 0; i<testSegmentIds.length; i++){System.out.println(+testSegmentIds[i]);}

		//Getting Stage Length
		assert (portal.getStageLength(stage1))==6.0;

		// Viewing Race Details
		String raceDetails = portal.viewRaceDetails(race1);
		System.out.println("Race details: "+raceDetails);

		// Getting Segment Ids From Race
      /*int[] testSegmentIds = portal.getStageSegments(stage3);
      System.out.println("Segment Ids of segments in stage: "); // !!! unordered
      for(int i = 0; i<testSegmentIds.length; i++){System.out.println(+testSegmentIds[i]);}*/


		// Simulated Times For Rider1 On A Stage
		LocalTime startTime = LocalTime.of(12,00);
		LocalTime segTime1 = LocalTime.of(12,05);
		LocalTime segTime2 = LocalTime.of(12,10);
		LocalTime segTime6 = LocalTime.of(12,13); // added in order
		LocalTime segTime3 = LocalTime.of(12,15);
		LocalTime segTime4 = LocalTime.of(12,20);
		LocalTime segTime5 = LocalTime.of(12,25);
		LocalTime finishTime = LocalTime.of(12,39,59,6);

		LocalTime[] ridersOtherTimes = new LocalTime[]{
				startTime,
				finishTime
		};
		// assume order
		LocalTime[] ridersTimes = new LocalTime[]{
				startTime,
				segTime1,
				segTime2,
				segTime6,
				segTime3,
				segTime4,
				segTime5,
				finishTime
		};
		// Registering Riders Results
		portal.registerRiderResultsInStage(stage3,rider1Id,ridersTimes);
		portal.registerRiderResultsInStage(stage1,rider1Id,ridersOtherTimes);
		portal.registerRiderResultsInStage(stage2,rider1Id,ridersOtherTimes);
		portal.registerRiderResultsInStage(stage4,rider1Id,ridersOtherTimes);

		// Simulated Times For Rider2 On A Stage
		startTime = LocalTime.of(12, 00);
		segTime1 = LocalTime.of(12,07);
		segTime2 = LocalTime.of(12, 11);
		segTime6 = LocalTime.of(12, 16);
		segTime3 = LocalTime.of(12, 17);
		segTime4 = LocalTime.of(12, 25);
		segTime5 = LocalTime.of(12, 30);
		finishTime = LocalTime.of(12, 40);
		// assume order
		ridersTimes = new LocalTime[]{
				startTime,
				segTime1,
				segTime2,
				segTime6,
				segTime3,
				segTime4,
				segTime5,
				finishTime
		};
		ridersOtherTimes = new LocalTime[]{
				startTime,
				finishTime
		};
		// Registering Riders Results
		portal.registerRiderResultsInStage(stage3,rider2Id,ridersTimes);
		portal.registerRiderResultsInStage(stage1,rider2Id,ridersOtherTimes);
		portal.registerRiderResultsInStage(stage2,rider2Id,ridersOtherTimes);
		portal.registerRiderResultsInStage(stage4,rider2Id,ridersOtherTimes);

		// Simulated Times For Rider to be deleted On A Stage
		startTime = LocalTime.of(12, 00);
		segTime1 = LocalTime.of(12,10);
		segTime2 = LocalTime.of(12, 12);
		segTime6 = LocalTime.of(12, 18);
		segTime3 = LocalTime.of(12, 25);
		segTime4 = LocalTime.of(12, 30);
		segTime5 = LocalTime.of(12, 35);
		finishTime = LocalTime.of(13, 10);
		// assume order
		ridersTimes = new LocalTime[]{
				startTime,
				segTime1,
				segTime2,
				segTime6,
				segTime3,
				segTime4,
				segTime5,
				finishTime
		};
		// Registering Riders Results
		portal.registerRiderResultsInStage(stage3,otherRider1Id,ridersTimes);

		//Removing riders results
		portal.deleteRiderResultsInStage(stage3,otherRider1Id);

		// Removing Races
		portal.removeRaceById(race2); // add results
		portal.removeRaceByName("Race3");
		System.out.println("Removed races");

		//Removing a rider
		portal.removeRider(otherRider4Id);
		//Removing a team
		portal.removeTeam(team3Id);
		// removing stage
		portal.removeStageById(stage5);


		// stage results
		LocalTime[] riderResults = portal.getRiderResultsInStage(stage3,rider1Id);
		System.out.println("Riders results: ");
		for(int i = 0; i<riderResults.length; i++){System.out.println(riderResults[i]);}

		LocalTime ridersAdjustedTime = portal.getRiderAdjustedElapsedTimeInStage(stage3,rider1Id);
		System.out.println("Riders adjusted time: "+ridersAdjustedTime);

		int[] ridersRank = portal.getRidersRankInStage(stage3);
		System.out.println("Riders rank: ");
		for(int i = 0; i<ridersRank.length; i++){System.out.println("RiderId: "+ridersRank[i]);}

		LocalTime[] ridersAdjustedRank = portal.getRankedAdjustedElapsedTimesInStage(stage3);
		System.out.println("Riders adjusted times: ");
		for(int i = 0; i<ridersAdjustedRank.length; i++){System.out.println("Adjusted time: "+ridersAdjustedRank[i]);}

		// riders race results
		int[] ridersPoints = portal.getRidersPointsInRace(race1);
		System.out.println("Riders Points(sorted by elapsed time): ");
		for(int i = 0; i<ridersPoints.length; i++){System.out.println("Points: "+ridersPoints[i]);}

		int[] ridersMountainPoints = portal.getRidersMountainPointsInRace(race1);
		System.out.println("Riders Mountain Points (sorted by finish time): ");
		for(int i = 0; i<ridersMountainPoints.length; i++){System.out.println("M Points: "+ridersMountainPoints[i]);}


		// Race results
		int[] gcRank = portal.getRidersGeneralClassificationRank(race1);
		System.out.println("Riders gc rank: ");
		for(int i = 0; i<gcRank.length; i++){System.out.println("Rider Id: "+gcRank[i]);}

		int[] pointRank = portal.getRidersPointClassificationRank(race1);
		System.out.println("Riders point rank: ");
		for(int i = 0; i<gcRank.length; i++){System.out.println("Rider Id: "+gcRank[i]);}

		int[] mountainRank = portal.getRidersMountainPointClassificationRank(race1);
		System.out.println("Riders mountainRank rank: ");
		for(int i = 0; i<mountainRank.length; i++){System.out.println("Rider Id: "+mountainRank[i]);}



		// Serialisation
		// portal.eraseCyclingPortal();
		// portal.saveCyclingPortal("filename");
		// portal.loadCyclingPortal("testingSer.ser");
	}



}

