package jorwoody.sunshine.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import jorwoody.sunshine.app.utilities.DownloadImageTask;
import jorwoody.sunshine.app.R;
import jorwoody.sunshine.app.objects.DayForecast;
import jorwoody.sunshine.app.objects.Weather;

/* Created by: Jordan Wood - July 2014
 * Description:
 * Adapter for forecast listview
 */
public class ForecastAdapter extends ArrayAdapter {

    private Context mContext;
    private ArrayList<DayForecast> mForecasts;

    public ForecastAdapter(Context context, int resource, ArrayList<DayForecast> forecasts) {
        super(context, resource, forecasts);
        mContext = context;
        mForecasts = forecasts;
    }

    @Override
    public Object getItem(int position) {
        return mForecasts.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item_forecast, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) rowView.findViewById(R.id.list_item_forecast_image);
            viewHolder.text = (TextView) rowView.findViewById(R.id.list_item_forecast_text);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        DayForecast tempForecast = mForecasts.get(position);
        Weather tempWeather = tempForecast.getWeather();

        new DownloadImageTask(holder.image).execute(tempWeather);

        // Day, Description, High/Low
        String combinedText = tempForecast.getDay() + ", " + tempForecast.getWeather().getDescription() + ", " + tempForecast.getMaxTemp() + "/" + tempForecast.getMinTemp();
        holder.text.setText(combinedText);

        return rowView;
    }

    // View holder for list_item_forecast
    static class ViewHolder {
        public ImageView image;
        public TextView text;
    }
}
