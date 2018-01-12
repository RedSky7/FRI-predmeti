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

/**
 * Created by Boštjan on 11.1.2018.
 */

public class GetClassDetails extends AsyncTask<Integer, Void, Course> {

    @Override
    protected Course doInBackground(Integer... params)
    {
        Course result = null;
        try
        {
            StringBuilder result1 = new StringBuilder("");
            URL url = new URL("http://friaplikacija.azurewebsites.net/Service.svc/PredmetWithOznaka");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("predmetID", Integer.toString(params[0]));
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while ((line = rd.readLine()) != null) { final StringBuilder append = result1.append(line);}

            JSONObject jsonObj = new JSONObject(result1.toString());
            JSONArray oznakeArray = jsonObj.getJSONArray("oznaka");;

            JSONObject oznakeObj;
            List<String> oznake = new ArrayList<>();

            for (int j = 0; j < oznakeArray.length(); j++) {
                oznakeObj = oznakeArray.getJSONObject(j);
                oznake.add(oznakeObj.getString("Ime"));
            }

            result = new Course(jsonObj.getString("ime"),
                    jsonObj.getString("izvajalci"),
                    jsonObj.getString("ocena"),
                    jsonObj.getInt("predmetID"),
                    jsonObj.getDouble("splosnaOcena"),
                    jsonObj.getDouble("tezavnostOcena"),
                    jsonObj.getDouble("uporabnostOcena"),
                    jsonObj.getDouble("zanimivostOcena"),
                    oznake
            );


            /*JSONArray array = new JSONArray(result1.toString());
            JSONArray oznakeArray;
            JSONObject oznakeObj;
            List<String> oznake = new ArrayList<>();
            for(int i = 0; i < array.length(); i++){
                JSONObject jsonObj = array.getJSONObject(i);
                oznakeArray = jsonObj.getJSONArray("oznaka");
                for (int j = 0; j < oznakeArray.length(); j++) {
                    oznakeObj = oznakeArray.getJSONObject(j);
                    oznake.add(oznakeObj.getString("Ime"));
                }
                result = new Course(jsonObj.getString("ime"),
                        jsonObj.getString("izvajalci"),
                        jsonObj.getString("ocena"),
                        jsonObj.getInt("predmetID"),
                        jsonObj.getInt("splosnaOcena"),
                        jsonObj.getInt("tezavnostOcena"),
                        jsonObj.getInt("uporabnostOcena"),
                        jsonObj.getInt("zanimivostOcena"),
                        oznake
                );
            }*/
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