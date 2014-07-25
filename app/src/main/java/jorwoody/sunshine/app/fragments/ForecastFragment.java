package jorwoody.sunshine.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import jorwoody.sunshine.app.R;
import jorwoody.sunshine.app.activities.DetailActivity;
import jorwoody.sunshine.app.adapters.ForecastAdapter;
import jorwoody.sunshine.app.objects.DayForecast;
import jorwoody.sunshine.app.utilities.WeatherAPI;

/* Created by: Jordan Wood - July 2014
 * Description:
 * Fragment to display 7-day forecast
 */
public class ForecastFragment extends Fragment {

    ForecastAdapter mForecastAdapter;
    View loadingView;

    public ForecastFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecast_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            WeatherAPI.refresh((TextView) getView().findViewById(R.id.loading_view));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView loadingView = (TextView) rootView.findViewById(R.id.loading_view);

        mForecastAdapter = new ForecastAdapter(getActivity(), R.layout.list_item_forecast, new ArrayList<DayForecast>());
        if(mForecastAdapter.getCount() < 7)
            WeatherAPI.initialize("R3T5N6", mForecastAdapter, loadingView);
        ListView listViewForecast = (ListView) rootView.findViewById(R.id.listview_forecast);
        listViewForecast.setAdapter(mForecastAdapter);
        listViewForecast.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DayForecast forecast = (DayForecast) mForecastAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DayForecast.EXTRA_FORECAST, forecast);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
