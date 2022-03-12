import cycling.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

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
	public static void main(String[] args) throws InvalidNameException, IllegalNameException, IDNotRecognisedException, InvalidLengthException, InvalidStageStateException, InvalidLocationException, InvalidStageTypeException {
		System.out.println("The system compiled and started the execution...");

		//MiniCyclingPortalInterface portal = new BadMiniCyclingPortal();
		CyclingPortalInterface portal = new CyclingPortal();
		assert (portal.getRaceIds().length == 0) : "Initial SocialMediaPlatform not empty as required or not returning an empty array.";
		//---------------------------------------------Testing Race Handling--------------------------------------------
		//getRaceIds
		int[] raceIds = portal.getRaceIds();
		System.out.println("Race Ids of races in system: ");
		for(int i = 0; i<raceIds.length; i++){System.out.println("Race "+i+"'s Id: "+raceIds[i]);}

		//creatRace
		int testRaceId = portal.createRace("Test", "Testing creation of a race");
		System.out.println("testRaceId = "+testRaceId);
		/* InvalidNameException:
		portal.createRace("","Testing if InvalidNameException is thrown");
		portal.createRace("contains white space ","Testing if InvalidNameException is thrown");
		portal.createRace("TestNameGreaterThanThirtyCharacters","Testing if InvalidNameException is thrown");
		IllegalNameException:
		portal.createRace("Test","Testing if IllegalNameException is thrown");
		*/
		// viewRaceDetails
		String testRaceDetails = portal.viewRaceDetails(testRaceId);
		System.out.println("Race details: "+testRaceDetails);
		/* IDNotRecognisedException
		portal.viewRaceDetails(123456789);
		*/
		// ***STILL TO DO*** removeRaceById
		// getNumberOfStages
		int numberOfTestStages = portal.getNumberOfStages(testRaceId);
		System.out.println("Number of stages in Test = "+numberOfTestStages);
		// addStageToRace
		LocalDateTime testStageTime = LocalDateTime.of(2022,03,25,12,0,0);
		int testStageId = portal.addStageToRace(testRaceId, "testStage","first stage of test race",5.5, testStageTime,StageType.FLAT);
		/* InvalidLengthException
		portal.addStageToRace(testRaceId, "testStage","first stage of test race",4.5, testStageTime,StageType.FLAT);
		 */
		// getRaceStages
		int[] stagesInTestRace = portal.getRaceStages(testRaceId);
		System.out.println("Stage Ids of stages in test race: ");
		for(int i = 0; i<stagesInTestRace.length; i++){System.out.println("Stage "+i+"'s Id: "+stagesInTestRace[i]);}
		//---------------------------------------------Testing stage Handling--------------------------------------------
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


		//finding relevant stage for the given id
		Stage stageForGivenId = null;
		for (int i=0; i<raceList.size(); i++){
			int[] stageIds = raceList.get(i).getStageIds();
			for(int j=0; j<stageIds.length; j++) {
				if (stageIds[j] == stageId) {
					stageForGivenId = raceList.get(i).getListOfStages(j);
				}
			}
		}
		//Race
		public Stage getListOfStages(int i) {return listOfStages.get(i);}
	}
}
