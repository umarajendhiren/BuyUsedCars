package com.androidapps.buyusedcars.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStateAdapter {


    private final List<Fragment> mFragmentList = new ArrayList<>();


    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentActivity fm) {
        super(fm);
        mContext = context;
    }


    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);

    }

    @Override
    public int getItemCount() {


        return mFragmentList.size();
    }

}