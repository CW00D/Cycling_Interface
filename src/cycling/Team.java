package cycling;

import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable {
    //<editor-fold desc="__________________________________Atributes__________________________________">
    //static instances counter
    private static int count = 0;
    //team's id
    private Integer teamId;
    //team's name
    private String teamName;
    //team's description
    private String teamDescription;
    //team's riders - list of Rider objects
    private ArrayList<Rider> listOfRiders = new ArrayList<Rider>();

    //</editor-fold>

    //<editor-fold desc="_________________________________Constructor_________________________________">
    public Team(String teamName, String teamDescription){
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.teamId=count++;
    }

    //</editor-fold>

    //<editor-fold desc="_____________________________Getters And Setters_____________________________">
    public Integer getTeamId () {
        return teamId;
    }

    public String getTeamName () {
        return teamName;
    }

    public ArrayList<Rider> getListOfRiders() {
        return listOfRiders;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Team.count = count;
    }

    //</editor-fold>

    //<editor-fold desc="___________________________________Methods___________________________________">
    /**
     * This method returns the ids of all the riders in the listOfRiders
     */
    public int[] getRiderIds () {
        int[] idList = new int[listOfRiders.size()];
        for (int i = 0; i < listOfRiders.size(); i++) {
            idList[i] = (listOfRiders.get(i).getRiderId());
        }
        return idList;
    }

    /**
     * This method adds a rider to the listOfRiders
     *
     * @param newRider an instance of Rider to add to the team
     */
    public void addRider(Rider newRider) {
        listOfRiders.add(newRider);
    }

    /**
     * This method removes a rider from the listOfRiders
     *
     * @param riderToRemove an instance of Rider to remove from the team
     */
    public void removeRider(Rider riderToRemove){
        listOfRiders.remove(riderToRemove);
    }

    //</editor-fold>
}