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
    private int splosnaOcena;
    private int tezavnostOcena;
    private int uporabnostOcena;
    private int zanimivostOcena;

    public Course(String ime, String opis, int predmetID, int splosnaOcena, int tezavnostOcena, int uporabnostOcena, int zanimivostOcena) {
        this.ime = ime;
        this.opis = opis;
        this.predmetID = predmetID;
        this.splosnaOcena = splosnaOcena;
        this.tezavnostOcena = tezavnostOcena;
        this.uporabnostOcena = uporabnostOcena;
        this.zanimivostOcena = zanimivostOcena;
    }

    public Course(String ime, String izvajalci, String ocena, int predmetID, int splosnaOcena, int tezavnostOcena, int uporabnostOcena, int zanimivostOcena){
        this.ime = ime;
        this.izvajalci = izvajalci;
        this.ocena = ocena;
        this.predmetID = predmetID;
        this.splosnaOcena = splosnaOcena;
        this.tezavnostOcena = tezavnostOcena;
        this.uporabnostOcena = uporabnostOcena;
        this.zanimivostOcena = zanimivostOcena;
    }

    public int getSplosnaOcena() {
        return splosnaOcena;
    }

    public String getOpis() {
        return opis;
    }

    public int getPredmetID() {
        return predmetID;
    }

    public int getTezavnostOcena() {
        return tezavnostOcena;
    }

    public int getUporabnostOcena() {
        return uporabnostOcena;
    }

    public int getZanimivostOcena() {
        return zanimivostOcena;
    }

    public String getIme() {
        return ime;
    }
}
