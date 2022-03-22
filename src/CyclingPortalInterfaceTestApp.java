import cycling.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * This is a testing interface that checks that all the functions implemented in the CyclingPortal are executing as expected
 *
 * 
 * @author Christian  Wood and Jake Beeson
 * @version 1.0
 */
public class CyclingPortalInterfaceTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) throws DuplicatedResultException, IDNotRecognisedException, IllegalNameException, InvalidCheckpointsException, InvalidLengthException, InvalidLocationException, InvalidNameException, InvalidStageStateException, InvalidStageTypeException, NameNotRecognisedException {
		//<editor-fold desc="________________________________Testing Setup________________________________
		System.out.println("The system compiled and started the execution...");
		CyclingPortalInterface portal = new CyclingPortal();

		System.out.println("");

		//</editor-fold>

		//<editor-fold desc="________________________________Testing Race_________________________________
		System.out.println("Testing races");
		System.out.println("---------------");

		//getRaceIds
		int[] raceIds = portal.getRaceIds();
		System.out.println("Race Ids of races in system: ");
		for(int i = 0; i<raceIds.length; i++){
			System.out.println(portal.viewRaceDetails(i));
		}

		System.out.println("");

		//createRace
		int testRaceId1 = portal.createRace("Test_1", "Testing creation of a race");
		System.out.println("testRaceId1 = "+testRaceId1);
		int testRaceId2 = portal.createRace("Test_2", "Testing creation of a race");
		System.out.println("testRaceId1 = "+testRaceId2);

		// InvalidNameException:
		try {
			portal.createRace("", "Testing if InvalidNameException is thrown");
		}catch(Exception InvalidNameException){
			System.out.println("InvalidNameException checks empty name");}

		try {
			portal.createRace("contains white space ","Testing if InvalidNameException is thrown");
		}catch(Exception InvalidNameException){
			System.out.println("InvalidNameException check white spaces");
		}

		try {
			portal.createRace("TestNameGreaterThanThirtyCharacters","Testing if InvalidNameException is thrown");
		}catch(Exception IllegalNameException){
			System.out.println("IllegalNameException checks longer than 30 characters");
		}

		try {
			portal.createRace("Test_2", "Testing if IllegalNameException is thrown");
		}catch(Exception IllegalNameException){
			System.out.println("IllegalNameException checks if name already exists");
		}

		System.out.println("");

		raceIds = portal.getRaceIds();
		System.out.println("Race Ids of races in system: ");
		for(int i = 0; i<raceIds.length; i++){
			System.out.println("    "+portal.viewRaceDetails(i));
		}

		System.out.println("");

		// viewRaceDetails
		String testRaceDetails = portal.viewRaceDetails(testRaceId1);
		System.out.println("Race details: "+testRaceDetails);
		try {
			portal.viewRaceDetails(123456789);
		}catch(Exception IDNotRecognisedException){
			System.out.println("IDNotRecognised checks if an id doesn't exist");
		}

		System.out.println("");

		// ***STILL TO DO*** removeRaceById
		// getNumberOfStages
		int numberOfTestStages1 = portal.getNumberOfStages(testRaceId1);
		System.out.println("Number of stages in Test = "+numberOfTestStages1);

		// addStageToRace
		LocalDateTime testStageTime = LocalDateTime.of(2022,03,25,12,0,0);
		int testStageId = portal.addStageToRace(testRaceId1, "testStage","first stage of test race",5.5, testStageTime,StageType.FLAT);
		// int testStageId2 = portal.addStageToRace(testRaceId1, "testStage","first stage of test race",5.5, testStageTime,StageType.FLAT);

		int numberOfTestStages2 = portal.getNumberOfStages(testRaceId1);
		System.out.println("Number of stages in Test = "+numberOfTestStages2);

		/* InvalidLengthException
		portal.addStageToRace(testRaceId1, "testStage","first stage of test race",4.5, testStageTime,StageType.FLAT);
		 */
		// getRaceStages
		int[] stagesInTestRace = portal.getRaceStages(testRaceId1);
		System.out.println("Stage Ids of stages in test race: ");
		for(int i = 0; i<stagesInTestRace.length; i++){System.out.println("    Stage "+i+"'s Id: "+stagesInTestRace[i]);}

		System.out.println("");

		//</editor-fold>


		//<editor-fold desc="________________________________Testing Stage________________________________
		System.out.println("Testing stages");
		System.out.println("---------------");
		// getStageLength *** when does stage id get added to stage list ??? - unable to find stage(two stage lists)
		double testStageLength = portal.getStageLength(testStageId);
		System.out.println("test stage length = "+testStageLength);
		// ***STILL TO DO*** removeStageById
		// addCategorizedClimbToStage
		int testSegmentId = portal.addCategorizedClimbToStage(testStageId,1.0,SegmentType.C4,1.2,1.0);

		/* InvalidLocationException
		int testSegmentId = portal.addCategorizedClimbToStage(testStageId,3.0,SegmentType.C4,1.2,3.0);
		int testSegmentId = portal.addCategorizedClimbToStage(testStageId,-1.0,SegmentType.C4,1.2,1.0);
		InvalidStageStateException when does this get set ?
		InvalidStageTypeException
		int testSegmentId = portal.addCategorizedClimbToStage(testStageId,1.0,SegmentType.TT,1.2,1.0);
		 */
		// ***STILL TO DO*** removeSegment
		// concludeStagePreparation
		portal.concludeStagePreparation(testStageId);
		//System.out.println("if InvalidStageStateException is thrown next previous works");
		//portal.concludeStagePreparation(testStageId);
		// getStageSegments
		int[] testSegmentIds = portal.getStageSegments(testStageId);
		System.out.println("Segment Ids of segments in test segment: ");
		for(int i = 0; i<testSegmentIds.length; i++){System.out.println("Stage "+i+"'s segment Id: "+testSegmentIds[i]);}

		//</editor-fold>

	}
}
