package si.lj.uni.fri.tpo.fripredmeti.Model;

import java.util.List;

/**
 * Created by Blaz on 30-Dec-17.
 */

public class Course {
    private String ime;
    private String opis;
    private String izvajalci;
    private String ocena;
    private int predmetID;
    private double splosnaOcena;
    private double tezavnostOcena;
    private double uporabnostOcena;
    private double zanimivostOcena;
    private List<String> oznake;

    public Course(String ime, String opis, int predmetID, double splosnaOcena, double tezavnostOcena, double uporabnostOcena, double zanimivostOcena) {
        this.ime = ime;
        this.opis = opis;
        this.predmetID = predmetID;
        this.splosnaOcena = splosnaOcena;
        this.tezavnostOcena = tezavnostOcena;
        this.uporabnostOcena = uporabnostOcena;
        this.zanimivostOcena = zanimivostOcena;
    }

    public Course(String ime, String izvajalci, String ocena, int predmetID, double splosnaOcena, double tezavnostOcena, double uporabnostOcena, double zanimivostOcena){
        this.ime = ime;
        this.izvajalci = izvajalci;
        this.ocena = ocena;
        this.predmetID = predmetID;
        this.splosnaOcena = splosnaOcena;
        this.tezavnostOcena = tezavnostOcena;
        this.uporabnostOcena = uporabnostOcena;
        this.zanimivostOcena = zanimivostOcena;
    }

    public Course(String ime, String izvajalci, String ocena, int predmetID, double splosnaOcena, double tezavnostOcena, double uporabnostOcena, double zanimivostOcena, List<String> oznake){
        this.ime = ime;
        this.izvajalci = izvajalci;
        this.ocena = ocena;
        this.predmetID = predmetID;
        this.splosnaOcena = splosnaOcena;
        this.tezavnostOcena = tezavnostOcena;
        this.uporabnostOcena = uporabnostOcena;
        this.zanimivostOcena = zanimivostOcena;
        this.oznake = oznake;
    }

    public double getSplosnaOcena() {
        return splosnaOcena;
    }

    public String getOpis() {
        return opis;
    }

    public int getPredmetID() {
        return predmetID;
    }

    public double getTezavnostOcena() {
        return tezavnostOcena;
    }

    public double getUporabnostOcena() {
        return uporabnostOcena;
    }

    public double getZanimivostOcena() {
        return zanimivostOcena;
    }

    public String getIzvajalci() {
        return izvajalci;
    }

    public String getOcena() {
        return ocena;
    }

    public List<String> getOznake() {
        return oznake;
    }

    public String getIme() {
        return ime;
    }
}
