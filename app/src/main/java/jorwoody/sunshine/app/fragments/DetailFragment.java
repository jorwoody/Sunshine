package jorwoody.sunshine.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import jorwoody.sunshine.app.objects.DayForecast;
import jorwoody.sunshine.app.R;

/* Created by: Jordan Wood - July 2014
 * Description:
 * Fragment to display a single day's forecast information
 */
public class DetailFragment extends Fragment {

    DayForecast mForecast;

    public DetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        if(intent != null && intent.hasExtra(DayForecast.EXTRA_FORECAST)) {
            mForecast = (DayForecast) intent.getExtras().getParcelable(DayForecast.EXTRA_FORECAST);
        }

        prepView(rootView);

        return rootView;
    }

    private void prepView(View rootView) {
        TextView textDate = (TextView) rootView.findViewById(R.id.detail_date);
        ImageView imageWeather = (ImageView) rootView.findViewById(R.id.detail_weather_icon);
        TextView textDesc = (TextView) rootView.findViewById(R.id.detail_description);
        TextView textHigh = (TextView) rootView.findViewById(R.id.detail_high);
        TextView textLow = (TextView) rootView.findViewById(R.id.detail_low);
        TextView textMorning = (TextView) rootView.findViewById(R.id.detail_morning);
        TextView textDay = (TextView) rootView.findViewById(R.id.detail_day);
        TextView textEvening = (TextView) rootView.findViewById(R.id.detail_evening);
        TextView textNight = (TextView) rootView.findViewById(R.id.detail_night);
        TextView textPressure = (TextView) rootView.findViewById(R.id.detail_pressure);
        TextView textHumidity = (TextView) rootView.findViewById(R.id.detail_humidity);
        TextView textClouds = (TextView) rootView.findViewById(R.id.detail_clouds);
        TextView textWind = (TextView) rootView.findViewById(R.id.detail_wind);
        TextView textRain = (TextView) rootView.findViewById(R.id.detail_rain);
        TextView textSnow = (TextView) rootView.findViewById(R.id.detail_snow);

        textDate.setText(mForecast.getDay());
        imageWeather.setImageBitmap(mForecast.getWeather().getIconBmp());
        textDesc.setText(mForecast.getWeather().getDescription());
        textHigh.setText(Double.toString(mForecast.getMaxTemp()));
        textLow.setText(Double.toString(mForecast.getMinTemp()));
        textMorning.setText(Double.toString(mForecast.getMorningTemp()));
        textDay.setText(Double.toString(mForecast.getDayTemp()));
        textEvening.setText(Double.toString(mForecast.getEveningTemp()));
        textNight.setText(Double.toString(mForecast.getNightTemp()));
        textPressure.setText(Double.toString(mForecast.getPressure()));
        textHumidity.setText(Integer.toString(mForecast.getHumidity()));
        textClouds.setText(Integer.toString(mForecast.getClouds()));
        textWind.setText(mForecast.getDirection() + " @ " + Double.toString(mForecast.getSpeed()));
        textRain.setText(Double.toString(mForecast.getRain()));
        textSnow.setText(Double.toString(mForecast.getSnow()));
    }
}
