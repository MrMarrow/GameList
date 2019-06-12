package com.softmarrow.gamelist;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Game> games;
    private BoxAdapter adapter;
    private ListView listView;

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        games = new ArrayList<>();
        adapter = new BoxAdapter(this, games);
        listView = findViewById(R.id.listItem);

        //custom list

        // присваиваем адаптер списку
        listView.setAdapter(adapter);
        //String url = "https://store.playstation.com/ru-ru/grid/STORE-MSF75508-PRICEDROPSCHI/1";
        final String url = "https://store.playstation.com/valkyrie-api/ru/RU/999/container/STORE-MSF75508-GAMESPECIALOFF?size=30&bucket=games&start=0";

        LoadTask loadTask = new LoadTask(this, adapter, games);
        loadTask.execute(url);


//        new Thread(new Runnable() {
//            public void run() {
//                String jsonResult;
//                try {
//                    jsonResult = processGetRequest(url);
//                    makeGameParames(jsonResult);
//                } catch (JSONException | IOException e) {
//                    e.printStackTrace();
//                }
//
//                MainActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter.notifyDataSetChanged();
//                    }
//                });
//            }
//        }).start();
    }






}
