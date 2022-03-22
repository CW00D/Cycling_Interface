package cycling;

import java.io.*;
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

    //holds the id counters for the objects
    private ArrayList<Integer> portalIds = new ArrayList<Integer>();
    private int change = new int;


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
        assert raceList.size() != 0 : "Race not added";
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
                assert segment == null : "associated segment not removed ";
            }
            for (Team team : teamList){
                for (Rider rider : team.getListOfRiders()){
                    rider.removeResultsForStage(stage.getStageId());
                }
            }
            assert stage == null : "associated stage not removed ";
        }
        raceList.remove(raceForGivenId);
        for (Race race : raceList){
            assert race != raceForGivenId : "race not removed ";
        }
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
        } //assuming double cannot be null as this is a primitive type and would raise an error when passing into function

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
        return raceForGivenId.getStageIds();
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
        return stageForGivenId.getSegmentIds();
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

        riderForGivenId.addResultsToHashMap(stageId, checkpoints);
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
        timesToReturn[checkpointTimes.length] = riderForGivenId.getElapsedTimeForGivenStage(stageId);
        return timesToReturn;
    }//TO CHECK

    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
        //finding relevant stage and rider for the given ids
        Stage stageForGivenId = null;
        for (Race race : raceList) {
            for (Stage stage : race.getListOfStages()){
                if (stage.getStageId() == stageId){
                    stageForGivenId = stage;
                }
            }
        }

        Rider riderForGivenId = null;
        for (Team team : teamList) {
            for (Rider rider : team.getListOfRiders()){
                if (rider.getRiderId() == riderId){
                    riderForGivenId = rider;
                }
            }
        }

        //handling exception
        if (stageForGivenId == null || riderForGivenId == null){
            throw new IDNotRecognisedException();
        }

        //returning the adjusted elapsed time
        ArrayList<Rider> ridersByElapsedTime = new ArrayList<Rider>();
        for (Team team : teamList){
            for (Rider rider : team.getListOfRiders()){
                if (rider.hasResult(stageForGivenId) == true){
                    if (ridersByElapsedTime.size() == 0){
                        ridersByElapsedTime.add(rider);
                    } else if (rider.getElapsedTimeForGivenStage(stageId).compareTo(ridersByElapsedTime.get(ridersByElapsedTime.size()-1).getElapsedTimeForGivenStage(stageId))>=0) {
                        ridersByElapsedTime.add(ridersByElapsedTime.size(), rider);
                    }else{
                        for (int i=0;i<ridersByElapsedTime.size();i++) {
                            if (rider.getElapsedTimeForGivenStage(stageId).compareTo(ridersByElapsedTime.get(i).getElapsedTimeForGivenStage(stageId))==-1){
                                ridersByElapsedTime.add(i, rider);
                                break;
                            }
                        }
                    }
                }

            }
        }
        for (int i=0;i<ridersByElapsedTime.size()-1;i++){
            if (ridersByElapsedTime.get(i).getElapsedTimeForGivenStage(stageId).until(ridersByElapsedTime.get(i+1).getElapsedTimeForGivenStage(stageId), ChronoUnit.MILLIS)<1000L){
                ridersByElapsedTime.get(i+1).setAdjustedElapsedTime(stageId, ridersByElapsedTime.get(i).getAdjustedElapsedTime(stageId));
            }
        }

        return riderForGivenId.getAdjustedElapsedTime(stageId);
    }//TO CHECK

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

        //finding the order of riders in the given stage
        ArrayList<Rider> riderOrderForStage = new ArrayList<>();
        for (Team team : teamList){
            for (Rider rider : team.getListOfRiders()){
                if (rider.hasResult(stageForGivenId)){
                    if (riderOrderForStage.size() == 0){
                        riderOrderForStage.add(rider);
                    } else if (rider.getElapsedTimeForGivenStage(stageId).compareTo(riderOrderForStage.get(riderOrderForStage.size()-1).getElapsedTimeForGivenStage(stageId))>=0) {
                        riderOrderForStage.add(riderOrderForStage.size(), rider);
                    }else{
                        for (int i=0; i<riderOrderForStage.size();i++){
                            if (rider.getElapsedTimeForGivenStage(stageId).compareTo(riderOrderForStage.get(i).getElapsedTimeForGivenStage(stageId))<0){
                                riderOrderForStage.add(i, rider);
                            }
                        }
                    }
                }
            }
        }

        int[] riderRanks = new int[riderOrderForStage.size()];
        for (int i=0;i<riderOrderForStage.size();i++){
            riderRanks[i] = riderOrderForStage.get(i).getRiderId();
        }
        return riderRanks;
    }

    @Override
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
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

        //creating the ranked array
        ArrayList<Rider> riderOrderForStage = new ArrayList<>();
        for (Team team : teamList){
            for (Rider rider : team.getListOfRiders()){
                if (rider.hasResult(stageForGivenId)){
                    if (riderOrderForStage.size() == 0){
                        riderOrderForStage.add(rider);
                    } else if (rider.getFinishTimeForStage(stageId).compareTo(riderOrderForStage.get(riderOrderForStage.size()-1).getFinishTimeForStage(stageId))>=0) {
                        riderOrderForStage.add(riderOrderForStage.size(), rider);
                    } else {
                        for (int i=0; i<riderOrderForStage.size();i++){
                            if (rider.getFinishTimeForStage(stageId).compareTo(riderOrderForStage.get(i).getFinishTimeForStage(stageId))<0){
                                riderOrderForStage.add(i, rider);
                                break;
                            }
                        }
                    }
                }
            }
        }

        LocalTime[] rankedElapsedTimes = new LocalTime[riderOrderForStage.size()];
        for (int i=0;i<riderOrderForStage.size();i++){
            rankedElapsedTimes[i] = riderOrderForStage.get(i).getElapsedTimeForGivenStage(stageId);
        }
        return rankedElapsedTimes;
    }//TO CHECK

    @Override
    public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
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

        //calculating points to be awarded for stage
        //generates a list of riders ordered by their elapsed times for the given stage
        ArrayList<Rider> riderOrderForStage = new ArrayList<>();
        for (Team team : teamList){
            for (Rider rider : team.getListOfRiders()){
                if (rider.hasResult(stageForGivenId)){
                    if (riderOrderForStage.size() == 0){
                        riderOrderForStage.add(rider);
                    } else if (rider.getElapsedTimeForGivenStage(stageId).compareTo(riderOrderForStage.get(riderOrderForStage.size()-1).getElapsedTimeForGivenStage(stageId))>=0) {
                        riderOrderForStage.add(riderOrderForStage.size(), rider);
                    }else{
                        for (int i=0; i<riderOrderForStage.size();i++){
                            if (rider.getElapsedTimeForGivenStage(stageId).compareTo(riderOrderForStage.get(i).getElapsedTimeForGivenStage(stageId))<0){
                                riderOrderForStage.add(i, rider);
                                break;
                            }
                        }
                    }
                }
            }
        }

        int[] flatPoints = {50,30,20,18,16,14,12,10,8,7,6,5,4,3,2};
        int[] mediumPoints = {30,25,22,19,17,15,13,11,9,7,6,5,4,3,2};
        int[] otherPoints = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1};

        //getting finishing line points
        int[] riderFinishPoints = new int[riderOrderForStage.size()];
        for (int i=0;i<riderOrderForStage.size();i++){
            if (i>=10){
                riderFinishPoints[i] = 0;
            }else if (stageForGivenId.getStageType() == StageType.FLAT){
                riderFinishPoints[i] = flatPoints[i];
            }else if (stageForGivenId.getStageType() == StageType.MEDIUM_MOUNTAIN){
                riderFinishPoints[i] = mediumPoints[i];
            }else if (stageForGivenId.getStageType() == StageType.HIGH_MOUNTAIN){
                riderFinishPoints[i] = otherPoints[i];
            }else if (stageForGivenId.getStageType() == StageType.TT){
                riderFinishPoints[i] = otherPoints[i];
            }

        }

        //getting sprint points
        int[] riderSprintPoints = new int[riderOrderForStage.size()];
        for (Segment segment : stageForGivenId.getStageSegments()){
            if (segment.getSegmentType() == SegmentType.SPRINT){
                for (int i=0;i<riderOrderForStage.size();i++){
                    int quickerRidersForSprint = 0;
                    for (Rider otherRider : riderOrderForStage){
                        if (otherRider!=riderOrderForStage.get(i)){
                            if (otherRider.getTimeForSegmentOfStage(stageForGivenId, segment).compareTo(riderOrderForStage.get(i).getTimeForSegmentOfStage(stageForGivenId, segment))==-1) {
                                quickerRidersForSprint += 1;
                            }
                        }
                    }
                    if (quickerRidersForSprint<=14) {
                        riderSprintPoints[i] += otherPoints[quickerRidersForSprint];
                    }
                }
            }
        }

        //add points for finish and sprint and return
        int[] riderPoints = new int[riderOrderForStage.size()];
        for (int i=0;i<riderPoints.length;i++){
            riderPoints[i] = riderFinishPoints[i] + riderSprintPoints[i];
        }

        return riderPoints;
    }//TO CHECK

    @Override
    public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
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

        //calculating points to be awarded for stage
        //creating the ranked array of riders
        ArrayList<Rider> riderOrderForStage = new ArrayList<>();
        for (Team team : teamList){
            for (Rider rider : team.getListOfRiders()){
                if (rider.hasResult(stageForGivenId)){
                    if (riderOrderForStage.size() == 0){
                        riderOrderForStage.add(rider);
                    } else if (rider.getFinishTimeForStage(stageId).compareTo(riderOrderForStage.get(riderOrderForStage.size()-1).getFinishTimeForStage(stageId))>=0) {
                        riderOrderForStage.add(riderOrderForStage.size(), rider);
                    } else {
                        for (int i=0; i<riderOrderForStage.size();i++){
                            if (rider.getFinishTimeForStage(stageId).compareTo(riderOrderForStage.get(i).getFinishTimeForStage(stageId))<0){
                                riderOrderForStage.add(i, rider);
                                break;
                            }
                        }
                    }
                }
            }
        }

        int[] climbFourPoints = {1};
        int[] climbThreePoints = {2, 1};
        int[] climbTwoPoints = {5, 3, 2, 1};
        int[] climbOnePoints = {10, 8, 6, 4, 2, 1};
        int[] climbHPoints = {20, 15, 12, 10, 8, 6, 4, 2};


        //calculating the mountain points for stage
        int[] riderMountainPoints = new int[riderOrderForStage.size()];
        for (Segment segment : stageForGivenId.getStageSegments()){
            if (segment.getSegmentType() != SegmentType.SPRINT){
                for (int i=0;i<riderOrderForStage.size();i++){
                    int quickerRidersForMountain = 0;
                    for (Rider otherRider : riderOrderForStage){
                        if (otherRider!=riderOrderForStage.get(i)){
                            if (otherRider.getTimeForSegmentOfStage(stageForGivenId, segment).compareTo(riderOrderForStage.get(i).getTimeForSegmentOfStage(stageForGivenId, segment))==-1) {
                                quickerRidersForMountain += 1;
                            }
                        }
                    }
                    if (segment.getSegmentType() == SegmentType.C4){
                        if (quickerRidersForMountain<=0) {
                            riderMountainPoints[i] += climbFourPoints[quickerRidersForMountain];
                        }
                    }else if (segment.getSegmentType() == SegmentType.C3){
                        if (quickerRidersForMountain<=1) {
                            riderMountainPoints[i] += climbThreePoints[quickerRidersForMountain];
                        }
                    }else if (segment.getSegmentType() == SegmentType.C2){
                        if (quickerRidersForMountain<=3) {
                            riderMountainPoints[i] += climbTwoPoints[quickerRidersForMountain];
                        }
                    }else if (segment.getSegmentType() == SegmentType.C1){
                        if (quickerRidersForMountain<=5) {
                            riderMountainPoints[i] += climbOnePoints[quickerRidersForMountain];
                        }
                    }else if (segment.getSegmentType() == SegmentType.HC){
                        if (quickerRidersForMountain<=7) {
                            riderMountainPoints[i] += climbHPoints[quickerRidersForMountain];
                        }
                    }
                }
            }
        }

        return riderMountainPoints;
    }//TO CHECK

    @Override
    public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
        //finding relevant race for the given id
        Race raceForGivenId = null;
        for (Race race : raceList) {
            if (race.getRaceId() == raceId){
                raceForGivenId = race;
            }
        }

        //handling exception
        if (raceForGivenId == null){
            throw new IDNotRecognisedException();
        }

        //calculating the cumulative adjusted times for races
        for (Stage stage : raceForGivenId.getListOfStages()){
            //calculate elapsed time for the stage and add to the results
        }

        //sort the results in order of smallest to largest and return

        return null;
    }//TO DO

    @Override
    public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
        //finding relevant race for the given id
        Race raceForGivenId = null;
        for (Race race : raceList) {
            if (race.getRaceId() == raceId){
                raceForGivenId = race;
            }
        }

        //handling exception
        if (raceForGivenId == null){
            throw new IDNotRecognisedException();
        }

        //calculating total points for race
        //generates a list of riders ordered by their total elapsed times for the race
        ArrayList<Rider> riderOrderForRace = new ArrayList<>();
        for (Team team : teamList) {
            for (Rider rider : team.getListOfRiders()) {
                LocalTime totalElapsedTime = LocalTime.of(0, 0, 0, 0);
                for (Stage stage : raceForGivenId.getListOfStages()){
                    if (rider.hasResult(stage)) {
                        LocalTime elapsedTime = rider.calculateElapsedTimeForGivenStage(stage.getStageId());
                        totalElapsedTime.plusHours(elapsedTime.getHour()).plusMinutes(elapsedTime.getMinute()).plusSeconds(elapsedTime.getSecond()).plusNanos(elapsedTime.getNano());
                        rider.addTotalElapsedTimeToRace(raceId, totalElapsedTime);
                    } else {
                        int[] emptyList = new int[0];
                        return emptyList;
                    }
                }
                if (riderOrderForRace.size() == 0){
                    riderOrderForRace.add(rider);
                } else if (rider.getTotalElapsedTimeForGivenRace(raceId).compareTo(riderOrderForRace.get(riderOrderForRace.size()-1).getTotalElapsedTimeForGivenRace(raceId))>=0) {
                    riderOrderForRace.add(riderOrderForRace.size(), rider);
                }else{
                    for (int i=0;i<riderOrderForRace.size();i++) {
                        if (rider.getTotalElapsedTimeForGivenRace(raceId).compareTo(riderOrderForRace.get(i).getTotalElapsedTimeForGivenRace(raceId))==-1){
                            riderOrderForRace.add(i, rider);
                            break;
                        }
                    }
                }
            }
        }

        for (Stage stage : raceForGivenId.getListOfStages()){

        }
        return null;
    }//TO DO

    @Override
    public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
        //finding relevant race for the given id
        Race raceForGivenId = null;
        for (Race race : raceList) {
            if (race.getRaceId() == raceId){
                raceForGivenId = race;
            }
        }

        //handling exception
        if (raceForGivenId == null){
            throw new IDNotRecognisedException();
        }


        return null;
    }//TO DO

    @Override
    public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
        //finding relevant race for the given id
        Race raceForGivenId = null;
        for (Race race : raceList) {
            if (race.getRaceId() == raceId){
                raceForGivenId = race;
            }
        }

        //handling exception
        if (raceForGivenId == null){
            throw new IDNotRecognisedException();
        }

        //finding the order of the riders in the race
        //needs to be ordered by total ADJUSTED elapsed time
        ArrayList<Rider> riderOrderForRace = new ArrayList<>();
        for (Team team : teamList) {
            for (Rider rider : team.getListOfRiders()) {
                LocalTime totalElapsedTime = LocalTime.of(0, 0, 0, 0);
                for (Stage stage : raceForGivenId.getListOfStages()){
                    if (rider.hasResult(stage)) {
                        LocalTime elapsedTime = rider.calculateElapsedTimeForGivenStage(stage.getStageId());
                        totalElapsedTime.plusHours(elapsedTime.getHour()).plusMinutes(elapsedTime.getMinute()).plusSeconds(elapsedTime.getSecond()).plusNanos(elapsedTime.getNano());
                        rider.addTotalElapsedTimeToRace(raceId, totalElapsedTime);
                    } else {
                        int[] emptyList = new int[0];
                        return emptyList;
                    }
                }
                if (riderOrderForRace.size() == 0){
                    riderOrderForRace.add(rider);
                } else if (rider.getTotalElapsedTimeForGivenRace(raceId).compareTo(riderOrderForRace.get(riderOrderForRace.size()-1).getTotalElapsedTimeForGivenRace(raceId))>=0) {
                    riderOrderForRace.add(riderOrderForRace.size(), rider);
                }else{
                    for (int i=0;i<riderOrderForRace.size();i++) {
                        if (rider.getTotalElapsedTimeForGivenRace(raceId).compareTo(riderOrderForRace.get(i).getTotalElapsedTimeForGivenRace(raceId))==-1){
                            riderOrderForRace.add(i, rider);
                            break;
                        }
                    }
                }
            }
        }

        int[] returnArray = new int[riderOrderForRace.size()];
        for (int i=0;i<riderOrderForRace.size();i++){
            returnArray[i] = riderOrderForRace.get(i).getRiderId();
        }
        return returnArray;
    }//TO DO

    @Override
    public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
        //finding relevant race for the given id
        Race raceForGivenId = null;
        for (Race race : raceList) {
            if (race.getRaceId() == raceId){
                raceForGivenId = race;
            }
        }

        //handling exception
        if (raceForGivenId == null){
            throw new IDNotRecognisedException();
        }


        return null;
    }//TO DO

    @Override
    public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
        //finding relevant race for the given id
        Race raceForGivenId = null;
        for (Race race : raceList) {
            if (race.getRaceId() == raceId){
                raceForGivenId = race;
            }
        }

        //handling exception
        if (raceForGivenId == null){
            throw new IDNotRecognisedException();
        }


        return null;
    }//TO DO

    //</editor-fold>

    //<editor-fold desc="_________________________________Serialization_______________________________">

    @Override
    public void eraseCyclingPortal() {
        teamList.clear();
        assert teamList.size()==0 : "teamList not clear";
        raceList.clear();
        assert raceList.size()==0 : "raceList not clear";

        //reset the current ids
        Team.setCount(0);
        Rider.setCount(0);
        Race.setCount(0);
        Stage.setCount(0);
        Segment.setCount(0);

        if (portalIds.size()!= 0){
            portalIds.set(0,0);
            portalIds.set(1,0);
            portalIds.set(2,0);
            portalIds.set(3,0);
            portalIds.set(4,0);}

        for (int i=0; i<portalIds.size(); i++){assert portalIds.get(i) == 0 : "Team count not reset";}

        assert Team.getCount() == 0 : "Team count not reset";
        assert Rider.getCount() == 0 : "Rider count not reset";
        assert Race.getCount() == 0 : "Race count not reset";
        assert Stage.getCount() == 0 : "Stage count not reset";
        assert Segment.getCount() == 0 : "Segment count not reset";

    }

    @Override
    public void saveCyclingPortal(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            portalIds.add(0, Team.getCount());
            portalIds.add(1, Rider.getCount());
            portalIds.add(2, Race.getCount());
            portalIds.add(3, Stage.getCount());
            portalIds.add(4, Segment.getCount());

            out.writeObject(raceList);
            out.writeObject(teamList);
            out.writeObject(portalIds);
            out.close();
            System.out.println("Saved in: " + filename);
        } catch (IOException exception) {
            throw exception;
        }
    }

    @Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new
                FileInputStream(filename))) {
            raceList = (ArrayList<Race>)in.readObject();
            System.out.println(raceList);
            teamList = (ArrayList<Team>)in.readObject();
            System.out.println(teamList);
            portalIds = (ArrayList<Integer>)in.readObject();
            System.out.println(portalIds);
            in.close();

            //reset the current ids
            Team.setCount(portalIds.get(0));
            Rider.setCount(portalIds.get(1));
            Race.setCount(portalIds.get(2));
            Stage.setCount(portalIds.get(3));
            Segment.setCount(portalIds.get(4));
        } catch (IOException exception) {
            throw  exception;
        } catch (ClassNotFoundException exception) {
            throw exception;
        }
    }

    //</editor-fold>

}