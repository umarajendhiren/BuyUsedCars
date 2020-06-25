package com.androidapps.buyusedcars.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.androidapps.buyusedcars.R;
import com.androidapps.buyusedcars.adapter.SectionsPagerAdapter;
import com.androidapps.buyusedcars.viewmodels.MainViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import javax.inject.Inject;

public class SearchFragment extends Fragment {

    @Inject
    MainViewModel mainViewModel;
    private ViewPager2 viewPager;
    private String[] titleName = {"Make And Model", "Body Type", "Saved Search"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tab_search, container, false);


        viewPager = v.findViewById(R.id.pager);

        TabLayout tabs = v.findViewById(R.id.tab_layout);

        setupViewPager(viewPager);

        new TabLayoutMediator(tabs, viewPager,
                (tab, position) -> {
                    tab.setText(titleName[position]);
                }
        ).attach();

        return v;
    }


    private void setupViewPager(ViewPager2 viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getActivity(), getActivity());
        adapter.addFragment(new MakeFragment());
        adapter.addFragment(new BodyTypeFragment());
        adapter.addFragment(new SavedSearchFragment());
        viewPager.setAdapter(adapter);
    }

}
