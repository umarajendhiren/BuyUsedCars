package com.androidapps.buyusedcars.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.androidapps.buyusedcars.MyApplication;
import com.androidapps.buyusedcars.R;
import com.androidapps.buyusedcars.databinding.ActivitySearchResultBinding;
import com.androidapps.buyusedcars.model.UsedCarDetailsModel;
import com.androidapps.buyusedcars.viewmodels.SearchResultViewModel;

import java.util.List;

import javax.inject.Inject;

public class SearchResultFragment extends Fragment {

    ActivitySearchResultBinding binding;


    @Inject
    SearchResultViewModel searchResultViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_search_result, container, false);
        binding.setViewModel(searchResultViewModel);
        binding.setLifecycleOwner(getActivity());
        View rootView = binding.getRoot();
        getLiveSearchResult();

        return rootView;
    }


    private void getLiveSearchResult() {

        searchResultViewModel.getLiveResult().observe(getViewLifecycleOwner(), new Observer<List<UsedCarDetailsModel>>() {
            @Override
            public void onChanged(List<UsedCarDetailsModel> resultList) {
                Log.d("SearchResultFragment: ", String.valueOf(resultList.size()));
                searchResultViewModel.setAdapterForSearchResult(resultList);
            }

        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        ((MyApplication) getActivity().getApplication()).component().inject(this);
        super.onAttach(context);

    }


}


