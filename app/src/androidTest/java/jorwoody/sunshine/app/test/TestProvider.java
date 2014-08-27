package jorwoody.sunshine.app.test;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.test.AndroidTestCase;
import android.util.Log;

import jorwoody.sunshine.app.data.WeatherContract.LocationEntry;
import jorwoody.sunshine.app.data.WeatherContract.WeatherEntry;
import jorwoody.sunshine.app.data.WeatherDbHelper;

/**
 * Created by: Jordan Wood - August 2014.
 * Description:
 * Provider tests
 */
public class TestProvider extends AndroidTestCase {

    public static final String LOG_TAG = TestProvider.class.getSimpleName();

    public void testDeleteDb() throws Throwable {
        mContext.deleteDatabase(WeatherDbHelper.DATABASE_NAME);
    }

    public void testInsertReadProvider() throws Throwable {
        WeatherDbHelper dbHelper = new WeatherDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Location
        ContentValues testValues = TestDb.createLocationValues();
        long locationRowId = db.insert(LocationEntry.TABLE_NAME, null, testValues);
        assertTrue(locationRowId != -1);
        Log.d(LOG_TAG, "New location row id: " + locationRowId);

        Cursor cursor = mContext.getContentResolver().query(LocationEntry.CONTENT_URI, null, null, null, null);
        TestDb.validateCursor(cursor, testValues);
        cursor.close();

        cursor = mContext.getContentResolver().query(LocationEntry.buildLocationUri(locationRowId), null, null, null, null);
        TestDb.validateCursor(cursor, testValues);
        cursor.close();

        // Weather
        ContentValues weatherValues = TestDb.createWeatherValues(locationRowId);
        long weatherRowId = db.insert(WeatherEntry.TABLE_NAME, null, weatherValues);
        assertTrue(weatherRowId != -1);
        Log.d(LOG_TAG, "New weather row id: " + weatherRowId);

        Cursor weatherCursor = mContext.getContentResolver().query(WeatherEntry.CONTENT_URI, null, null, null, null);
        TestDb.validateCursor(weatherCursor, weatherValues);
        weatherCursor.close();

        addAllContentValues(weatherValues, testValues);

        // Get the joined Weather and Location data
        weatherCursor = mContext.getContentResolver().query(WeatherEntry.buildWeatherLocation(TestDb.TEST_LOCATION), null, null, null, null);
        TestDb.validateCursor(weatherCursor, weatherValues);
        weatherCursor.close();

        // Get the joined Weather and Location data with a start date
        weatherCursor = mContext.getContentResolver().query(WeatherEntry.buildWeatherLocationWithStartDate(TestDb.TEST_LOCATION, TestDb.TEST_DATE),
                null, null, null, null);
        TestDb.validateCursor(weatherCursor, weatherValues);
        weatherCursor.close();

        dbHelper.close();
    }

    public void testGetType() {
        // content://com.example.android.sunshine.app/weather/
        String type = mContext.getContentResolver().getType(WeatherEntry.CONTENT_URI);
        // vnd.android.cursor.dir/com.example.android.sunshine.app/weather
        assertEquals(WeatherEntry.CONTENT_TYPE, type);

        String testLocation = "94074";
        // content://com.example.android.sunshine.app/weather/94074
        type = mContext.getContentResolver().getType(WeatherEntry.buildWeatherLocation(testLocation));
        // vnd.android.cursor.dir/com.example.android.sunshine.app/weather
        assertEquals(WeatherEntry.CONTENT_TYPE, type);

        String testDate = "20140612";
        // content://com.example.android.sunshine.app/weather/94074/20140612
        type = mContext.getContentResolver().getType(WeatherEntry.buildWeatherLocationWithDate(testLocation, testDate));
        // vnd.android.cursor.item/com.example.android.sunshine.app/weather
        assertEquals(WeatherEntry.CONTENT_ITEM_TYPE, type);

        // content://com.example.android.sunshine.app/location/
        type = mContext.getContentResolver().getType(LocationEntry.CONTENT_URI);
        // vnd.android.cursor.dir/com.example.android.sunshine.app/location
        assertEquals(LocationEntry.CONTENT_TYPE, type);

        // content://com.example.android.sunshine.app/location/1
        type = mContext.getContentResolver().getType(LocationEntry.buildLocationUri(1L));
        // vnd.android.cursor.item/com.example.android.sunshine.app/location
        assertEquals(LocationEntry.CONTENT_ITEM_TYPE, type);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void addAllContentValues(ContentValues destination, ContentValues source) {
        for (String key : source.keySet()) {
            destination.put(key, source.getAsString(key));
        }
    }
}
