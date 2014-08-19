package jorwoody.sunshine.app.database;

import android.provider.BaseColumns;

/**
 * Created by Jordan on 8/17/2014.
 */
public class WeatherContract {

    public static final class WeatherEntry implements BaseColumns {

        public static final String TABLE_NAME = "weather";

        public static final String COLUMN_LOC_KEY = "location_id";
        public static final String COLUMN_DATETEXT = "date";
        public static final String COLUMN_WEATHER_ID = "weather_id";
        public static final String COLUMN_LONG_DESC = "long_desc";
        public static final String COLUMN_SHORT_DESC = "short_desc";
        public static final String COLUMN_MIN_TEMP = "min";
        public static final String COLUMN_MAX_TEMP = "max";
        public static final String COLUMN_MORN_TEMP = "morning";
        public static final String COLUMN_DAY_TEMP = "day";
        public static final String COLUMN_EVE_TEMP = "evening";
        public static final String COLUMN_NIGHT_TEMP = "night";
        public static final String COLUMN_HUMIDITY = "humidity";
        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_WIND_SPEED = "speed";
        public static final String COLUMN_WIND_DIRECTION = "direction";
        public static final String COLUMN_CLOUDS = "clouds";
        public static final String COLUMN_RAIN = "rain";
        public static final String COLUMN_SNOW = "snow";
    }

    public static final class LocationEntry implements BaseColumns {

        public static final String TABLE_NAME = "location";

        public static final String COLUMN_CITY_NAME = "city_name";
        public static final String COLUMN_LOCATION_SETTING = "location_setting";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
    }
}
