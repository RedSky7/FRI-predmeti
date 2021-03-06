package si.lj.uni.fri.tpo.fripredmeti.Model;

/**
 * Created by Blaz on 27-Dec-17.
 */

public class Teacher {
    private int teacherID;
    private String ime;
    private String naziv;
    private String opis;
    private String priimek;
    private String slika;
    private String email;
    private int numberOfComments;
    private double splosnaOcena;

    public Teacher(int teacherID, String ime, String priimek, double splosnaOcena){
        this.teacherID = teacherID;
        this.ime = ime;
        this.priimek = priimek;
        this.splosnaOcena = splosnaOcena;

    }

    public Teacher(int teacherID, String ime, String naziv, String opis, String priimek, String slika, double splosnaOcena, String email, int numberOfComments) {
        this.teacherID = teacherID;
        this.ime = ime;
        this.naziv = naziv;
        this.opis = opis;
        this.priimek = priimek;
        this.slika = slika;
        this.splosnaOcena = (double) Math.round(splosnaOcena * 10) / 10;
        this.email = email;
        this.numberOfComments = numberOfComments;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public String getOpis() {
        return opis;
    }

    public double getSplosnaOcena() {
        return splosnaOcena;
    }

    public String toString(){
        return ime + " " + priimek;
    }

    public String getIme(){
        return ime;
    };

    public String getPriimek(){
        return priimek;
    };

    public String getNaziv(){
        return naziv;
    };

    public Double splosnaOcena(){
        return splosnaOcena;
    }

    public String getSlika() { return slika; }

    public String getEmail() { return email; }

    public int getNumberOfComments() { return  numberOfComments; }
}
