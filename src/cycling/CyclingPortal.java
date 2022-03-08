package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author Christian Wood and Jake Beeson
 * @version 1.0
 *
 */
public class CyclingPortal implements CyclingPortalInterface {
    //<editor-fold desc="__________________________________Atributes__________________________________">
    //races - list of all races
    private ArrayList<Race> raceList = new ArrayList<Race>();
    //stages - list of all stages
    private ArrayList<Stage> stageList = new ArrayList<Stage>();
    //teams - list of all teams
    private ArrayList<Team> teamlist = new ArrayList<Team>();

    //</editor-fold>

    //___________________________________Methods___________________________________
    //<editor-fold desc="________________________________Handle Races_________________________________#Need to Finish removeRaceById">
    @Override
    public int[] getRaceIds() {
        int[] idList =  new int[raceList.size()];
        for (int i=0; i<=raceList.size(); i++){
            idList[i] = (raceList.get(i).getRaceId());
        }
        return idList;
    }

    @Override
    public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        boolean illegalName = false;
        boolean invalidName = false;
        //handling exception
        for (int i=0; i<=raceList.size(); i++){
            if (raceList.get(i).getRaceName() == name){
                illegalName = true;
            }
        }
        if (name == null || name == "" || name.length()>2147483647 || name.contains(" ")){
            invalidName = true;
        }
        if (illegalName) { throw new IllegalNameException(); }
        if (invalidName) {throw new InvalidNameException(); }
        //working normally
        Race newRace = new Race(name, description);
        raceList.add(newRace);
        return newRace.getRaceId();
    }

    @Override
    public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
        Race raceForGivenId = null;
        for (int i=0; i<=raceList.size(); i++){
            if (raceList.get(i).getRaceId() == raceId){
                raceForGivenId = raceList.get(i);
            }
        }
        //handling exception
        if (raceForGivenId == null){
            throw new IDNotRecognisedException();
        }
        String raceName = raceForGivenId.getRaceName();
        String description = raceForGivenId.getRaceDescription();
        String numOfStages = Integer.toString(raceForGivenId.getNumberOfStages());
        double  totalLength = raceForGivenId.getTotalLength();
        return ("[Race Id: "+ Integer.toString(raceId) + ", Race Name: " + raceName + ", Race Description: " + description + ", Number Of Stages: " + numOfStages + ", Total Length: " + totalLength +"]");
    }

    @Override
    //We still need to remove all attached stages segments etc
    public void removeRaceById(int raceId) throws IDNotRecognisedException {
        Race raceForGivenId = null;
        for (int i=0; i<=raceList.size(); i++){
            if (raceList.get(i).getRaceId() == raceId){
                raceForGivenId = raceList.get(i);
            }
        }
        raceList.remove(raceForGivenId);
        //handling exception
        if (raceForGivenId == null){
            throw new IDNotRecognisedException();
        }
    }

    @Override
    public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
        Race raceForGivenId = null;
        for (int i=0; i<=raceList.size(); i++){
            if (raceList.get(i).getRaceId() == raceId){
                raceForGivenId = raceList.get(i);
            }
        }
        //handling exception
        if (raceForGivenId == null){
            throw new IDNotRecognisedException();
        }
        return raceForGivenId.getNumberOfStages();
    }

    @Override
    public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
        boolean idNotRecognised = false;
        boolean illegalName = false;
        boolean invalidName = false;
        boolean invalidLength = false;
        Race raceForGivenId = null;
        for (int i=0; i<=raceList.size(); i++){
            if (raceList.get(i).getRaceId() == raceId){
                raceForGivenId = raceList.get(i);
            }
        }
        //handling exception
        if (raceForGivenId == null) { idNotRecognised = true; }
        for (int i=0; i<=stageList.size(); i++){
            if (stageList.get(i).getStageName() == stageName){
                illegalName = true;
            }
        }
        if (stageName == null || stageName == "" || stageName.length()>2147483647 || stageName.contains(" ")){
            invalidName = true;
        }
        if (length < 5.0) {
            invalidLength = true;
        } //asuming double cannot be null as this is a primitive type and would raise an error when passing into funtion

        if (idNotRecognised) { throw new IDNotRecognisedException(); }
        if (illegalName) { throw new IllegalNameException(); }
        if (invalidName) {throw new InvalidNameException(); }
        if (invalidLength) { throw new InvalidLengthException(); }
        //returns the id of the stage created
        Stage newStage = new Stage(stageName, description, length, startTime, type);
        raceForGivenId.addStage(newStage);
        return newStage.getStageId();
    }

    @Override
    public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
        Race raceForGivenId = null;
        for (int i=0; i<=raceList.size(); i++){
            if (raceList.get(i).getRaceId() == raceId){
                raceForGivenId = raceList.get(i);
            }
        }
        //handling exception
        if (raceForGivenId == null){
            throw new IDNotRecognisedException();
        }
        return raceForGivenId.getStageIds();//asuming that stages of a race are added in order as not specified in spec
    }

    //</editor-fold>

    //<editor-fold desc="________________________________Handle Stages________________________________remove stage by id still to do">
    @Override
    public double getStageLength(int stageId) throws IDNotRecognisedException {
        Stage stageForGivenId = null;
        for (int i=0; i<=stageList.size(); i++){
            if (stageList.get(i).getStageId() == stageId){
                stageForGivenId = stageList.get(i);
            }
        }
        //handling exception
        if (stageForGivenId == null){
            throw new IDNotRecognisedException();
        }
        return stageForGivenId.getStageLength();
    }

    @Override
    public void removeStageById(int stageId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
    }

    @Override
    public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient, Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        boolean idNotRecognised = false;
        boolean invalidLocation = false;
        boolean invalidStageState = false;
        boolean invalidStageType = false;
        Stage stageForGivenId = null;
        for (int i=0; i<=stageList.size(); i++){
            if (stageList.get(i).getStageId() == stageId){
                stageForGivenId = stageList.get(i);
            }
        }
        //handling exception
        if (stageForGivenId == null) { idNotRecognised = true; }
        if (location < 0.0 || location+length > stageForGivenId.getStageLength()) { invalidLocation = true; }
        if (stageForGivenId.getStageState() == StageState.WAITING_FOR_RESULTS) { invalidStageState = true; }
        if (stageForGivenId.getStageType() == StageType.TT) { invalidStageType = true; }

        if (idNotRecognised) { throw new IDNotRecognisedException(); }
        if (invalidLocation) { throw new InvalidLocationException(); }
        if (invalidStageState) { throw new InvalidStageStateException(); }
        if (invalidStageType) { throw new InvalidStageTypeException(); }

        //returns the id of the stage created
        Segment newSegment = new CategorizedClimbSegment(location, type, averageGradient, length);
        stageForGivenId.addSegment(newSegment);
        return newSegment.getSegmentId();
    }

    @Override
    public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        boolean idNotRecognised = false;
        boolean invalidLocation = false;
        boolean invalidStageState = false;
        boolean invalidStageType = false;
        Stage stageForGivenId = null;
        for (int i=0; i<=stageList.size(); i++){
            if (stageList.get(i).getStageId() == stageId){
                stageForGivenId = stageList.get(i);
            }
        }
        //handling exception
        if (stageForGivenId == null) { idNotRecognised = true; }
        if (location < 0.0 || location > stageForGivenId.getStageLength()) { invalidLocation = true; }
        if (stageForGivenId.getStageState() == StageState.WAITING_FOR_RESULTS) { invalidStageState = true; }
        if (stageForGivenId.getStageType() == StageType.TT) { invalidStageType = true; }

        if (idNotRecognised) { throw new IDNotRecognisedException(); }
        if (invalidLocation) { throw new InvalidLocationException(); }
        if (invalidStageState) { throw new InvalidStageStateException(); }
        if (invalidStageType) { throw new InvalidStageTypeException(); }

        //returns the id of the stage created
        Segment newSegment = new IntermediateSprintSegment(location);
        stageForGivenId.addSegment(newSegment);
        return newSegment.getSegmentId();
    }

    @Override
    public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
        // TODO Auto-generated method stub

    }

    @Override
    public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
        // TODO Auto-generated method stub

    }

    @Override
    public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
        Stage stageForGivenId = null;
        for (int i=0; i<=stageList.size(); i++){
            if (stageList.get(i).getStageId() == stageId){
                stageForGivenId = stageList.get(i);
            }
        }
        //handling exception
        if (stageForGivenId == null){
            throw new IDNotRecognisedException();
        }
        return stageForGivenId.getSegmentIds();//asuming that stages of a race are added in order as not specified in spec
    }

    //</editor-fold>

    @Override
    public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
        // TODO Auto-generated method stub
        //returns id of team created
        return 0;
    }

    @Override
    public void removeTeam(int teamId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub

    }

    @Override
    public int[] getTeams() {
        // TODO Auto-generated method stub
        //returns a list of the ids of teams in the system
        return null;
    }

    @Override
    public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        //returns a list of rider ids
        return null;
    }

    @Override
    public int createRider(int teamID, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException {
        // TODO Auto-generated method stub
        //returns the id of the rider created
        return 0;
    }

    @Override
    public void removeRider(int riderId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub

    }

    @Override
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints) throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException, InvalidStageStateException {
        // TODO Auto-generated method stub

    }

    @Override
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub

    }

    @Override
    public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void eraseCyclingPortal() {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveCyclingPortal(String filename) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeRaceByName(String name) throws NameNotRecognisedException {
        // TODO Auto-generated method stub

    }

    @Override
    public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

}