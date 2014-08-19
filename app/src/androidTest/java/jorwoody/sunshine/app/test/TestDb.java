package jorwoody.sunshine.app.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.Map;
import java.util.Set;

import jorwoody.sunshine.app.database.WeatherContract.LocationEntry;
import jorwoody.sunshine.app.database.WeatherContract.WeatherEntry;
import jorwoody.sunshine.app.database.WeatherDbHelper;

/**
 * Created by Jordan on 8/18/2014.
 */
public class TestDb extends AndroidTestCase {

    private static final String LOG_TAG = TestDb.class.getSimpleName();

    public void testCreateDb() throws Throwable {
        mContext.deleteDatabase(WeatherDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new WeatherDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsertReadDb() throws Throwable {
        WeatherDbHelper dbHelper = new WeatherDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Location
        ContentValues locationValues = createLocationValues();
        long locationRowId = db.insert(LocationEntry.TABLE_NAME, null, locationValues);
        assertTrue(locationRowId != -1);
        Log.d(LOG_TAG, "New location row id: " + locationRowId);

        Cursor cursor = db.query(LocationEntry.TABLE_NAME, null, null, null, null, null, null);

        validateCursor(cursor, locationValues);

        // Weather
        ContentValues weatherValues = createWeatherValues(locationRowId);
        long weatherRowId = db.insert(WeatherEntry.TABLE_NAME, null, weatherValues);
        assertTrue(weatherRowId != -1);
        Log.d(LOG_TAG, "New weather row id: " + weatherRowId);

        Cursor weatherCursor = db.query(WeatherEntry.TABLE_NAME, null, null, null, null, null, null);

        validateCursor(weatherCursor, weatherValues);

        dbHelper.close();
    }

    private static ContentValues createWeatherValues(long locationRowId) {
        ContentValues weatherValues = new ContentValues();
        weatherValues.put(WeatherEntry.COLUMN_LOC_KEY, locationRowId);
        weatherValues.put(WeatherEntry.COLUMN_DATETEXT, "20141205");
        weatherValues.put(WeatherEntry.COLUMN_WEATHER_ID, 321);
        weatherValues.put(WeatherEntry.COLUMN_LONG_DESC, "Raining asteroids");
        weatherValues.put(WeatherEntry.COLUMN_SHORT_DESC, "Asteroids");
        weatherValues.put(WeatherEntry.COLUMN_MIN_TEMP, 65);
        weatherValues.put(WeatherEntry.COLUMN_MAX_TEMP, 75);
        weatherValues.put(WeatherEntry.COLUMN_MORN_TEMP, 65);
        weatherValues.put(WeatherEntry.COLUMN_DAY_TEMP, 70);
        weatherValues.put(WeatherEntry.COLUMN_EVE_TEMP, 75);
        weatherValues.put(WeatherEntry.COLUMN_NIGHT_TEMP, 67);
        weatherValues.put(WeatherEntry.COLUMN_HUMIDITY, 1.2);
        weatherValues.put(WeatherEntry.COLUMN_PRESSURE, 1.3);
        weatherValues.put(WeatherEntry.COLUMN_WIND_SPEED, 5.5);
        weatherValues.put(WeatherEntry.COLUMN_WIND_DIRECTION, "NW");
        weatherValues.put(WeatherEntry.COLUMN_CLOUDS, 30);
        weatherValues.put(WeatherEntry.COLUMN_RAIN, 0);
        weatherValues.put(WeatherEntry.COLUMN_SNOW, 0);

        return weatherValues;
    }

    private static ContentValues createLocationValues() {
        String testName = "Winnipeg";
        String testLocationSetting = "R3T5N6";
        double testLatitude = 49.8994;
        double testLongitude = -97.1392;

        ContentValues values = new ContentValues();
        values.put(LocationEntry.COLUMN_CITY_NAME, testName);
        values.put(LocationEntry.COLUMN_LOCATION_SETTING, testLocationSetting);
        values.put(LocationEntry.COLUMN_LATITUDE, testLatitude);
        values.put(LocationEntry.COLUMN_LONGITUDE, testLongitude);

        return values;
    }

    private static void validateCursor(Cursor valueCursor, ContentValues expectedValues) {
        assertTrue(valueCursor.moveToFirst());

        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for(Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int i = valueCursor.getColumnIndex(columnName);
            assertFalse(i == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals(expectedValue, valueCursor.getString(i));
        }

        valueCursor.close();
    }
}
