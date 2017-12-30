package si.lj.uni.fri.tpo.fripredmeti.Model;

/**
 * Created by Blaz on 29-Dec-17.
 */

public class User {
    private String username;
    private String geslo;
    private String email;
    private String verificationCode;


    public User(String username, String geslo, String email, String verificationCode) {
        this.username = username;
        this.geslo = geslo;
        this.email = email;
        this.verificationCode = verificationCode;
    }

    public String getUsername() { return username; }

    public String getEmail() { return email; }
}
