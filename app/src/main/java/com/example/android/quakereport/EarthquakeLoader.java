package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    private String[] urls;
    EarthquakeLoader(Context context, String... urls) {
        super(context);
        this.urls = urls;
    }

    @Override
    public List<Earthquake> loadInBackground() {
        // Don't perform the request if there are no URLs, or the first URL is null.
        if (urls.length < 1 || urls[0] == null) {
            return null;
        }
        // Perform the HTTP request for earthquake data and process the response.
        return QueryUtils.extractEarthquakes(urls[0]);
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}
