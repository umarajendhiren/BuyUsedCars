package com.androidapps.buyusedcars.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.androidapps.buyusedcars.MyApplication;
import com.androidapps.buyusedcars.R;
import com.androidapps.buyusedcars.databinding.ActivityMoreDetailBinding;
import com.androidapps.buyusedcars.model.UsedCarDetailsModel;
import com.androidapps.buyusedcars.viewmodels.MoreDetailViewModel;

import javax.inject.Inject;

public class MoreDetailActivity extends AppCompatActivity {
    ActivityMoreDetailBinding binding;
    @Inject
    MoreDetailViewModel moreDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((MyApplication) getApplication()).component().inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_more_detail);
        moreDetailViewModel.getMoreDetailSnapShot().observe(this, new Observer<UsedCarDetailsModel>() {
            @Override
            public void onChanged(UsedCarDetailsModel usedCarDetailsModel) {
                binding.setModel(usedCarDetailsModel);
            }
        });
    }
}
