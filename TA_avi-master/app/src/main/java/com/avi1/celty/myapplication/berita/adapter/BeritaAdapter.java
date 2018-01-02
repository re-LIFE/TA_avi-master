package com.avi1.celty.myapplication.berita.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avi1.celty.myapplication.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ipul on 12/31/2017.
 */

public class BeritaAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>> arrayList;
    public BeritaAdapter(Context context, ArrayList<HashMap<String, String>> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_row, viewGroup, false);

        ImageView img = (ImageView)view.findViewById(R.id.gambar);
        TextView judul = (TextView)view.findViewById(R.id.judul);
        TextView kode = (TextView)view.findViewById(R.id.kode);

        judul.setText(arrayList.get(i).get("judul"));
        Glide.with(context).load(arrayList.get(i).get("gambar"))
                .thumbnail(0.5f)
                .override(200,200)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img);

        return view;
    }
}
