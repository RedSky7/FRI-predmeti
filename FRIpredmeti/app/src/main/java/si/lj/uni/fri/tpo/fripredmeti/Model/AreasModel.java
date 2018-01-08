package si.lj.uni.fri.tpo.fripredmeti.Model;

/**
 * Created by Boštjan on 8.1.2018.
 */

public class AreasModel {

    private int podrocjeID;
    private String imePodrocja;

    public AreasModel(int podrocjeID, String imePodrocja){
        this.podrocjeID = podrocjeID;
        this.imePodrocja = imePodrocja;
    }

    public int getPodrocjeID() {
        return podrocjeID;
    }

    public String getImePodrocja() {
        return imePodrocja;
    }

}
