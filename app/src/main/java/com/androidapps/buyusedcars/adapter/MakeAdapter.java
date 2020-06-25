package com.androidapps.buyusedcars.adapter;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.androidapps.buyusedcars.model.MakeModel;
import com.androidapps.buyusedcars.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.List;


public class MakeAdapter extends BaseAdapter {

    private final int layoutId;
    private MainViewModel viewmodel;
    private List<MakeModel> makeList = new ArrayList<>();

    public MakeAdapter(int layoutId, MainViewModel mainViewModel) {
        this.layoutId = layoutId;
        viewmodel = mainViewModel;
    }

    @Override
    protected Object getObjForPosition(int position) {
        return makeList.get(position);
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    @Override
    protected ViewModel getViewmodel() {
        return viewmodel;
    }

    @Override
    public int getItemCount() {
        return makeList == null ? 0 : makeList.size();
    }

    public void setMakeList(List<MakeModel> makeList) {
        Log.d("setMakeList: ", String.valueOf(makeList.size()));
        this.makeList = makeList;
    }

    public void filterList(ArrayList<MakeModel> filteredList) {

        //set the filtered list value in adapter arraylist
        this.makeList = filteredList;
        notifyDataSetChanged();


    }

}