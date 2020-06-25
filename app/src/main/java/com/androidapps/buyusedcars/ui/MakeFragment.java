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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class MakeFragment extends Fragment {


    FragmentMakeBinding binding;
    //we can not initiate list using new List(),because list is interface so we need to initiate using ArrayList() class.

    private List<MakeModel> makeAndModelList = new ArrayList<>();


    /*this is filed injection
     * this annotation tells dagger to provide the instance of this SearchFragmentViewModel from ApplicationGraph*/
    @Inject
    MainViewModel mainViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

//        MyApplication application = new MyApplication();
//        application.appComponent.inject(this);
        ((MyApplication) getActivity().getApplication()).component().inject(this);

        // Make Dagger instantiate @Inject fields in MakeFragment
        // assign singleton instances to fields
        // We need to cast to `MyApplication` in order to get the right method
//        ((MyApplication) getActivity().getApplicationContext()).appComponent.inject(this);

        //now Searchviewmodel is available for this fragment
    }

    public void getMakeLiveData() {

        /*The observer updates the UI whenever the underlying data from the database changes. */


        mainViewModel.getLiveData("CarMakeAndModel").observe(getViewLifecycleOwner(), new Observer<List<MakeModel>>() {
            @Override
            public void onChanged(List<MakeModel> resultMakeList) {

                //for search purpose.
                makeAndModelList.clear();
                makeAndModelList.addAll(resultMakeList);
                mainViewModel.setAdapterForMakeList(resultMakeList);
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_make, container, false);
        binding.setViewmodel(mainViewModel);

        binding.setLifecycleOwner(this);
        View rootView = binding.getRoot();

        getMakeLiveData();

        return rootView;
    }


}