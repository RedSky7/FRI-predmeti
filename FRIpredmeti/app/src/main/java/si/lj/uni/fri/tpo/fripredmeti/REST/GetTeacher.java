package si.lj.uni.fri.tpo.fripredmeti.REST;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import si.lj.uni.fri.tpo.fripredmeti.Model.Teacher;
import si.lj.uni.fri.tpo.fripredmeti.TeacherOverview;

/**
 * Created by Blaz on 27-Dec-17.
 */

public class GetTeacher extends AsyncTask<Integer, Void, Teacher> {

    @Override
    protected Teacher doInBackground(Integer... params)
    {
        Teacher result = null;
        try
        {
            StringBuffer result1 = new StringBuffer("");
            URL url = new URL("http://friaplikacija.azurewebsites.net/Service.svc/Izvajalec");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //nastavi prave klice v header
            connection.setRequestProperty("izvajalecID", Integer.toString(params[0]));
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while ((line = rd.readLine()) != null) {result1.append(line);}

            JSONObject jsonObj = new JSONObject(result1.toString());
            result = new Teacher(jsonObj.getInt("izvajalecID"),
                    jsonObj.getString("ime"),
                    jsonObj.getString("naziv"),
                    jsonObj.getString("opis"),
                    jsonObj.getString("priimek"),
                    jsonObj.getString("slika"),
                    jsonObj.getDouble("splosnaOcena"),
                    jsonObj.getString("email"));
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
