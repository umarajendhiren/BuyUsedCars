package com.androidapps.buyusedcars.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.androidapps.buyusedcars.MyApplication;
import com.androidapps.buyusedcars.R;
import com.androidapps.buyusedcars.databinding.ActivityFilterBinding;
import com.androidapps.buyusedcars.databinding.RangePickerDialogBinding;
import com.androidapps.buyusedcars.model.Filter;
import com.androidapps.buyusedcars.viewmodels.SearchResultViewModel;

import javax.inject.Inject;


public class FilterFragment extends Fragment implements View.OnClickListener {

    private TextView tvMake, tvModel, tvMakeAndModel, price, selectedMinimumPrice, selectedMaximumPrice;
    private FragmentContainerView FragmentContainer;
    private Group mainContainerView;
    private Spinner yearSpinner, exteriorColorSpinner, bodyTypeSpinner;
    private String selectedMinimum, selectedMaximum;
    private String[] priceRangeFrom;
    private String[] priceRangeTo;
    private int miniPrice, maxiPrice;
    private int positionOfSelectedMini, positionOfSelectedMaxi;

    @Inject
    SearchResultViewModel searchResultViewModel;


    ActivityFilterBinding binding;
    Filter filter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Obtain binding
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_filter, container, false);
        View v = binding.getRoot();
        filter = Filter.getFilterForCarInstance();
        //bind layout with Filter model.
        binding.setModel(filter);

        //bind layout with viewModel
        binding.setViewModel(searchResultViewModel);

        tvMakeAndModel = binding.filterMake;
        tvMake = binding.selectedMake;
        tvModel = binding.selectedModel;

        price = binding.filterPrice;
        selectedMinimumPrice = binding.SelectedMiniPrice;
        selectedMaximumPrice = binding.SelectedMaxiPrice;


        yearSpinner = binding.filterYear;
        exteriorColorSpinner = binding.filterExteriorColor;
        bodyTypeSpinner = binding.filterBodyType;

        price.setOnClickListener(this);


        FragmentContainer = v.findViewById(R.id.fragment_container);
        mainContainerView = v.findViewById(R.id.group);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((MyApplication) getActivity().getApplication()).component().inject(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.filter_make:

                mainContainerView.setVisibility(View.INVISIBLE);
                startActivityForMakeAndModel();
                break;
            case R.id.filter_price:
                priceSpinnerDialog();
                break;

            case R.id.filter_body_type:
                mainContainerView.setVisibility(View.INVISIBLE);
                startBodyTypeFragment();
                break;
        }

    }

    private void startBodyTypeFragment() {
        FragmentManager fm = getParentFragmentManager();
        BodyTypeFragment bodyTypeFragment = new BodyTypeFragment();
        fm.beginTransaction().add(R.id.fragment_container, bodyTypeFragment).commit();
    }

    private void startActivityForMakeAndModel() {

        FragmentManager fm = getParentFragmentManager();
        SearchFragment searchFragment = new SearchFragment();
        fm.beginTransaction().add(R.id.fragment_container, searchFragment).commit();


    }

    private void priceSpinnerDialog() {

        RangePickerDialogBinding pickerDialogBinding;
        priceRangeFrom = new String[]{"No Minimum", "$5000", "$10000", "$15000", "$20000", "$25000", "$30000", "$35000", "$40000", "$45000", "$50000"};
        pickerDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.range_picker_dialog, null, false);


        priceRangeTo = new String[]{"$5000", "$10000", "$15000", "$20000", "$25000", "$30000", "$35000", "$40000", "$45000", "$50000", "No Maximum"};
        final Dialog pricePicker = new Dialog(getContext());
        pricePicker.setTitle("Price");

//        pricePicker.setContentView(R.layout.range_picker_dialog);
        pricePicker.setContentView(pickerDialogBinding.getRoot());

//     final NumberPicker from = (NumberPicker) pricePicker.findViewById(R.id.minimum_picker);
        final NumberPicker from = pickerDialogBinding.minimumPicker;


        final NumberPicker to = (NumberPicker) pricePicker.findViewById(R.id.maximum_picker);


        from.setDisplayedValues(priceRangeFrom);
        to.setDisplayedValues(priceRangeTo);


        from.setMinValue(0);
        from.setMaxValue(10);
        from.setWrapSelectorWheel(true);
        from.setValue(positionOfSelectedMini);


        to.setMinValue(0);
        to.setMaxValue(10);
        to.setWrapSelectorWheel(true);
        to.setValue(positionOfSelectedMaxi);//set selected value as default


        from.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {

                positionOfSelectedMini = from.getValue();
                //get string from number picker and position
                selectedMinimum = priceRangeFrom[positionOfSelectedMini];

                if (positionOfSelectedMini == 0) {
                    //setPriceMinimu 1 will show  the result which price grater than 1
                    miniPrice = 1;
                } else {

                    miniPrice = Integer.parseInt(selectedMinimum.substring(1));

                    if (positionOfSelectedMaxi < positionOfSelectedMini) {
                        to.setValue(positionOfSelectedMini - 1);
                        positionOfSelectedMaxi = positionOfSelectedMini - 1;
                        selectedMaximum = priceRangeTo[positionOfSelectedMaxi];
                        maxiPrice = Integer.parseInt(selectedMaximum.substring(1));
                    }
                }


            }
        });

        to.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {


                positionOfSelectedMaxi = to.getValue();


                if (positionOfSelectedMaxi == 10) {
                    //setPriceMaxi 50000 will show  the result which price less than 50000.(this is the maximium price)
                    maxiPrice = 50000;


                } else {
                    selectedMaximum = priceRangeTo[positionOfSelectedMaxi];
                    maxiPrice = Integer.parseInt(selectedMaximum.substring(1));

                    if (positionOfSelectedMaxi <= positionOfSelectedMini - 1) {
                        from.setValue(positionOfSelectedMaxi + 1);
                        positionOfSelectedMini = positionOfSelectedMaxi + 1;

                        selectedMinimum = priceRangeFrom[positionOfSelectedMini];
                        miniPrice = Integer.parseInt(selectedMinimum.substring(1));

                    }

                }


            }
        });


        Button cancel = (Button) pricePicker.findViewById(R.id.button_cancel);
        Button done = (Button) pricePicker.findViewById(R.id.button_done);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pricePicker.dismiss();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedMinimumPrice.setText(selectedMinimum + "  to  ");
                selectedMaximumPrice.setText(selectedMaximum);


                filter.setPriceMini(miniPrice);
                filter.setPriceMaxi(maxiPrice);

                searchResultViewModel.setFilter(filter);
                pricePicker.dismiss();
            }
        });
        pricePicker.show();

    }

}


