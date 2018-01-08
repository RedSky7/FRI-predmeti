package si.lj.uni.fri.tpo.fripredmeti.REST;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import si.lj.uni.fri.tpo.fripredmeti.Model.Comment;

/**
 * Created by Boštjan on 8.1.2018.
 */

public class GetKomentarPredmet extends AsyncTask<Integer, Void, List<Comment>> {

    @Override
    protected List<Comment> doInBackground(Integer... params)
    {
        List<Comment> result = new ArrayList<Comment>();
        try
        {
            StringBuilder result1 = new StringBuilder("");
            URL url = new URL("http://friaplikacija.azurewebsites.net/Service.svc/GetKomentarPredmet");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            String sort;
            if(params[1] == 1)
                sort = "splosnaOcena";
            else if(params[1] == 2)
                sort = "OcenaKomentarja";
            else
                sort = "datum";

            //nastavi prave klice v header
            connection.setRequestProperty("predmetID", Integer.toString(params[0]));
            connection.setRequestProperty("sort", sort);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while ((line = rd.readLine()) != null) {result1.append(line);}

            JSONArray array = new JSONArray(result1.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                JSONObject jsonObjectComment = jsonObject.getJSONObject("komentar");
                JSONObject jsonObjectUser = jsonObject.getJSONObject("uporabnik");
                //(String username, int komentarID, int ocenaKomentar, int splosnaOcena, String komentar)
                result.add(new Comment(
                        jsonObjectUser.getString("username"),
                        jsonObjectComment.getInt("komentarID"),
                        jsonObjectComment.getInt("ocenaKomentar"),
                        jsonObject.getInt("splosnaOcena"),
                        jsonObjectComment.getString("komentar"),
                        jsonObjectComment.getString("datum")));
            }
        }
        catch (UnknownHostException e)
        {
            Log.d("ERROR", "No internet connection!");
            //Toast.makeText(TeacherOverview.this, "No internet connection!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;

        //onPreExecute se izvrši pre doInBackGround
        //onPostExecute se izvrši po doInBackground
    }
}
