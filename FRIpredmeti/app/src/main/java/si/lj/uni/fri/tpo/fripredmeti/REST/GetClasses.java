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
 * Created by Blaz on 30-Dec-17.
 */

public class GetClasses extends AsyncTask<Integer, Void, List<Course> > {

    @Override
    protected List<Course> doInBackground(Integer... params)
    {
        List<Course> result = new ArrayList<Course>();
        try
        {
            StringBuilder result1 = new StringBuilder("");
            URL url = new URL("http://friaplikacija.azurewebsites.net/Service.svc/PredmetiForIzvajalec");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //nastavi prave klice v header
            connection.setRequestProperty("izvajalecID", Integer.toString(params[0]));
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while ((line = rd.readLine()) != null) {result1.append(line);}

            JSONArray array = new JSONArray(result1.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                result.add(new Course(
                        jsonObject.getString("ime"),
                        jsonObject.getString("ocena"),
                        jsonObject.getInt("predmetID"),
                        jsonObject.getInt("splosnaOcena"),
                        jsonObject.getInt("tezavnostOcena"),
                        jsonObject.getInt("uporabnostOcena"),
                        jsonObject.getInt("zanimivostOcena")));
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
