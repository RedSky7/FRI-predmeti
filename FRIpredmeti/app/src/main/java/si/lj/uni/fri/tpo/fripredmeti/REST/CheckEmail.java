package si.lj.uni.fri.tpo.fripredmeti.REST;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by Blaz on 06-Jan-18.
 */

public class CheckEmail extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params)
    {
        String result = null;
        try
        {
            StringBuffer result1 = new StringBuffer("");
            URL url = new URL("http://friaplikacija.azurewebsites.net/Service.svc/CheckEmail");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //nastavi prave klice v header
            connection.setRequestProperty("email", params[0]);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while ((line = rd.readLine()) != null) {result1.append(line);}

            result = result1.toString();

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
