package com.softmarrow.gamelist;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;

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
import java.net.MalformedURLException;
import java.util.List;


class LoadTask extends AsyncTask<String, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    private MainActivity activity;
    private BoxAdapter adapter;
    private static List<Game> games;

    public LoadTask(MainActivity activity, BoxAdapter adapter, List<Game> games) {
        super();
        this.activity = activity;
        this.adapter = adapter;
        this.games = games;
    }

    @Override
    protected Void doInBackground(String... strings) {
        String jsonResult;
        for (String url : strings)
            try {
                jsonResult = processGetRequest(url);
                makeGameParames(jsonResult);
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

    public static void makeGameParames(String jsonResult) throws JSONException, IOException {
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
            String iconUrl;

            if (game.has("skus")) {
                name = game.getString("name");
                int skusIndex;
                //if skus length = 2 then skus[0] - demo version
                if (game.getJSONArray("skus").length() == 2){
                    skusIndex = 1;
                } else {
                    skusIndex = 0;
                }

                iconUrl = game.getString("thumbnail-url-base");



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
                games.add(new Game(name, realPrice, discountPrice, iconUrl));
            }
        }
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
}