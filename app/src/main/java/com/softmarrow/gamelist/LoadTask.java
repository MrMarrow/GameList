package com.softmarrow.gamelist;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONException;

import java.io.IOException;

import static com.softmarrow.gamelist.MainActivity.doAllLogicWithJsonData;
import static com.softmarrow.gamelist.MainActivity.processGetRequest;

class LoadTask extends AsyncTask<String, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    private MainActivity activity;
    private BoxAdapter adapter;

    public LoadTask(MainActivity activity, BoxAdapter adapter) {
        super();
        this.activity = activity;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(String... strings) {
        String jsonResult;
        for (String url : strings)
            try {
                jsonResult = processGetRequest(url);
                doAllLogicWithJsonData(jsonResult);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.findViewById(R.id.prBar).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        activity.findViewById(R.id.prBar).setVisibility(View.INVISIBLE);
        adapter.notifyDataSetChanged();
    }
}