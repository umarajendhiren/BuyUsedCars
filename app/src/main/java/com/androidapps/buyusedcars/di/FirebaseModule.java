package com.androidapps.buyusedcars.di;

import com.androidapps.buyusedcars.model.Filter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/*to get instance of firebase database ,this FirebaseModule need to included in Appcomponent class.*/
@Module
public class FirebaseModule {

    @Singleton
    @Provides
    FirebaseDatabase providesFirebaseDatabase() {

        return FirebaseDatabase.getInstance();
    }

    @Singleton
    @Provides
    FirebaseAuth providesFirebaseAuth() {

        return FirebaseAuth.getInstance();
    }

    @Singleton
    @Provides
    FirebaseFirestore ProvideFirebaseFireStrore() {

        return FirebaseFirestore.getInstance();
    }

    @Singleton
    @Provides
    @Named("usedCarDetailRef")
    CollectionReference provideFirebaseFireStoreCollection() {

        return FirebaseFirestore.getInstance().collection("UsedCarDetails");
    }

    @Singleton
    @Provides
    @Named("firebaseUserRef")
    CollectionReference ProvideUserCollectionReference() {

        return FirebaseFirestore.getInstance().collection("BuyUsedCarUsers");
    }

    @Singleton
    @Provides
    Filter provideFilter() {
        return Filter.getFilterForCarInstance();
    }


    @Singleton
    @Provides
    FirebaseUser provideFirebaseUser() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        return mUser;
    }
}
