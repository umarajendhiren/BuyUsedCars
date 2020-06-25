package com.androidapps.buyusedcars.di;

import android.content.Context;

import com.androidapps.buyusedcars.MyApplication;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GoogleSignInModule {

    /**
     * A module for Android-specific dependencies which require a {@link Context} or
     * {@link android.app.Application} to create.
     */

    private final MyApplication application;

    public GoogleSignInModule(MyApplication application) {
        this.application = application;
    }

    /**
     * Allow the application context to be injected but require that it be annotated with
     *
     * @Named("application.context") to explicitly differentiate it from an activity context.
     */
    @Provides
    @Singleton
    @Named("application.context")
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    GoogleSignInClient provideGoogleSignInClient(@Named("application.context") Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("757339392912-qrns2bji8pgfpmtb0igorhqi9uqfhbd4.apps.googleusercontent.com")
                .requestProfile()
                .requestEmail()
                .build();


        return GoogleSignIn.getClient(context, gso);
    }
}
