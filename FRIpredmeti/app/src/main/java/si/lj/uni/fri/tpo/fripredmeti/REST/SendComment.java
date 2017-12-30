package si.lj.uni.fri.tpo.fripredmeti.REST;


import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import si.lj.uni.fri.tpo.fripredmeti.Model.Teacher;
import si.lj.uni.fri.tpo.fripredmeti.TeacherOverview;

/**
 * Created by Blaz on 27-Dec-17.
 */

public class SendComment extends AsyncTask<String, Void, String> {

    private final String SERVICE_URL = "http://friaplikacija.azurewebsites.net/Service.svc/KomentirajIzvaj";

    @Override
    protected String doInBackground(String... params) {
        String urlString = SERVICE_URL;

        String result = "Faild";

        try{
            StringBuffer resultT = new StringBuffer("");
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            //KomentirajIzvajalca|izvajalecID|komentar|splosnaOcena|email
            connection.setRequestProperty("komentar", params[0]);
            connection.setRequestProperty("izvajalecID", params[1]);
            connection.setRequestProperty("splosnaOcena", params[2]);
            connection.setRequestProperty("email", params[3]);
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
