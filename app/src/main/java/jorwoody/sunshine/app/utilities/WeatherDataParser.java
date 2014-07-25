package jorwoody.sunshine.app.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import jorwoody.sunshine.app.objects.DayForecast;
import jorwoody.sunshine.app.objects.Weather;

/* Created by: Jordan Wood - July 2014
 * Description:
 * Provides static methods for parsing JSON response from OpenWeatherMap.org API
 */
public class WeatherDataParser {

    private static final String LOG_TAG = "WeatherDataParser";

    private static String getReadableDateString(long time){
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat("E, MMM d");
        return format.format(date).toString();
    }

    /**
     * Take the field data from the JSON string.
     * Unfortunately, the OpenWeatherMap API and it's documentation are quite out of sync,
     * but I did my best to get all the info.
     */
    public static DayForecast[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
            throws JSONException {
        // These are the names of the JSON objects that need to be extracted.
        final String OWM_LIST = "list";
        final String OWM_DATETIME = "dt";
        final String OWM_TEMPERATURE = "temp";
        final String OWM_DAY = "day";
        final String OWM_MIN = "min";
        final String OWM_MAX = "max";
        final String OWM_NIGHT = "night";
        final String OWM_EVENING = "eve";
        final String OWM_MORNING = "morn";
        final String OWM_PRESSURE = "pressure";
        final String OWM_HUMIDITY = "humidity";
        final String OWM_WEATHER = "weather";
        final String OWM_ID = "id";
        final String OWM_MAIN = "main";
        final String OWM_DESCRIPTION = "description";
        final String OWM_ICON = "icon";
        final String OWM_SPEED = "speed";
        final String OWM_DEGREE = "deg";
        final String OWM_CLOUDS = "clouds";
        final String OWM_RAIN = "rain";
        final String OWM_SNOW = "snow";

        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

        DayForecast[] dayForecasts = new DayForecast[numDays];

        for(int i = 0; i < weatherArray.length(); i++) {
            String day;
            double[] temps = new double[6];
            double pressure;
            int humidity;
            Weather weather;
            double speed;
            int degree;
            int clouds;
            double rain;
            double snow;

            // Get the JSON object representing the day
            JSONObject dayForecast = weatherArray.getJSONObject(i);

            // Parse out the info into the DayForecast parameters
            long dateTime = dayForecast.getLong(OWM_DATETIME);
            day = getReadableDateString(dateTime);

            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            weather = new Weather(weatherObject.getInt(OWM_ID), weatherObject.getString(OWM_MAIN), weatherObject.getString(OWM_DESCRIPTION), weatherObject.getString(OWM_ICON));

            // temps array:    0 = day; 1 = min; 2 = max; 3 = night; 4 = evening; 5 = morning
            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
            temps[0] = Math.round(temperatureObject.getDouble(OWM_DAY));
            temps[1] = Math.round(temperatureObject.getDouble(OWM_MIN));
            temps[2] = Math.round(temperatureObject.getDouble(OWM_MAX));
            temps[3] = Math.round(temperatureObject.getDouble(OWM_NIGHT));
            temps[4] = Math.round(temperatureObject.getDouble(OWM_EVENING));
            temps[5] = Math.round(temperatureObject.getDouble(OWM_MORNING));

            pressure = dayForecast.getDouble(OWM_PRESSURE);
            humidity = dayForecast.getInt(OWM_HUMIDITY);
            speed = dayForecast.getInt(OWM_SPEED);
            degree = dayForecast.getInt(OWM_DEGREE);
            clouds = dayForecast.getInt(OWM_CLOUDS);
            rain = (dayForecast.has(OWM_RAIN) ? dayForecast.getDouble(OWM_RAIN) : 0);
            snow = (dayForecast.has(OWM_SNOW) ? dayForecast.getDouble(OWM_SNOW) : 0);

            dayForecasts[i] = new DayForecast(day, temps, pressure, humidity, weather, speed, degree, clouds, rain, snow);
        }

        return dayForecasts;
    }
}
