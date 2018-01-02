package com.avi1.celty.myapplication.berita.konten;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avi1.celty.myapplication.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DetailBerita extends AppCompatActivity {
    public ImageLoader imageLoader;
    {
        imageLoader = new ImageLoader(null);
    }

    JSONArray string_json = null;
    String idberita;

    JSONParser jsonParser = new JSONParser();
    public static final String TAG_ID = "id";
    public static final String TAG_JUDUL = "judul";
    public static final String TAG_ISI = "isi";
    public static final String TAG_GAMBAR = "gambar";
    private static final String url_detail_berita =
            "http://172.20.10.5/remote/aviserver/detailberita.php?id_berita=";
    TextView judul;
    TextView content;
    ImageView imageView1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item);

        Intent i = getIntent();
        idberita = i.getStringExtra(TAG_ID);

        judul = (TextView)findViewById(R.id.judul);
        imageView1 = (ImageView)findViewById(R.id.imageView1);
        content = (TextView)findViewById(R.id.content);

        new AmbilDetailBerita().execute();

    }
    class AmbilDetailBerita extends AsyncTask<Void, Void,String> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DetailBerita.this);
            pDialog.setMessage("Mohon Tunggu ... !");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            try {
                URL url = new URL(url_detail_berita+idberita);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                result = sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("berita");
                JSONObject objItem = jsonArray.getJSONObject(0);

                judul.setText(objItem.getString(TAG_JUDUL));
                content.setText(objItem.getString(TAG_ISI));
                Glide.with(DetailBerita.this).load(objItem.getString(TAG_GAMBAR))
                        .thumbnail(0.5f)
                        .override(200,200)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView1);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}