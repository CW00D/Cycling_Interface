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
    //teams - list of all teams
    private ArrayList<Team> teamList = new ArrayList<Team>();


    //</editor-fold>

    //___________________________________Methods___________________________________
    //<editor-fold desc="________________________________Handle Races_________________________________#Need to Finish removeRaceById">
    @Override
    public int[] getRaceIds() {
        //returning a list of ids of all the races in the portal
        int[] idList =  new int[raceList.size()];
        for (int i=0; i<raceList.size(); i++){
            idList[i] = (raceList.get(i).getRaceId());
        }
        return idList;
    }

    @Override
    public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        //handling exception
        for (int i=0; i<raceList.size(); i++){
            if (raceList.get(i).getRaceName() == name){
                throw new IllegalNameException();
            }
        }
        if (name == null || name == "" || name.length()>30 || name.contains(" ")){
            throw new InvalidNameException();
        }

        //creating a new race
        Race newRace = new Race(name, description);
        raceList.add(newRace);
        return newRace.getRaceId();
    }

    @Override
    public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
        //finding relevant race for the given id
        Race raceForGivenId = null;
        for (int i=0; i<raceList.size(); i++){
            if (raceList.get(i).getRaceId() == raceId){
                raceForGivenId = raceList.get(i);
            }
        }

        //handling exception
        if (raceForGivenId == null){
            throw new IDNotRecognisedException();
        }

        //creating the string to be returned
        String raceName = raceForGivenId.getRaceName();
        String description = raceForGivenId.getRaceDescription();
        String numOfStages = Integer.toString(raceForGivenId.getNumberOfStages());
        double  totalLength = raceForGivenId.getTotalLength();
        return ("[Race Id: "+ Integer.toString(raceId) + ", Race Name: " + raceName + ", Race Description: " + description + ", Number Of Stages: " + numOfStages + ", Total Length: " + totalLength +"]");
    }

    @Override
    //We still need to remove all attached stages segments etc
    public void removeRaceById(int raceId) throws IDNotRecognisedException {
        //finding relevant race for the given id
        Race raceForGivenId = null;
        for (int i=0; i<raceList.size(); i++){
            if (raceList.get(i).getRaceId() == raceId){
                raceForGivenId = raceList.get(i);
            }
        }

        //handling exception
        if (raceForGivenId == null){
            throw new IDNotRecognisedException();
        }

        //removing all related stages segments, etc.
        raceList.remove(raceForGivenId);

    }

    @Override
    public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
        //finding relevant race for the given id
        Race raceForGivenId = null;
        for (int i=0; i<raceList.size(); i++){
            if (raceList.get(i).getRaceId() == raceId){
                raceForGivenId = raceList.get(i);
            }
        }
        //handling exception
        if (raceForGivenId == null){
            throw new IDNotRecognisedException();
        }
        //returning the number of stages for the race with the given id
        return raceForGivenId.getNumberOfStages();
    }

    @Override
    public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
        //finding relevant race for the given id
        Race raceForGivenId = null;
        for (int i=0; i<raceList.size(); i++){
            if (raceList.get(i).getRaceId() == raceId){
                raceForGivenId = raceList.get(i);
            }
        }

        //handling exception
        if (raceForGivenId == null) { throw new IDNotRecognisedException(); }
        for (int i=0; i<raceList.size(); i++){
            int[] stageIds = raceList.get(i).getStageIds();
            for(int j=0; j<stageIds.length; j++) {
                if (raceList.get(i).getStagesForGivenId(stageIds[j]).getStageName() == stageName) {
                    throw new IllegalNameException();
                }
            }
        }
        if (stageName == null || stageName == "" || stageName.length()>30){
            throw new InvalidNameException();
        }
        if (length < 5.0) {
            throw new InvalidLengthException();
        } //asuming double cannot be null as this is a primitive type and would raise an error when passing into funtion

        //returns the id of the stage created
        Stage newStage = new Stage(stageName, description, length, startTime, type);
        raceForGivenId.addStage(newStage);
        return newStage.getStageId();
    }

    @Override
    public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
        //finding relevant race for the given id
        Race raceForGivenId = null;
        for (int i=0; i<raceList.size(); i++){
            if (raceList.get(i).getRaceId() == raceId){
                raceForGivenId = raceList.get(i);
            }
        }

        //handling exception
        if (raceForGivenId == null){
            throw new IDNotRecognisedException();
        }

        //returning the stages for the race with the given id
        return raceForGivenId.getStageIds();//asuming that stages of a race are added in order as not specified in spec
    }

    //</editor-fold>

    //<editor-fold desc="________________________________Handle Stages________________________________#Need to Finish removeStageById and removeSegment">
    @Override
    public double getStageLength(int stageId) throws IDNotRecognisedException {
        //finding relevant stage for the given id
        Stage stageForGivenId = null;
        for (int i=0; i<raceList.size(); i++){
            int[] stageIds = raceList.get(i).getStageIds();
            for(int j=0; j<stageIds.length; j++) {
                if (stageIds[j] == stageId) {
                    stageForGivenId = raceList.get(i).getStagesForGivenId(j);
                }
            }
        }

        //handling exception
        if (stageForGivenId == null){
            throw new IDNotRecognisedException();
        }

        //returning the length of the stage with the given id
        return stageForGivenId.getStageLength();
    }

    @Override
    public void removeStageById(int stageId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
    }

    @Override
    public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient, Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        //finding relevant stage for the given id
        Stage stageForGivenId = null;
        for (int i=0; i<raceList.size(); i++){
            int[] stageIds = raceList.get(i).getStageIds();
            for(int j=0; j<stageIds.length; j++) {
                if (stageIds[j] == stageId) {
                    stageForGivenId = raceList.get(i).getStagesForGivenId(j);
                }
            }
        }

        //handling exception
        if (stageForGivenId == null) { throw new IDNotRecognisedException(); }
        if (location < 0.0 || location+length > stageForGivenId.getStageLength()) { throw new InvalidLocationException(); }
        if (stageForGivenId.getStageState() == StageState.WAITING_FOR_RESULTS) { throw new InvalidStageStateException(); }
        if (stageForGivenId.getStageType() == StageType.TT) { throw new InvalidStageTypeException(); }

        //creating a new categorized climb
        Segment newSegment = new CategorizedClimbSegment(location, type, averageGradient, length);
        stageForGivenId.addSegment(newSegment);
        return newSegment.getSegmentId();
    }

    @Override
    public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        //finding relevant stage for the given id
        Stage stageForGivenId = null;
        for (int i=0; i<raceList.size(); i++){
            int[] stageIds = raceList.get(i).getStageIds();
            for(int j=0; j<stageIds.length; j++) {
                if (stageIds[j] == stageId) {
                    stageForGivenId = raceList.get(i).getStagesForGivenId(j);
                }
            }
        }

        //handling exception
        if (stageForGivenId == null) { throw new IDNotRecognisedException(); }
        if (location < 0.0 || location > stageForGivenId.getStageLength()) { throw new InvalidLocationException(); }
        if (stageForGivenId.getStageState() == StageState.WAITING_FOR_RESULTS) { throw new InvalidStageStateException(); }
        if (stageForGivenId.getStageType() == StageType.TT) { throw new InvalidStageTypeException(); }

        //creating a new intermediate sprint
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
        //finding relevant stage for the given id
        Stage stageForGivenId = null;
        for (int i=0; i<raceList.size(); i++){
            int[] stageIds = raceList.get(i).getStageIds();
            for(int j=0; j<stageIds.length; j++) {
                if (stageIds[j] == stageId) {
                    stageForGivenId = raceList.get(i).getStagesForGivenId(j);
                }
            }
        }

        //handling exception
        if (stageForGivenId == null) { throw new IDNotRecognisedException(); }
        if (stageForGivenId.getStageState() == StageState.WAITING_FOR_RESULTS) { throw new InvalidStageStateException(); }

        //changing the stage with the given id's state
        stageForGivenId.setStageState(StageState.WAITING_FOR_RESULTS);
    }

    @Override
    public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
        //finding relevant stage for the given id
        Stage stageForGivenId = null;
        for (int i=0; i<raceList.size(); i++){
            int[] stageIds = raceList.get(i).getStageIds();
            for(int j=0; j<stageIds.length; j++) {
                if (stageIds[j] == stageId) {
                    stageForGivenId = raceList.get(i).getStagesForGivenId(j);
                }
            }
        }

        //handling exception
        if (stageForGivenId == null){ throw new IDNotRecognisedException(); }

        //returning the segments for the stage with the given id
        return stageForGivenId.getSegmentIds();//asuming that stages of a race are added in order as not specified in spec
    }

    //</editor-fold>

    //<editor-fold desc="________________________________Handle Teams_________________________________#Need to Finish removeTeam">
    @Override
    public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
        //handling exception
        for (int i=0; i<teamList.size(); i++){
            if (teamList.get(i).getTeamName() == name){
                throw new IllegalNameException();
            }
        }
        if (name == null || name == "" || name.length()>30) {
            throw new InvalidNameException();
        }

        //creating a new team
        Team newTeam = new Team(name, description);
        teamList.add(newTeam);
        return newTeam.getTeamId();
    }

    @Override
    public void removeTeam(int teamId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub

    }

    @Override
    public int[] getTeams() {
        //returning a list of all the ids of the teams in the portal
        int[] idList =  new int[teamList.size()];
        for (int i=0; i<teamList.size(); i++){
            idList[i] = (teamList.get(i).getTeamId());
        }
        return idList;
    }

    @Override
    public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
        //finding relevant team for the given id
        Team teamForGivenId = null;
        for (int i=0; i<teamList.size(); i++){
            if (teamList.get(i).getTeamId() == teamId){
                teamForGivenId = teamList.get(i);
            }
        }

        //handling exception
        if (teamForGivenId == null){ throw new IDNotRecognisedException(); }

        //returning the riders for the team with the given id
        return teamForGivenId.getRiderIds();
    }

    //</editor-fold>

    //<editor-fold desc="________________________________Handle Riders________________________________#Need to Finish removeRider">
    @Override
    public int createRider(int teamID, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException {
        //finding relevant team for the given id
        Team teamForGivenId = null;
        for (int i=0; i<teamList.size(); i++){
            if (teamList.get(i).getTeamId() == teamID){
                teamForGivenId = teamList.get(i);
            }
        }

        //handling exceptions
        if (teamForGivenId == null) { throw new IDNotRecognisedException(); }
        if (name == null || yearOfBirth<1900) { throw new IllegalArgumentException(); }

        //creating a new rider
        Rider newRider = new Rider(name, yearOfBirth);
        teamForGivenId.addRider(newRider);
        return newRider.getRiderId();
    }

    @Override
    public void removeRider(int riderId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub

    }

    //</editor-fold>

    //<editor-fold desc="_____________________________Handle Stage Results____________________________#Need to finish getRiderResultsInStage">
    @Override
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints) throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException, InvalidStageStateException {
        ///finding relevant rider for the given id
        Rider riderForGivenId = null;
        for (int i=0; i<teamList.size(); i++){
            if (teamList.get(i).hasRiderWithGivenId(riderId)!=null){
                riderForGivenId = teamList.get(i).hasRiderWithGivenId(riderId);
            }
        }

        //finding relevant stage for the given id
        Stage stageForGivenId = null;
        for (int i=0; i<raceList.size(); i++){
            int[] stageIds = raceList.get(i).getStageIds();
            for(int j=0; j<stageIds.length; j++) {
                if (stageIds[j] == stageId) {
                    stageForGivenId = raceList.get(i).getStagesForGivenId(j);
                }
            }
        }

        //handling exceptions
        if (riderForGivenId == null || stageForGivenId == null) { throw new IDNotRecognisedException(); }
        if (riderForGivenId.hasResult(stageForGivenId)) { throw new DuplicatedResultException(); }
        if ((stageForGivenId.getSegmentIds().length + 2) != checkpoints.length) {throw new InvalidCheckpointsException(); }
        if (stageForGivenId.getStageState() != StageState.WAITING_FOR_RESULTS) { throw new InvalidStageStateException(); }

        riderForGivenId.addResultToHashMap(Integer.valueOf(stageId), checkpoints);
    }

    @Override
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        ///finding relevant rider for the given id
        Rider riderForGivenId = null;
        for (int i=0; i<teamList.size(); i++){
            if (teamList.get(i).hasRiderWithGivenId(riderId)!=null){
                riderForGivenId = teamList.get(i).hasRiderWithGivenId(riderId);
            }
        }

        //finding relevant stage for the given id
        Stage stageForGivenId = null;
        for (int i=0; i<raceList.size(); i++){
            int[] stageIds = raceList.get(i).getStageIds();
            for(int j=0; j<stageIds.length; j++) {
                if (stageIds[j] == stageId) {
                    stageForGivenId = raceList.get(i).getStagesForGivenId(j);
                }
            }
        }

        //handling exceptions
        if (riderForGivenId == null || stageForGivenId == null) { throw new IDNotRecognisedException(); }

        return riderForGivenId.getResultsForGivenStage(Integer.valueOf(stageId));//also have to return elapsed time?
    }

    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        ///finding relevant rider for the given id
        Rider riderForGivenId = null;
        for (int i=0; i<teamList.size(); i++){
            if (teamList.get(i).hasRiderWithGivenId(riderId)!=null){
                riderForGivenId = teamList.get(i).hasRiderWithGivenId(riderId);
            }
        }

        //finding relevant stage for the given id
        Stage stageForGivenId = null;
        for (int i=0; i<raceList.size(); i++){
            int[] stageIds = raceList.get(i).getStageIds();
            for(int j=0; j<stageIds.length; j++) {
                if (stageIds[j] == stageId) {
                    stageForGivenId = raceList.get(i).getStagesForGivenId(j);
                }
            }
        }

        //handling exceptions
        if (riderForGivenId == null || stageForGivenId == null) { throw new IDNotRecognisedException(); }

        riderForGivenId.removeResultsForStage(Integer.valueOf(stageId));

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

    //</editor-fold>

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