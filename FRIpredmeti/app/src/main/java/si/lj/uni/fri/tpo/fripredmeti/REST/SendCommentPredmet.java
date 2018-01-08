package si.lj.uni.fri.tpo.fripredmeti.REST;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Boštjan on 8.1.2018.
 */

public class SendCommentPredmet  extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String result = "Failed";

        try{
            StringBuffer resultT = new StringBuffer("");
            String SERVICE_URL = "http://friaplikacija.azurewebsites.net/Service.svc/KomentirajPredmet";
            URL url = new URL(SERVICE_URL);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            //KomentirajIzvajalca|izvajalecID|komentar|splosnaOcena|email
            connection.setRequestProperty("komentar", params[0]);
            connection.setRequestProperty("predmetID", params[1]);
            connection.setRequestProperty("splosnaOcena", params[2]);
            connection.setRequestProperty("email", params[3]);
            connection.setRequestProperty("tezavnostOcena", params[4]);
            connection.setRequestProperty("zanimivostOcena", params[5]);
            connection.setRequestProperty("uporabnostOcena","1");

            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while ((line = rd.readLine()) != null) {
                resultT.append(line);
            }

            JSONObject jsonObject = new JSONObject(resultT.toString());
            result = jsonObject.getString("date");

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    @Override
    protected void onPostExecute(String s) {

    }
}