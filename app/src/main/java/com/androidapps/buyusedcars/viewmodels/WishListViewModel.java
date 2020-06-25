package com.androidapps.buyusedcars.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidapps.buyusedcars.R;
import com.androidapps.buyusedcars.adapter.WishListAdapter;
import com.androidapps.buyusedcars.model.UsedCarDetailsModel;
import com.androidapps.buyusedcars.model.User;
import com.androidapps.buyusedcars.repository.MainRepository;
import com.androidapps.buyusedcars.ui.MoreDetailActivity;

import java.util.List;

import javax.inject.Inject;

public class WishListViewModel extends ViewModel {
    public final ObservableInt recyclerViewVisibility;
    private WishListAdapter adapter;
    public final ObservableInt loading;
    public final ObservableInt showEmpty;
    private MainRepository repository;
    public final ObservableInt tvLogin;
    private Context context;
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private boolean isLoggedIn;

    @Inject
    WishListViewModel(MainRepository repository) {
        context = repository.getContext();
        this.repository = repository;
        adapter = new WishListAdapter(R.layout.view_wish_list, this);
        loading = new ObservableInt(View.GONE);
        recyclerViewVisibility = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
        tvLogin = new ObservableInt(View.GONE);
        getLoginStatus();
    }

    public void getLoginStatus() {

        userMutableLiveData = repository.getLoggedInUserLiveData();
        if (userMutableLiveData.getValue() != null) {
            isLoggedIn = userMutableLiveData.getValue().isAuthenticated;

            Log.d("getLoginStatus: ", String.valueOf(isLoggedIn));

            if (isLoggedIn) {
                tvLogin.set(View.GONE);
            }
        } else
            tvLogin.set(View.VISIBLE);
    }

    public WishListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapterForWishList(List<UsedCarDetailsModel> usedCarDetailsModels) {
        this.adapter.setCarDetailsList(usedCarDetailsModels);
        this.adapter.notifyDataSetChanged();
    }

    public LiveData<List<UsedCarDetailsModel>> getWishList() {
        return repository.WishListQueryLiveData();
    }

    public void onItemClick(String documentkey) {

        Log.d("onItemClick: ", documentkey);

        repository.getDocumentFromWishList(documentkey);

        Intent intent = new Intent(context, MoreDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public LiveData<User> isUserLoggedIn() {
        return repository.getLoggedInUserLiveData();

    }


}



