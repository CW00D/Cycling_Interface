package cycling;

import java.util.ArrayList;

public class Race{
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
    public  Race(String raceName, String raceDescription){
        this.raceName = raceName;
        this.raceDescription = raceDescription;
        this.raceId=count++;
    }

    //</editor-fold>

    //<editor-fold desc="_____________________________Getters And Setters_____________________________">
    public Integer getRaceId() {
        return raceId;
    }
    public String getRaceName() { return raceName; }
    public String getRaceDescription() { return raceDescription; }

    //</editor-fold>

    //<editor-fold desc="___________________________________Methods___________________________________">
    public Integer getNumberOfStages() {
        return listOfStages.size();
    }

    public double getTotalLength() {
        double totalLength = 0;
        for (int i=0; i<listOfStages.size(); i++) {
            totalLength += listOfStages.get(i).getStageLength();
        }
        return  totalLength;
    }

    public void addStage(Stage newStage) {
        listOfStages.add(newStage);
    }

    public int[] getStageIds() {
        int[] idList =  new int[listOfStages.size()];
        for (int i=0; i<listOfStages.size(); i++){
            idList[i] = (listOfStages.get(i).getStageId());
        }
        return idList;
    }
    //</editor-fold>
}