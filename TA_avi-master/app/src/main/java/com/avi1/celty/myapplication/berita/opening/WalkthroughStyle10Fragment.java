package com.avi1.celty.myapplication.berita.opening;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avi1.celty.myapplication.R;


/**
 * Created by wahyu on 15/11/16.
 */

@SuppressLint("ValidFragment")
public class WalkthroughStyle10Fragment extends Fragment {
    int wizard_page_position;

    public WalkthroughStyle10Fragment(int position) {
        this.wizard_page_position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout_id = R.layout.walkthrough10_fragment;
        switch (wizard_page_position) {
            case 0:
                layout_id = R.layout.walkthrough10_fragment;
                break;

            case 1:
                layout_id = R.layout.walkthrough11_fragment;
                break;

            case 2:
                layout_id = R.layout.walkthrough12_fragment;
                break;

            case 3:
                layout_id = R.layout.walkthrough13_fragment;
                break;
        }

        View view = inflater.inflate(layout_id, container, false);
        return view;
    }


}
