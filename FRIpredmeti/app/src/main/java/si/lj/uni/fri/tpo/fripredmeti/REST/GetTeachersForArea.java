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

import si.lj.uni.fri.tpo.fripredmeti.Model.Course;
import si.lj.uni.fri.tpo.fripredmeti.Model.Teacher;

/**
 * Created by Boštjan on 8.1.2018.
 */


public class GetTeachersForArea extends AsyncTask<String, Void, List<Teacher> > {

    @Override
    protected List<Teacher> doInBackground(String... params)
    {
        List<Teacher> result = new ArrayList<Teacher>();
        try
        {
            StringBuilder result1 = new StringBuilder("");
            URL url = new URL("http://friaplikacija.azurewebsites.net/Service.svc/AllIzvajalciForPodrocje");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //nastavi prave klice v header
            connection.setRequestProperty("podrocjeID", params[0]);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while ((line = rd.readLine()) != null) {result1.append(line);}

            JSONArray array = new JSONArray(result1.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                result.add(new Teacher(
                        jsonObject.getInt("izvajalecID"),
                        jsonObject.getString("ime"),
                        jsonObject.getString("priimek"),
                        jsonObject.getDouble("splosnaOcena")));
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