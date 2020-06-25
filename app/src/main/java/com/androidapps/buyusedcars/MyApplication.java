package com.androidapps.buyusedcars;

import android.app.Application;

import com.androidapps.buyusedcars.di.GoogleSignInModule;
import com.androidapps.buyusedcars.di.AppComponent;
import com.androidapps.buyusedcars.di.DaggerAppComponent;
import com.androidapps.buyusedcars.di.FirebaseModule;

public class MyApplication extends Application {

    AppComponent appComponent;
    // appComponent lives in the Application class to share its lifecycle
    // Instance of the AppComponent that will be used by all the Activities in the project
    // Creates an instance of AppComponent using its Factory constructor
    //We pass the applicationContext that will be used as Context in the graph


    // list of modules that are part of this component need to be created here too
     /* DaggerAppComponent.builder()
                .firebaseModule(new FirebaseModule())
                .build()
                .inject(this);*/

    // If a Dagger 2 component does not have any constructor arguments for any of its modules,
    // then we can use .create() as a shortcut instead:
    //  mAppComponent = com.codepath.dagger.components.DaggerAppComponent.create();

    //// Reference to the application graph that is used across the whole app

//       public AppComponent appComponent= DaggerAppComponent.factory().create(this);

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .googleSignInModule(new GoogleSignInModule(this))
                .firebaseModule(new FirebaseModule())
                .build();

        component().inject(this);

    }

    public AppComponent component() {
        return appComponent;
    }
}


