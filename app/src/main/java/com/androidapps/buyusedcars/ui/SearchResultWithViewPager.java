package com.androidapps.buyusedcars.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.androidapps.buyusedcars.MyApplication;
import com.androidapps.buyusedcars.R;
import com.androidapps.buyusedcars.adapter.SectionsPagerAdapter;
import com.androidapps.buyusedcars.model.Filter;
import com.androidapps.buyusedcars.viewmodels.SearchResultViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import javax.inject.Inject;


public class SearchResultWithViewPager extends AppCompatActivity {
    @Inject
    SearchResultViewModel searchResultViewModel;

    Filter filter;

    ViewPager2 viewPager;
    String selectedMake, selectedModel;
    FrameLayout frameLayout;

    String[] titleName = {"Search Result", "Filter", "New Search"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ((MyApplication) getApplication()).component().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_search);


        if (getIntent().getExtras() != null) {

            selectedMake = getIntent().getExtras().getString("make");

            selectedModel = getIntent().getExtras().getString("model");
            Log.d("IntentData: ", selectedMake + "\n" + selectedModel);
        }
        viewPager = findViewById(R.id.pager);
        frameLayout = findViewById(R.id.fragment_container);
        TabLayout tabs = findViewById(R.id.tab_layout);

        setupViewPager(viewPager);

        new TabLayoutMediator(tabs, viewPager,
                (tab, position) -> {
//                    tab.setText("OBJECT " + (position + 1));
                    tab.setText(titleName[position]);

                }
        ).attach();


        filter = Filter.getFilterForCarInstance();
        searchResultViewModel.setFilter(null);

        filter.setMake(selectedMake);
        filter.setModel(selectedModel);
        searchResultViewModel.setFilter(filter);


    }


    private void setupViewPager(ViewPager2 viewPager) {

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(this, this);
        adapter.addFragment(new SearchResultFragment());
        adapter.addFragment(new FilterFragment());
        adapter.addFragment(new MakeFragment());
        viewPager.setAdapter(adapter);


    }
}



