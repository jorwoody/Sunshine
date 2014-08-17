package jorwoody.sunshine.app.utilities;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import jorwoody.sunshine.app.R;
import jorwoody.sunshine.app.adapters.ForecastAdapter;
import jorwoody.sunshine.app.objects.DayForecast;

/* Created by: Jordan Wood - July 2014
 * Description:
 * Provides static methods to initialize a ForecastAdapter with a 7-day forecast for the given
 * postal/zip code. Also allows refreshing of data and changing of postal/zip code.
 */
public class WeatherAPI {

    private static HttpURLConnection urlConnection = null;
    private static String forecastJsonStr;
    private static BufferedReader reader = null;
    private static ForecastAdapter mForecastAdapter;
    private static View mLoadingView;

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    private static final String QUERY_PARAM = "q";
    private static final String FORMAT_PARAM = "mode";
    private static final String UNITS_PARAM = "units";
    private static final String DAYS_PARAM = "cnt";

    public static void initialize(ForecastAdapter forecastAdapter, View loadingView) {
        mForecastAdapter = forecastAdapter;
        mLoadingView = loadingView;
        new FetchWeatherTask().execute(getLocationCode());
    }

    private static String getLocationCode() {
        Context context = mLoadingView.getContext();
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.pref_location_key), context.getString(R.string.pref_location_default));
    }

    private static String getUnits() {
        Context context = mLoadingView.getContext();
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.pref_units_key), context.getString(R.string.pref_units_default));
    }

    public static void refresh(View loadingView) {
        mLoadingView = loadingView;
        new FetchWeatherTask().execute(getLocationCode());
    }

    private static class FetchWeatherTask extends AsyncTask<String, Void, DayForecast[]> {

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingView.setVisibility(View.VISIBLE);
        }

        /*
         * Description:
         * Makes call to OWM API and parses the returned JSON into DayForecast objects.
         */
        @Override
        protected DayForecast[] doInBackground(String... params) {
            String format = "json";
            String units = getUnits();
            int numDays = 7;

            try {
                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, params[0])
                        .appendQueryParameter(FORMAT_PARAM, format)
                        .appendQueryParameter(UNITS_PARAM, units)
                        .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                        .build();
                URL url = new URL(builtUri.toString());
                Log.d(LOG_TAG, "Built URI: " + builtUri.toString());

                // create request to OpenWeatherMap, and open it
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // read the input stream into a String
                InputStream inputStream = null;
                try {
                    inputStream = urlConnection.getInputStream();
                } catch(IOException e) {
                    Log.e("Error", e.getMessage());
                }

                StringBuffer buffer = new StringBuffer();
                if(inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = reader.readLine()) != null) {
                    buffer.append(line + "\n"); // newline for readability
                }

                if(buffer.length() == 0) {
                    return null;
                }
                forecastJsonStr = buffer.toString();
            } catch(IOException e) {
                Log.e(LOG_TAG, "Error", e);
                return null;
            } finally {
                if(urlConnection != null) {
                    urlConnection.disconnect();
                }

                if(reader != null) {
                    try {
                        reader.close();
                    } catch(final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return WeatherDataParser.getWeatherDataFromJson(forecastJsonStr, numDays);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        /*
         * Description:
         * Add the forecasts (retrieved from OWM API) to the ForecastAdapter
         */
        @Override
        protected void onPostExecute(DayForecast[] forecasts) {
            super.onPostExecute(forecasts);
            if(forecasts != null) {
                mForecastAdapter.clear();
                for (DayForecast forecast : forecasts) {
                    mForecastAdapter.add(forecast);
                }
                Toast.makeText(mLoadingView.getContext(), "Forecast refreshed!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mLoadingView.getContext(), "Error retrieving forecast. Please try again.", Toast.LENGTH_LONG).show();
            }
            mForecastAdapter.notifyDataSetChanged();
            mLoadingView.setVisibility(View.INVISIBLE);
        }
    }
}

