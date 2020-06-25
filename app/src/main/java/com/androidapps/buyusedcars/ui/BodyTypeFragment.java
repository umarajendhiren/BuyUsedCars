package com.androidapps.buyusedcars.ui;

import android.content.Context;
import android.os.Bundle;
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
import com.androidapps.buyusedcars.databinding.FragmentMakeBinding;
import com.androidapps.buyusedcars.model.MakeModel;
import com.androidapps.buyusedcars.viewmodels.MainViewModel;

import java.util.List;

import javax.inject.Inject;


public class BodyTypeFragment extends Fragment {
    @Inject
    MainViewModel mainViewModel;
    FragmentMakeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_make, container, false);
        binding.setViewmodel(mainViewModel);

        binding.setLifecycleOwner(this);
        View rootView = binding.getRoot();
        getBodyType();
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MyApplication) getActivity().getApplication()).component().inject(this);

    }

    private void getBodyType() {

        mainViewModel.getLiveData("BodyType").observe(getViewLifecycleOwner(), new Observer<List<MakeModel>>() {
            @Override
            public void onChanged(List<MakeModel> uploadMakeAndModels) {
                mainViewModel.setAdapterForMakeList(uploadMakeAndModels);
            }
        });
    }
}
