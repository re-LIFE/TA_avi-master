package com.avi1.celty.myapplication.berita.BottomTab.other;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.avi1.celty.myapplication.R;
import com.avi1.celty.myapplication.berita.adapter.BeritaAdapter;
import com.avi1.celty.myapplication.berita.konten.BeritaUtama;
import com.avi1.celty.myapplication.berita.konten.DetailBerita;

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
import java.util.HashMap;

@SuppressLint("ValidFragment")
public class AFragment extends Fragment
{
    int wizard_page_position;


    @SuppressLint("ValidFragment")
    public  AFragment(int position) {
        this.wizard_page_position = position;
    }

    ListView listView;
    String baseurl = "http://172.20.10.5/remote/aviserver/berita.php";
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        {
            int layout_id = R.layout.walkthrough10_fragment;
            switch (wizard_page_position) {
                case 0:

                    layout_id = R.layout.berita_utama;
                    break;

                case 1:
                    layout_id = R.layout.list_row;
                    break;

                case 2:
                    layout_id = R.layout.walkthrough12_fragment;
                    break;

                case 3:
                    layout_id = R.layout.walkthrough13_fragment;
                    break;
            }

            View view = inflater.inflate(layout_id, container, false);
            switch (wizard_page_position){
                case 0:
                    berita(view);
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
            return view;
        }
    }

    private void berita(View view){
        listView = (ListView)view.findViewById(R.id.list);
        new sync().execute();
    }

    class sync extends AsyncTask<Void, Void, String>{
        ProgressDialog dialog;
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(false);
            dialog.setIndeterminate(false);
            dialog.setMessage("Loading...!!!");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            try {
                URL url = new URL(baseurl);
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
            dialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("berita");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objItem = jsonArray.getJSONObject(i);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", objItem.getString("id"));
                    hashMap.put("judul", objItem.getString("judul"));
                    hashMap.put("gambar", objItem.getString("gambar"));

                    arrayList.add(hashMap);
                }

                BeritaAdapter adapter = new BeritaAdapter(getActivity(), arrayList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getActivity(), DetailBerita.class);
                        intent.putExtra("id", arrayList.get(i).get("id"));
                        startActivity(intent);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
