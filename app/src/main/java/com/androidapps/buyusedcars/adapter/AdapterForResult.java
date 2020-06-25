package com.androidapps.buyusedcars.adapter;

import androidx.lifecycle.ViewModel;

import com.androidapps.buyusedcars.model.UsedCarDetailsModel;
import com.androidapps.buyusedcars.viewmodels.SearchResultViewModel;

import java.util.ArrayList;
import java.util.List;


public class AdapterForResult extends BaseAdapter {

    private final int layoutId;
    private SearchResultViewModel searchResultViewModel;
    private List<UsedCarDetailsModel> searchResultList = new ArrayList<>();

    public AdapterForResult(int layoutId, SearchResultViewModel viewModel) {
        this.layoutId = layoutId;
        searchResultViewModel = viewModel;
    }

    @Override
    protected Object getObjForPosition(int position) {
        return searchResultList.get(position);
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    @Override
    protected ViewModel getViewmodel() {
        return searchResultViewModel;
    }

    @Override
    public int getItemCount() {
        return searchResultList == null ? 0 : searchResultList.size();
    }

    public void setSearchResultList(List<UsedCarDetailsModel> searchResultList) {
        this.searchResultList = searchResultList;
    }


}