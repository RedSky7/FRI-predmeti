package si.lj.uni.fri.tpo.fripredmeti.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Blaz on 30-Dec-17.
 */

public class Comment {
    private String username;
    private int komentarID;
    private int ocenaKomentar; //up-down voti
    private int splosnaOcena;  //ocena profesorja
    private String komentar;
    private String datum;

    public Comment(String username, int komentarID, int ocenaKomentar, int splosnaOcena, String komentar, String datum) {
        this.username = username;
        this.komentarID = komentarID;
        this.ocenaKomentar = ocenaKomentar;
        this.splosnaOcena = splosnaOcena;
        this.komentar = komentar;

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date d = dateFormat.parse(datum);

            DateFormat df = new SimpleDateFormat("d MMMM");
            this.datum = df.format(d);
        } catch (ParseException e) {
            this.datum = "0";
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public int getKomentarID() {
        return komentarID;
    }

    public int getOcenaKomentar() {
        return ocenaKomentar;
    }

    public int getSplosnaOcena() {
        return splosnaOcena;
    }

    public String getKomentar() { return komentar; }

    public String getDatum() { return datum; }
}
