package si.lj.uni.fri.tpo.fripredmeti.REST;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Blaz on 27-Dec-17.
 */

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap result = null;
        try {
            StringBuffer result1 = new StringBuffer("");
            URL url = new URL(strings[0]);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();

            /* Buffered is always good for a performance plus. */
            BufferedInputStream bis = new BufferedInputStream(is);

            /* Decode url-data to a bitmap. */
            result = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
