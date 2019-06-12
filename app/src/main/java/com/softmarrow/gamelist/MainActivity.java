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

    static List<Game> games;
    BoxAdapter adapter;
    ListView listView;

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

        LoadTask loadTask = new LoadTask(this, adapter);
        loadTask.execute(url);

        
//        new Thread(new Runnable() {
//            public void run() {
//                String jsonResult;
//                try {
//                    jsonResult = processGetRequest(url);
//                    doAllLogicWithJsonData(jsonResult);
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



    public static String processGetRequest(String url) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet request = new HttpGet(url);
// Depends on your web service
        request.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result;
        try {
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (Exception squish) {
            }
        }
        return result;
    }

    public static void doAllLogicWithJsonData(String jsonResult) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonResult);
        if (!jsonObject.has("included")) {
            System.out.println("error");
            return;
        }
        JSONArray included = jsonObject.getJSONArray("included");
        for (int i = 0; i < included.length(); i++) {
            JSONObject game = included.getJSONObject(i).getJSONObject("attributes");

            String name;
            String discountPrice;
            String realPrice;

            if (game.has("skus")) {
                name = game.getString("name");
                int skusIndex;
                if (game.getJSONArray("skus").length() == 2){
                    skusIndex = 1;
                } else {
                    skusIndex = 0;
                }
                JSONObject object = game.
                        getJSONArray("skus").getJSONObject(skusIndex).
                        getJSONObject("prices").getJSONObject("plus-user");
                if (object.optJSONObject("strikethrough-price") != null) {
                    discountPrice = object.getJSONObject("actual-price").
                            getString("display");
                    realPrice = object.getJSONObject("strikethrough-price").
                            getString("display");
                } else {
                    continue;
                }
                games.add(new Game(name, realPrice, discountPrice));
            }
        }
    }
}
