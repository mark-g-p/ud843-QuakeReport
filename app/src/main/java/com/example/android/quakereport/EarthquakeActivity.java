/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.android.quakereport.databinding.EarthquakeActivityBinding;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    /**
     * URL for earthquake data from the USGS dataset
     */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=2&limit=26";
    // Find a reference to the {@link ListView} in the layout
    private EarthquakeAdapter adapter;

    private EarthquakeActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        binding = DataBindingUtil.setContentView(this, R.layout.earthquake_activity);
//      Check if there is an Internet connection.
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            binding.list.setEmptyView(binding.emptyView);
            adapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
            binding.list.setAdapter(adapter);

            getLoaderManager().initLoader(1, null, this);

//        Open link in browser when list item is pressed
            binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Earthquake earthquake = (Earthquake) parent.getItemAtPosition(position);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(earthquake.getUrl()));
                    startActivity(browserIntent);
                }
            });
        } else {
            binding.loadingSpinner.setVisibility(View.GONE);
            binding.emptyView.setText(getString(R.string.problem_internet_connection));
        }
    }

    private void updateUi(List<Earthquake> earthquakes) {
//        Hide ProgressBar
        binding.loadingSpinner.setVisibility(View.GONE);
//        Clean previous list from the adapter.
        adapter.clear();
        // Populate adapter with new data.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            adapter.addAll(earthquakes);
        } else {
            binding.emptyView.setText(getString(R.string.no_earthquakes_loaded));
        }
    }

    @Override
    public void onLoadFinished
            (@NonNull Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
//      Show fetched data on the screen.

        updateUi(earthquakes);
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        updateUi(new ArrayList<Earthquake>());
    }

    @NonNull
    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, @Nullable Bundle args) {
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
