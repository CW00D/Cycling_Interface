package cycling;

import java.util.ArrayList;

public class Team {
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

    //</editor-fold>

    //<editor-fold desc="___________________________________Methods___________________________________">
    //deleteTeam
    //getRiders
    public int[] getRiderIds () {
        int[] idList = new int[listOfRiders.size()];
        for (int i = 0; i < listOfRiders.size(); i++) {
            idList[i] = (listOfRiders.get(i).getRiderId());
        }
        return idList;
    }
    //addRider
    public void addRider(Rider newRider) {
        listOfRiders.add(newRider);
    }

    //</editor-fold>
}