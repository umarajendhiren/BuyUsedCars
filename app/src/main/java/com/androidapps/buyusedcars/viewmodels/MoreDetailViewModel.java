package com.androidapps.buyusedcars.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapps.buyusedcars.model.UsedCarDetailsModel;
import com.androidapps.buyusedcars.repository.MainRepository;

import javax.inject.Inject;

public class MoreDetailViewModel extends ViewModel {
    MainRepository repository;

    @Inject
    MoreDetailViewModel(MainRepository repository) {
        this.repository = repository;
    }


    public LiveData<UsedCarDetailsModel> getMoreDetailSnapShot() {
        return repository.getMoreDetailDocumentSnapShot();
    }
}
