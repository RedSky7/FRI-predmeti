package si.lj.uni.fri.tpo.fripredmeti.Model;

/**
 * Created by Blaz on 30-Dec-17.
 */

public class Comment {
    private String username;
    private int komentarID;
    private int ocenaKomentar; //up-down voti
    private int splosnaOcena;  //ocena profesorja
    private String komentar;

    public Comment(String username, int komentarID, int ocenaKomentar, int splosnaOcena, String komentar) {
        this.username = username;
        this.komentarID = komentarID;
        this.ocenaKomentar = ocenaKomentar;
        this.splosnaOcena = splosnaOcena;
        this.komentar = komentar;
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
}
