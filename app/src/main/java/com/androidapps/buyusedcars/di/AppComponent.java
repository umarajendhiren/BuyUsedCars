package com.androidapps.buyusedcars.di;


import com.androidapps.buyusedcars.MyApplication;
import com.androidapps.buyusedcars.ui.BodyTypeFragment;
import com.androidapps.buyusedcars.ui.FilterFragment;
import com.androidapps.buyusedcars.ui.HomeFragement;
import com.androidapps.buyusedcars.ui.LatestModelActivity;
import com.androidapps.buyusedcars.ui.MakeFragment;
import com.androidapps.buyusedcars.ui.MoreDetailActivity;
import com.androidapps.buyusedcars.ui.SearchResultFragment;
import com.androidapps.buyusedcars.ui.SearchResultWithViewPager;
import com.androidapps.buyusedcars.ui.WishListFragement;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {FirebaseModule.class, GoogleSignInModule.class})


public interface AppComponent {
    /*Dagger needs to know that which activity has to access the graph in order to provide the ViewModel it requires*/

    /*This function tells Dagger that below Fragment wants to access the graph(to get viewmodel instance) and requests injection.
     Dagger needs to satisfy all the dependencies that fragment requires*/
    void inject(MakeFragment makeFragment);

    void inject(BodyTypeFragment bodyTypeFragment);

    void inject(LatestModelActivity latestModelActivity);

    void inject(SearchResultFragment searchResultFragment);

    void inject(SearchResultWithViewPager searchResultWithViewPager);

    void inject(FilterFragment filterFragment);

    void inject(HomeFragement homeFragement);

    void inject(WishListFragement wishListFragement);

    void inject(MoreDetailActivity moreDetailActivity);

    void inject(MyApplication application);

    // Factory to create instances of the AppComponent
   /* @Component.Factory
    interface Factory {*/
    // With @BindsInstance, the Context passed in will be available in the graph
    //when ever the context needed by dagger,it will get it from graph.
//      The instance of Context is passed in the AppComponent's factory create method.
//      Therefore, we'll have the same instance provided anytime an object needs Context.
    //we also want to have the application Context available in the graph.
    // As advantages, the graph is available to other Android framework classes (that can access with their Context) and it's also good for testing since you can use a custom Application class in tests.

//      AppComponent create( @BindsInstance Context context);


}




