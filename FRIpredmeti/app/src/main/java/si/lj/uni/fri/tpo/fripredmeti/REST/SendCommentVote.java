package si.lj.uni.fri.tpo.fripredmeti.REST;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Blaz on 02-Jan-18.
 */

public class SendCommentVote extends AsyncTask<String, Void, String>{

    @Override
    protected String doInBackground(String... params) {
        String result = "fail";

        try{
            StringBuilder resultT = new StringBuilder("");
            String SERVICE_URL = "http://friaplikacija.azurewebsites.net/Service.svc/AddOcenaKomentar";
            URL url = new URL(SERVICE_URL);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            //email|komentarID|positive
            connection.setRequestProperty("email", params[0]);
            connection.setRequestProperty("komentarID", params[1]);
            connection.setRequestProperty("positive", params[2]);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while ((line = rd.readLine()) != null) {StringBuilder append = resultT.append(line);}

            JSONObject jsonObject = new JSONObject(resultT.toString());
            result = jsonObject.getString("ocenaKomentar");

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