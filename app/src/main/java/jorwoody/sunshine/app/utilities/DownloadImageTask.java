package jorwoody.sunshine.app.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

import jorwoody.sunshine.app.objects.Weather;

/* Created by: Jordan Wood - July 2014
 * Description:
 * Downloads icon image from OWM endpoint, or simply returns it if we already have it
 */
public class DownloadImageTask extends AsyncTask<Weather, Void, Bitmap> {

    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(Weather... weathers) {
        Weather weather = weathers[0];
        if(weather.getIconBmp() == null) {
            try {
                InputStream in = new java.net.URL(weather.getIconUrl()).openStream();
                weather.setIconBmp(BitmapFactory.decodeStream(in));
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }

        return weather.getIconBmp();
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
