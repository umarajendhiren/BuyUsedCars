package com.androidapps.buyusedcars.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.androidapps.buyusedcars.R;
import com.androidapps.buyusedcars.adapter.AdapterForResult;
import com.androidapps.buyusedcars.model.Filter;
import com.androidapps.buyusedcars.model.UsedCarDetailsModel;
import com.androidapps.buyusedcars.repository.MainRepository;
import com.androidapps.buyusedcars.ui.MoreDetailActivity;

import java.util.List;

import javax.inject.Inject;

public class SearchResultViewModel extends ViewModel {
    public AdapterForResult adapter;
    public final ObservableInt emptyresultVisibility;
    public final ObservableInt recyclerViewVisibility;
    private final MainRepository repository;
    public AdapterView.OnItemSelectedListener itemClickListener;
    private static MutableLiveData<Filter> filter = new MutableLiveData<>();
    private final LiveData<List<UsedCarDetailsModel>> result;
    private Filter filterReference;
    Context context;

    @Inject
    public SearchResultViewModel(MainRepository mainRepository) {
        emptyresultVisibility = new ObservableInt(View.GONE);
        recyclerViewVisibility = new ObservableInt(View.VISIBLE);
        this.repository = mainRepository;
        context = mainRepository.getContext();
        result = Transformations.switchMap(filter, repository::queryLiveData);
        onClickSpinner();
        filterReference = Filter.getFilterForCarInstance();
        adapter = new AdapterForResult(R.layout.view_search_result, this);

    }


    public LiveData<List<UsedCarDetailsModel>> getLiveResult() {

        return result;

    }

    public MutableLiveData<Filter> getFilter() {
        return filter;

    }


    public void setFilter(final Filter filter) {
        if (filter == null) {
            return;
        }

        this.filter.setValue(filter);


    }


    public void onClickSpinner() {

        itemClickListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("onItemSelected: ", String.valueOf(adapterView.getSelectedItem()));
                int selectedSpinnerItem = adapterView.getId();
                String selectedItem = (String) adapterView.getSelectedItem();

                switch (selectedSpinnerItem) {


                    case R.id.filter_year:

                        Log.d("selectedSpinnerItem: ", String.valueOf(adapterView.getSelectedItem()));
                        if (selectedItem.equals("Select Year")) {

                            filterReference.setYear(0);
                            filter.setValue(filterReference);
                            return;
                        }
                        filterReference.setYear(Integer.parseInt(selectedItem));

                        break;

                    case R.id.filter_exterior_color:
                        Log.d("selectedSpinnerItem: ", String.valueOf(adapterView.getSelectedItem()));
                        if (selectedItem.equals("Select a Exterior Color")) {
                            filterReference.setExteriorColor(null);
                            filter.setValue(filterReference);
                            return;
                        }

                        filterReference.setExteriorColor(selectedItem);
                        break;

                    case R.id.filter_body_type:
                        Log.d("selectedSpinnerItem: ", String.valueOf(adapterView.getSelectedItem()));

                        if (selectedItem.equals("Select Body Type")) {

                            filterReference.setBodyType(null);
                            filter.setValue(filterReference);
                            return;
                        }

                        filterReference.setBodyType(selectedItem);
                        break;


                }

                filter.setValue(filterReference);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

    }

    public void setWishList(boolean isHeartChecked, String documentKey) {

        repository.checkHeart(documentKey, isHeartChecked);

    }

    public void setAdapterForSearchResult(List<UsedCarDetailsModel> searchResult) {

        if (searchResult.size() > 0) {

            recyclerViewVisibility.set(View.VISIBLE);
            emptyresultVisibility.set(View.GONE);

            adapter.setSearchResultList(searchResult);
            adapter.notifyDataSetChanged();
        } else {
            recyclerViewVisibility.set(View.GONE);
            emptyresultVisibility.set(View.VISIBLE);


        }


    }


    public AdapterForResult getResultAdapter() {
        return adapter;
    }

    public void onClickMoreDetail(String dockey) {

        Log.d("onClickMoreDetail: ", dockey);

        repository.getDocumentForMoreDetail(dockey);

        Intent intent = new Intent(context, MoreDetailActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}







