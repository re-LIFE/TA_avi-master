package com.avi1.celty.myapplication.berita.BottomTab.other;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.avi1.celty.myapplication.berita.opening.WalkthroughStyle10Fragment;

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private int size;

    public MyViewPagerAdapter(FragmentManager fm,int size) {
        super(fm);
        this.size = size;
    }

    @Override
    public Fragment getItem(int position) {
        return new AFragment(position);
    }

    @Override
    public int getCount() {
        return size;
    }
}
