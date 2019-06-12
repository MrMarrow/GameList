package com.softmarrow.gamelist;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


class IconLoadTask extends AsyncTask<String, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    private ImageView imageView;
    private String iconUrl;
    private Bitmap downloaded;
    private static Map<String, Bitmap> cache = new ConcurrentHashMap<>();

    public IconLoadTask(ImageView imageView, String iconUrl) {
        super();
        this.imageView = imageView;
        this.iconUrl = iconUrl;
    }

    @Override
    protected Void doInBackground(String... strings) {
        if (cache.containsKey(iconUrl)){
            downloaded = cache.get(iconUrl);
            return null;
        }
        InputStream in = null;
        try {
            in = new java.net.URL(iconUrl).openStream();
            downloaded = getResizedBitmap(BitmapFactory.decodeStream(in), 50, 50);
            cache.put(iconUrl, downloaded);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        imageView.setImageBitmap(downloaded);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);

        return resizedBitmap;
    }
}