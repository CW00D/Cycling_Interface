package cycling;

public class Rider{
    //<editor-fold desc="__________________________________Atributes__________________________________">
    //static instances counter
    private static int count = 0;
    //rider's id
    private Integer riderId;
    //rider's name
    private String riderName;
    //rider's year of birth
    private Integer riderYOB;

    //</editor-fold>

    //<editor-fold desc="_________________________________Constructor_________________________________">
    public Rider(String riderName, Integer riderYOB){
        this.riderName = riderName;
        this.riderYOB = riderYOB;
        this.riderId=count++;
    }

    //</editor-fold>

    //<editor-fold desc="___________________________________Methods___________________________________">
    //deleteRider - removes rider and all of their results from the system

    //</editor-fold>

}