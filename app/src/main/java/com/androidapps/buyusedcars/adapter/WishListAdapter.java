package com.androidapps.buyusedcars.adapter;

import androidx.lifecycle.ViewModel;

import com.androidapps.buyusedcars.model.UsedCarDetailsModel;
import com.androidapps.buyusedcars.viewmodels.WishListViewModel;

import java.util.ArrayList;
import java.util.List;

public class WishListAdapter extends BaseAdapter {


    private final int layoutId;
    private WishListViewModel wishListViewModel;
    private List<UsedCarDetailsModel> carDetailsList = new ArrayList<>();

    public WishListAdapter(int layoutId, WishListViewModel viewModel) {
        this.layoutId = layoutId;
        this.wishListViewModel = viewModel;
    }

    public void setCarDetailsList(List<UsedCarDetailsModel> carDetailsList) {
        this.carDetailsList = carDetailsList;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    @Override
    protected ViewModel getViewmodel() {
        return wishListViewModel;
    }

    @Override
    protected Object getObjForPosition(int position) {
        return carDetailsList.get(position);
    }


    @Override
    public int getItemCount() {
        return carDetailsList == null ? 0 : carDetailsList.size();
    }


}
