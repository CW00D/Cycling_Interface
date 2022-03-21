import cycling.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortalInterface interface -- note you
 * will want to increase these checks, and run it on your CyclingPortal class
 * (not the BadCyclingPortal class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class CyclingPortalInterfaceTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) throws InvalidNameException, IllegalNameException, IDNotRecognisedException, InvalidLengthException, InvalidStageStateException, InvalidLocationException, InvalidStageTypeException, DuplicatedResultException, InvalidCheckpointsException, IOException, ClassNotFoundException, NameNotRecognisedException {
		System.out.println("The system compiled and started the execution...");

		//MiniCyclingPortalInterface portal = new BadMiniCyclingPortal();
		CyclingPortalInterface portal = new CyclingPortal();
		assert (portal.getRaceIds().length == 0) : "Initial SocialMediaPlatform not empty as required or not returning an empty array.";
		//---------------------------------------------Testing Race Handling--------------------------------------------
		//getRaceIds
		//int[] raceIds = portal.getRaceIds();
		//System.out.println("Race Ids of races in system: ");
		//for(int i = 0; i<raceIds.length; i++){System.out.println("Race "+i+"'s Id: "+raceIds[i]);}

		//creatRace
		//int testRaceId = portal.createRace("Test", "Testing creation of a race");
		//System.out.println("testRaceId = "+testRaceId);
		/* InvalidNameException:
		portal.createRace("","Testing if InvalidNameException is thrown");
		portal.createRace("contains white space ","Testing if InvalidNameException is thrown");
		portal.createRace("TestNameGreaterThanThirtyCharacters","Testing if InvalidNameException is thrown");
		IllegalNameException:
		portal.createRace("Test","Testing if IllegalNameException is thrown");
		*/
		// viewRaceDetails
		//String testRaceDetails = portal.viewRaceDetails(testRaceId);
		//System.out.println("Race details: "+testRaceDetails);
		/* IDNotRecognisedException
		portal.viewRaceDetails(123456789);
		*/
		// ***STILL TO DO*** removeRaceById
		// getNumberOfStages
		//int numberOfTestStages = portal.getNumberOfStages(testRaceId);
		//System.out.println("Number of stages in Test = "+numberOfTestStages);
		// addStageToRace
		//LocalDateTime testStageTime = LocalDateTime.of(2022,03,25,12,0,0);
		//int testStageId = portal.addStageToRace(testRaceId, "testStage","first stage of test race",5.5, testStageTime,StageType.FLAT);
		// int testStageId2 = portal.addStageToRace(testRaceId, "testStage","first stage of test race",5.5, testStageTime,StageType.FLAT);
		/* InvalidLengthException
		portal.addStageToRace(testRaceId, "testStage","first stage of test race",4.5, testStageTime,StageType.FLAT);
		 */
		// getRaceStages
		//int[] stagesInTestRace = portal.getRaceStages(testRaceId);
		//System.out.println("Stage Ids of stages in test race: ");
		//for(int i = 0; i<stagesInTestRace.length; i++){System.out.println("Stage "+i+"'s Id: "+stagesInTestRace[i]);}
		//---------------------------------------------Testing stage Handling--------------------------------------------
		// getStageLength *** when does stage id get added to stage list ??? - unable to find stage(two stage lists)
		//double testStageLength = portal.getStageLength(testStageId);
		//System.out.println("test stage length = "+testStageLength);
		// ***STILL TO DO*** removeStageById
		// addCategorizedClimbToStage
		//int testSegmentId = portal.addCategorizedClimbToStage(testStageId,1.0,SegmentType.C4,1.2,1.0);

		/* InvalidLocationException
		int testSegmentId = portal.addCategorizedClimbToStage(testStageId,3.0,SegmentType.C4,1.2,3.0);
		int testSegmentId = portal.addCategorizedClimbToStage(testStageId,-1.0,SegmentType.C4,1.2,1.0);
		InvalidStageStateException when does this get set ?
		InvalidStageTypeException
		int testSegmentId = portal.addCategorizedClimbToStage(testStageId,1.0,SegmentType.TT,1.2,1.0);
		 */
		// ***STILL TO DO*** removeSegment
		// concludeStagePreparation
		//portal.concludeStagePreparation(testStageId);
		//System.out.println("if InvalidStageStateException is thrown next previous works");
		//portal.concludeStagePreparation(testStageId);
		// getStageSegments

		//int[] testSegmentIds = portal.getStageSegments(testStageId);
		//System.out.println("Segment Ids of segments in test segment: ");
		//for(int i = 0; i<testSegmentIds.length; i++){System.out.println("Stage "+i+"'s segment Id: "+testSegmentIds[i]);}
		//---------------------------------------------Testing team Handling--------------------------------------------

		// Creating teams
		int team1Id = portal.createTeam("Team1","testing team1");
		int team2Id = portal.createTeam("Team2","testing team2");
		int team3Id = portal.createTeam("Team3","testing team3");

		// Creating riders
		int rider1Id = portal.createRider(team1Id, "TestRider1",2001);
		int rider2Id = portal.createRider(team2Id, "TestRider2",2002);
		int rider3Id = portal.createRider(team3Id, "TestRider3",2003);
		int otherRider1Id = portal.createRider(team1Id, "otherRider1",2001);
		int otherRider2Id = portal.createRider(team2Id, "otherRider2",2002);
		int otherRider3Id = portal.createRider(team3Id, "otherRider3",2003);
		int otherRider4Id = portal.createRider(team3Id, "otherRider4",2004);

		//Removing a rider
		portal.removeRider(otherRider4Id);

		// Creating races
		int race1 = portal.createRace("Race1","what is the race?");
		int race2 = portal.createRace("Race2","what is the race?");
		int race3 = portal.createRace("Race3","what is the race?");

		// Removing Races
		portal.removeRaceById(race2);
		portal.removeRaceByName("Race3");

		// Adding stages to race
		LocalDateTime testStageTime1 = LocalDateTime.of(2022,03,25,12,0,0);
		int stage1 = portal.addStageToRace(race1,"stage1","flat stage",6,testStageTime1,StageType.FLAT);

		LocalDateTime testStageTime2 = LocalDateTime.of(2022,03,26,12,0,0);
		int stage2 = portal.addStageToRace(race1,"stage2","medium mountain stage",6,testStageTime2,StageType.MEDIUM_MOUNTAIN);

		LocalDateTime testStageTime3 = LocalDateTime.of(2022,03,27,12,0,0);
		int stage3 = portal.addStageToRace(race1,"stage3","High mountain stage",6,testStageTime2,StageType.HIGH_MOUNTAIN);

		LocalDateTime testStageTime4 = LocalDateTime.of(2022,03,28,12,0,0);
		int stage4 = portal.addStageToRace(race1,"stage4","time trial mountain stage",6,testStageTime2,StageType.TT);

		// Adding segments to stage
		int segmentId1 = portal.addCategorizedClimbToStage(stage3, 1.0,SegmentType.C1,1.0,1.0);
		int segmentId2 = portal.addCategorizedClimbToStage(stage3, 2.0,SegmentType.C2,1.0,1.0);
		int segmentId3 = portal.addCategorizedClimbToStage(stage3, 3.0,SegmentType.C3,1.0,1.0);
		int segmentId4 = portal.addCategorizedClimbToStage(stage3, 4.0,SegmentType.C4,1.0,1.0);
		int segmentId5 = portal.addCategorizedClimbToStage(stage3, 5.0,SegmentType.HC,1.0,1.0);
		// !!! can add sprint with addCategorizedClimbToStage (ask if its up the user)
		int segmentId6 = portal.addIntermediateSprintToStage(stage3, 2.5);
		int segmentId7 = portal.addIntermediateSprintToStage(stage3, 3.5);

		//Getting Stage Length
		assert (portal.getStageLength(stage1))==6.0;

		// Removing A Stage
		portal.removeStageById(stage1);
		// Removing A segment
		portal.removeSegment(segmentId7);

		// Viewing Race Details
		String raceDetails = portal.viewRaceDetails(race1);
		System.out.println("Race details: "+raceDetails);

		// Getting Segment Ids From Race
		int[] testSegmentIds = portal.getStageSegments(stage3);
		System.out.println("Segment Ids of segments in stage: "); // !!! unordered
		for(int i = 0; i<testSegmentIds.length; i++){System.out.println(+testSegmentIds[i]);}

		// Conclude Prep
		portal.concludeStagePreparation(stage3);

		// Simulated Times For Rider1 On A Stage
		LocalTime startTime = LocalTime.of(12,00);
		LocalTime segTime1 = LocalTime.of(12,05);
		LocalTime segTime2 = LocalTime.of(12,10);
		LocalTime segTime6 = LocalTime.of(12,13); // added in order
		LocalTime segTime3 = LocalTime.of(12,15);
		LocalTime segTime4 = LocalTime.of(12,20);
		LocalTime segTime5 = LocalTime.of(12,25);
		LocalTime finishTime = LocalTime.of(12,25);
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
		// Registering Riders Results
		portal.registerRiderResultsInStage(stage3,rider2Id,ridersTimes);

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

		portal.saveCyclingPortal("testingSer.ser");
		// single rider results
		LocalTime[] riderResults = portal.getRiderResultsInStage(stage3,rider1Id);
		System.out.println("Riders results: ");
		for(int i = 0; i<riderResults.length; i++){System.out.println(riderResults[i]);}

		LocalTime ridersAdjustedTime = portal.getRiderAdjustedElapsedTimeInStage(stage3,rider1Id);
		System.out.println("Riders adjusted time: "+ridersAdjustedTime);

		portal.saveCyclingPortal("SerialTest.ser");

		// Stage results
		int[] ridersRank = portal.getRidersRankInStage(stage3);
		System.out.println("Riders rank: ");
		for(int i = 0; i<ridersRank.length; i++){System.out.println("RiderId: "+ridersRank[i]);}

		LocalTime[] ridersAdjustedRank = portal.getRankedAdjustedElapsedTimesInStage(stage3);
		System.out.println("Riders adjusted times: ");
		for(int i = 0; i<ridersAdjustedRank.length; i++){System.out.println("Adjusted time: "+ridersAdjustedRank[i]);}

		int[] ridersPoints = portal.getRidersPointsInRace(stage3);
		System.out.println("Riders Points(sorted by elapsed time): ");
		for(int i = 0; i<ridersPoints.length; i++){System.out.println("Points: "+ridersPoints[i]);}

		int[] ridersMountainPoints = portal.getRidersMountainPointsInRace(stage3);
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
