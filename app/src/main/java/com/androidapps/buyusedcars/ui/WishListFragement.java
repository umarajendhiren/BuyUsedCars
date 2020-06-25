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
import com.androidapps.buyusedcars.databinding.FragmentWishlistBinding;
import com.androidapps.buyusedcars.model.UsedCarDetailsModel;
import com.androidapps.buyusedcars.model.User;
import com.androidapps.buyusedcars.viewmodels.WishListViewModel;

import java.util.List;

import javax.inject.Inject;


public class WishListFragement extends Fragment {

    FragmentWishlistBinding binding;
    @Inject
    WishListViewModel wishListViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wishlist, container, false);
        binding.setViewmodel(wishListViewModel);

        binding.setLifecycleOwner(this);
        View view = binding.getRoot();

        isUserLoggedIn();

        return view;
    }

    public void isUserLoggedIn() {
        wishListViewModel.isUserLoggedIn().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    Log.d("onChanged: ", String.valueOf(user.isAuthenticated));
                    if (user.isAuthenticated) {
                        setUpResultList();
                    }
                } else {

                    Log.d("onChanged: ", "Please login to view wishlist");
                    wishListViewModel.tvLogin.set(View.VISIBLE);


                }
            }
        });
    }

    private void setUpResultList() {
        wishListViewModel.loading.set(View.VISIBLE);
        wishListViewModel.getWishList().observe(getViewLifecycleOwner(), new Observer<List<UsedCarDetailsModel>>() {
            @Override
            public void onChanged(List<UsedCarDetailsModel> modelList) {
                List<UsedCarDetailsModel> wishList = modelList;


                wishListViewModel.loading.set(View.GONE);
                if (wishList.size() == 0) {
                    wishListViewModel.showEmpty.set(View.VISIBLE);
                    wishListViewModel.recyclerViewVisibility.set(View.GONE);

                } else {
                    wishListViewModel.showEmpty.set(View.GONE);
                    wishListViewModel.recyclerViewVisibility.set(View.VISIBLE);
                    wishListViewModel.setAdapterForWishList(wishList);
                }
            }
        });
    }


    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MyApplication) getActivity().getApplication()).component().inject(this);

    }
}



