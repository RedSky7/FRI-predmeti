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

import si.lj.uni.fri.tpo.fripredmeti.Model.AreasModel;

/**
 * Created by Boštjan on 8.1.2018.
 */

public class GetAllPodrocja extends AsyncTask<Integer, Void, List<AreasModel>> {

    @Override
    protected List<AreasModel> doInBackground(Integer... params)
    {
        List<AreasModel> result = new ArrayList<>();
        try
        {
            StringBuilder result1 = new StringBuilder("");
            URL url = new URL("http://friaplikacija.azurewebsites.net/Service.svc/AllPodrocja");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while ((line = rd.readLine()) != null) { final StringBuilder append = result1.append(line);}

            JSONArray array = new JSONArray(result1.toString());
            for(int i = 0; i < array.length(); i++){
                JSONObject jsonObj = array.getJSONObject(i);
                result.add(new AreasModel(jsonObj.getInt("podrocjeID"),
                        jsonObj.getString("imePodrocja")));
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
