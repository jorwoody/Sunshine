package jorwoody.sunshine.app.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import jorwoody.sunshine.app.R;
import jorwoody.sunshine.app.fragments.ForecastFragment;
import jorwoody.sunshine.app.utilities.WeatherAPI;

/* Created by: Jordan Wood - July 2014
 * Description:
 * Main activity showing the 7-day forecast
 */
public class MainActivity extends ActionBarActivity {

    private DrawerLayout mDrawer;
    private EditText mLocation;
    private Button mButtonDone;
    public static String locationCode;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }

        prefs = getPreferences(MODE_PRIVATE);
        prefEditor = prefs.edit();
        locationCode = prefs.getString("locationCode", "R3T5E1");   // default value for Winnipeg, Manitoba, Canada
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLocation = (EditText) mDrawer.findViewById(R.id.edit_postal_code);
        mLocation.setText(locationCode);
        mButtonDone = (Button) mDrawer.findViewById(R.id.button_done);
        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(Gravity.RIGHT);
            }
        });
        mDrawer.setDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mLocation.requestFocus();
                mLocation.setSelection(0, locationCode.length());
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mLocation, InputMethodManager.SHOW_IMPLICIT);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if(!locationCode.equalsIgnoreCase(mLocation.getText().toString())) {
                    locationCode = mLocation.getText().toString();
                    prefEditor.putString("locationCode", locationCode);
                    prefEditor.apply();
                    WeatherAPI.changeLocation(locationCode);
                }
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mDrawer.getWindowToken(), 0);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
