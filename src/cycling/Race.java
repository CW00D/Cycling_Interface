package cycling;

import java.io.Serializable;
import java.util.ArrayList;

public class Race implements Serializable {
    //<editor-fold desc="__________________________________Atributes__________________________________">
    //static instances counter
    private static int count = 0;
    //race's id
    private Integer raceId;
    //race's name
    private String raceName;
    //race's description
    private String raceDescription;
    //list of race's stages
    private ArrayList<Stage> listOfStages = new ArrayList<Stage>();

    //</editor-fold>

    //<editor-fold desc="_________________________________Constructor_________________________________">
    public Race(String raceName, String raceDescription) {
        this.raceName = raceName;
        this.raceDescription = raceDescription;
        this.raceId = count++;
    }

    //</editor-fold>

    //<editor-fold desc="_____________________________Getters And Setters_____________________________">
    public Integer getRaceId() {
        return raceId;
    }

    public String getRaceName() {
        return raceName;
    }

    public String getRaceDescription() {
        return raceDescription;
    }

    public ArrayList<Stage> getListOfStages() {
        return listOfStages;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Race.count = count;
    }

    //</editor-fold>

    //<editor-fold desc="___________________________________Methods___________________________________">
    /**
     * This method returns the number of stages withing the race
     */
    public Integer getNumberOfStages () {
        return listOfStages.size();
    }

    /**
     * This method returns the total length of the race
     */
    public double getTotalLength () {
        double totalLength = 0;
        for (int i = 0; i < listOfStages.size(); i++) {
            totalLength += listOfStages.get(i).getStageLength();
        }
        return totalLength;
    }

    /**
     * This method adds a new stage to the race
     *
     * @param newStage the Stage instance to add to the race.
     */
    public void addStage (Stage newStage){
        if (listOfStages.size() == 0){
            listOfStages.add(newStage);
        } else if (newStage.getStageStartTime().compareTo(listOfStages.get(listOfStages.size()-1).getStageStartTime())>=0) {
            listOfStages.add(listOfStages.size(), newStage);
        } else {
            int currentSize = listOfStages.size();
            for (int i = 0; i < currentSize; i++) {
                if (newStage.getStageStartTime().compareTo(listOfStages.get(i).getStageStartTime()) < 0) {
                    listOfStages.add(i, newStage);
                    break;                }
            }
        }
    }

    /**
     * This returns an array of stageIds within the race
     */
    public int[] getStageIds () {
        int[] idList = new int[listOfStages.size()];
        for (int i = 0; i < listOfStages.size(); i++) {
            idList[i] = (listOfStages.get(i).getStageId());
        }
        return idList;
    }

    /**
     * This method removes a sage from the race
     *
     * @param stageToRemove the Stage instance to remove from the race.
     */
    public void removeStage(Stage stageToRemove){
        listOfStages.remove(stageToRemove);
    }

    //</editor-fold>
}