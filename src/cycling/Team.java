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

    //<editor-fold desc="___________________________________Methods___________________________________">
    //deleteTeam
    //getRiders
    //addRider

    //</editor-fold>
}