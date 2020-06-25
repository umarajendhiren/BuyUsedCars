package com.androidapps.buyusedcars.model;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.Exclude;

public class User extends LiveData {
    public String uid;
    public String name;
    @SuppressWarnings("WeakerAccess")
    public String email;
    @Exclude
    public boolean isAuthenticated;

    @Exclude
    public boolean isNew;

    public User() {
    }

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }


}

