package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 *
 * @author Christian Wood and Jake Beeson
 * @version 1.0
 *
 */
public class CyclingPortal implements CyclingPortalInterface {
    //<editor-fold desc="__________________________________Attributes__________________________________">
    //races - list of all races
    private ArrayList<Race> raceList = new ArrayList<Race>();
    //teams - list of all teams
    private ArrayList<Team> teamList = new ArrayList<Team>();


    //</editor-fold>

    //___________________________________Methods___________________________________
    //<editor-fold desc="________________________________Handle Races_________________________________#COMPLETED">
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
        for (Race race : raceList) {
            if (race.getRaceName().equals(name)) {
                throw new IllegalNameException();
            }
        }
        if (name == null || name.equals("") || name.length()>30 || name.contains(" ")){
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
        for (Race race : raceList) {
            if (race.getRaceId() == raceId) {
                raceForGivenId = race;
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
        return ("[Race Id: "+ raceId + ", Race Name: " + raceName + ", Race Description: " + description + ", Number Of Stages: " + numOfStages + ", Total Length: " + totalLength +"]");
    }

    @Override
    public void removeRaceById(int raceId) throws IDNotRecognisedException {
        //finding relevant race for the given id
        Race raceForGivenId = null;
        for (Race race : raceList) {
            if (race.getRaceId() == raceId) {
                raceForGivenId = race;
            }
        }

        //handling exception
        if (raceForGivenId == null){
            throw new IDNotRecognisedException();
        }

        //removing race and all related stages segments, etc.
        for (Stage stage : raceForGivenId.getListOfStages()){
            for (Segment segment : stage.getStageSegments()){
                segment = null;
            }
            for (Team team : teamList){
                for (Rider rider : team.getListOfRiders()){
                    rider.removeResultsForStage(stage.getStageId());
                }
            }
            stage = null;
        }

        raceList.remove(raceForGivenId);
        raceForGivenId = null;

    }

    @Override
    public void removeRaceByName(String name) throws NameNotRecognisedException {
        //finding relevant race for the given id
        Race raceForGivenName = null;
        for (Race race : raceList) {
            if (race.getRaceName().equals(name)) {
                raceForGivenName = race;
            }
        }

        //handling exception
        if (raceForGivenName == null){
            throw new NameNotRecognisedException();
        }

        //removing all related stages segments, etc.
        //removing race and all related stages segments, etc.
        for (Stage stage : raceForGivenName.getListOfStages()){
            for (Segment segment : stage.getStageSegments()){
                segment = null;
            }
            for (Team team : teamList){
                for (Rider rider : team.getListOfRiders()){
                    rider.removeResultsForStage(stage.getStageId());
                }
            }
            stage = null;
        }

        raceList.remove(raceForGivenName);
        raceForGivenName = null;

    }

    @Override
    public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
        //finding relevant race for the given id
        Race raceForGivenId = null;
        for (Race race : raceList) {
            if (race.getRaceId() == raceId) {
                raceForGivenId = race;
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
        for (Race race : raceList) {
            if (race.getRaceId() == raceId) {
                raceForGivenId = race;
            }
        }

        //handling exception
        if (raceForGivenId == null) { throw new IDNotRecognisedException(); }
        for (Race race : raceList) {
            for (Stage stage : race.getListOfStages()) {
                if (stage.getStageName().equals(stageName)) {
                    throw new IllegalNameException();
                }
            }
        }
        if (stageName == null || stageName.equals("") || stageName.length()>30 || stageName.contains(" ")){
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
        for (Race race : raceList) {
            if (race.getRaceId() == raceId) {
                raceForGivenId = race;
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

    //<editor-fold desc="________________________________Handle Stages________________________________#COMPLETED">
    @Override
    public double getStageLength(int stageId) throws IDNotRecognisedException {
        //finding relevant stage for the given id
        Stage stageForGivenId = null;
        for (Race race : raceList) {
            for (Stage stage : race.getListOfStages()){
                if (stage.getStageId() == stageId){
                    stageForGivenId = stage;
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
        //finding relevant stage for the given id
        Stage stageForGivenId = null;
        Race raceStageBelongsTo = null;
        for (Race race : raceList) {
            for (Stage stage : race.getListOfStages()){
                if (stage.getStageId() == stageId){
                    stageForGivenId = stage;
                    raceStageBelongsTo = race;
                }
            }
        }

        //handling exception
        if (stageForGivenId == null){
            throw new IDNotRecognisedException();
        }

        //removing the stage and all relevant data
        for (Team team :teamList){
            for (Rider rider : team.getListOfRiders()){
                if (rider.getRiderStageResults().containsKey(stageId)){
                    rider.removeResultsForStage(stageId);
                }
            }
        }

        for(Segment segment : stageForGivenId.getStageSegments()){
            segment = null;
        }

        raceStageBelongsTo.removeStage(stageForGivenId);
        stageForGivenId = null;
    }

    @Override
    public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient, Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        //finding relevant stage for the given id
        Stage stageForGivenId = null;
        for (Race race : raceList) {
            for (Stage stage : race.getListOfStages()){
                if (stage.getStageId() == stageId){
                    stageForGivenId = stage;
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
        for (Race race : raceList) {
            for (Stage stage : race.getListOfStages()){
                if (stage.getStageId() == stageId){
                    stageForGivenId = stage;
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
        //finding relevant stage for the given id
        Segment segmentForGivenId = null;
        Stage stageSegmentBelongsTo = null;
        for (Race race : raceList) {
            for (Stage stage : race.getListOfStages()) {
                for (Segment segment : stage.getStageSegments()){
                    if (segment.getSegmentId() == segmentId){
                        segmentForGivenId = segment;
                        stageSegmentBelongsTo = stage;
                    }
                }
            }
        }

        //handling exception
        if (segmentForGivenId == null){
            throw new IDNotRecognisedException();
        }
        if (stageSegmentBelongsTo.getStageState() == StageState.WAITING_FOR_RESULTS){
            throw new InvalidStageStateException();
        }

        //removing the segment
        stageSegmentBelongsTo.removeSegmentFromStage(segmentForGivenId);
        segmentForGivenId = null;
    }

    @Override
    public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
        //finding relevant stage for the given id
        Stage stageForGivenId = null;
        for (Race race : raceList) {
            for (Stage stage : race.getListOfStages()){
                if (stage.getStageId() == stageId){
                    stageForGivenId = stage;
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
        for (Race race : raceList) {
            for (Stage stage : race.getListOfStages()){
                if (stage.getStageId() == stageId){
                    stageForGivenId = stage;
                }
            }
        }

        //handling exception
        if (stageForGivenId == null){ throw new IDNotRecognisedException(); }

        //returning the segments for the stage with the given id
        return stageForGivenId.getSegmentIds();//asuming that stages of a race are added in order as not specified in spec
    }

    //</editor-fold>

    //<editor-fold desc="________________________________Handle Teams_________________________________#COMPLETED">
    @Override
    public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
        //handling exception
        for (Team team : teamList) {
            if (team.getTeamName().equals(name)) {
                throw new IllegalNameException();
            }
        }
        if (name == null || name.equals("") || name.length()>30 || name.contains(" ")) {
            throw new InvalidNameException();
        }

        //creating a new team
        Team newTeam = new Team(name, description);
        teamList.add(newTeam);
        return newTeam.getTeamId();
    }

    @Override
    public void removeTeam(int teamId) throws IDNotRecognisedException {
        //finding team for the given id
        Team teamForGivenId = null;
        for (Team team : teamList){
            if (team.getTeamId() == teamId){
                teamForGivenId = team;
            }
        }

        //handling exception
        if (teamForGivenId == null){
            throw new IDNotRecognisedException();
        }

        //removing team and all related riders
        for (Rider rider : teamForGivenId.getListOfRiders()){
            rider = null;
        }
        teamForGivenId = null;
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
        for (Team team : teamList) {
            if (team.getTeamId() == teamId) {
                teamForGivenId = team;
            }
        }

        //handling exception
        if (teamForGivenId == null){ throw new IDNotRecognisedException(); }

        //returning the riders for the team with the given id
        return teamForGivenId.getRiderIds();
    }

    //</editor-fold>

    //<editor-fold desc="________________________________Handle Riders________________________________#COMPLETED">
    @Override
    public int createRider(int teamID, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException {
        //finding relevant team for the given id
        Team teamForGivenId = null;
        for (Team team : teamList) {
            if (team.getTeamId() == teamID) {
                teamForGivenId = team;
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
        //finding the rider for the given id
        Rider riderForGivenId = null;
        Team teamRiderBelongsTo = null;
        for (Team team : teamList){
            for (Rider rider : team.getListOfRiders()){
                if (rider.getRiderId() == riderId){
                    riderForGivenId = rider;
                    teamRiderBelongsTo = team;
                }
            }
        }

        //handing exception
        if (riderForGivenId == null){
            throw new IDNotRecognisedException();
        }

        //removing rider
        teamRiderBelongsTo.removeRider(riderForGivenId);
        riderForGivenId = null;

    }

    //</editor-fold>

    //<editor-fold desc="_____________________________Handle Stage Results____________________________#To Finish">
    @Override
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints) throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException, InvalidStageStateException {
        ///finding relevant rider for the given id
        Rider riderForGivenId = null;
        for (Team team : teamList) {
            for (Rider rider : team.getListOfRiders()){
                if (rider.getRiderId() == riderId){
                    riderForGivenId = rider;
                }
            }
        }

        //finding relevant stage for the given id
        Stage stageForGivenId = null;
        for (Race race : raceList) {
            for (Stage stage : race.getListOfStages()){
                if (stage.getStageId() == stageId){
                    stageForGivenId = stage;
                }
            }
        }

        //handling exceptions
        if (riderForGivenId == null || stageForGivenId == null) { throw new IDNotRecognisedException(); }
        if (riderForGivenId.hasResult(stageForGivenId)) { throw new DuplicatedResultException(); }
        if ((stageForGivenId.getSegmentIds().length + 2) != checkpoints.length) {throw new InvalidCheckpointsException(); }
        if (stageForGivenId.getStageState() != StageState.WAITING_FOR_RESULTS) { throw new InvalidStageStateException(); }

        riderForGivenId.addResultToHashMap(stageId, checkpoints);
    }

    @Override
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        ///finding relevant rider for the given id
        Rider riderForGivenId = null;
        for (Team team : teamList) {
            for (Rider rider : team.getListOfRiders()){
                if (rider.getRiderId() == riderId){
                    riderForGivenId = rider;
                }
            }
        }

        //finding relevant stage for the given id
        Stage stageForGivenId = null;
        for (Race race : raceList) {
            for (Stage stage : race.getListOfStages()){
                if (stage.getStageId() == stageId){
                    stageForGivenId = stage;
                }
            }
        }

        //handling exceptions
        if (riderForGivenId == null || stageForGivenId == null) { throw new IDNotRecognisedException(); }

        //calculating and appending elapsed time
        LocalTime[] checkpointTimes = riderForGivenId.getResultsForGivenStage(stageId);
        LocalTime[] timesToReturn = new LocalTime[riderForGivenId.getResultsForGivenStage(stageId).length + 1];
        for(int i = 0; i < checkpointTimes.length; i++) {
            timesToReturn[i] = checkpointTimes[i];
        }

        LocalTime start = checkpointTimes[0];
        LocalTime end = checkpointTimes[checkpointTimes.length-1];
        long numberOfSeconds = start.until(end, ChronoUnit.SECONDS);
        long secondsInAnHour = 3600L;
        long secondsInAMinute = 60L;
        long hours = numberOfSeconds / secondsInAnHour;
        long minutes = (numberOfSeconds % secondsInAnHour) / secondsInAMinute;
        long seconds = numberOfSeconds % secondsInAMinute;
        LocalTime elapsedTime = LocalTime.of((int)hours, (int)minutes, (int)seconds);
        timesToReturn[checkpointTimes.length] = elapsedTime;

        return timesToReturn;
    }//needs checking

    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        ///finding relevant rider for the given id
        Rider riderForGivenId = null;
        for (Team team : teamList) {
            for (Rider rider : team.getListOfRiders()){
                if (rider.getRiderId() == riderId){
                    riderForGivenId = rider;
                }
            }
        }

        //finding relevant stage for the given id
        Stage stageForGivenId = null;
        for (Race race : raceList) {
            for (Stage stage : race.getListOfStages()){
                if (stage.getStageId() == stageId){
                    stageForGivenId = stage;
                }
            }
        }

        //handling exceptions
        if (riderForGivenId == null || stageForGivenId == null) { throw new IDNotRecognisedException(); }

        riderForGivenId.removeResultsForStage(stageId);

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

    //</editor-fold>

    //<editor-fold desc="_________________________________Serialization_______________________________#TO DO">

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

    //</editor-fold>

}