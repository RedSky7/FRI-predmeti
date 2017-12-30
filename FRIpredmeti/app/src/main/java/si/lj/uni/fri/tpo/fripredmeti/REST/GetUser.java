package si.lj.uni.fri.tpo.fripredmeti.REST;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import si.lj.uni.fri.tpo.fripredmeti.Model.User;

/**
 * Created by Blaz on 29-Dec-17.
 */

public class GetUser extends AsyncTask<String, Void, User> {

    @Override
    protected User doInBackground(String... params)
    {
        User result = null;
        try
        {
            StringBuffer result1 = new StringBuffer("");
            URL url = new URL("http://friaplikacija.azurewebsites.net/Service.svc/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //nastavi prave klice v header
            connection.setRequestProperty("email", params[0]);
            connection.setRequestProperty("geslo", params[1]);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while ((line = rd.readLine()) != null) {result1.append(line);}

            JSONObject jsonObj = new JSONObject(result1.toString());
            result = new User(jsonObj.getString("username"),
                    jsonObj.getString("geslo"),
                    jsonObj.getString("email"),
                    jsonObj.getString("verificationCode"));

        }
        catch (UnknownHostException e)
        {
            Log.d("ERROR", "No internet connection!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
