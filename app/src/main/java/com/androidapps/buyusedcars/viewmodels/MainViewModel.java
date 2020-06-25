package com.androidapps.buyusedcars.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.androidapps.buyusedcars.R;
import com.androidapps.buyusedcars.adapter.MakeAdapter;
import com.androidapps.buyusedcars.model.MakeModel;
import com.androidapps.buyusedcars.model.User;
import com.androidapps.buyusedcars.repository.MainRepository;
import com.androidapps.buyusedcars.ui.LatestModelActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;

public class MainViewModel extends ViewModel {


    private final MediatorLiveData<List<MakeModel>> makeLiveData = new MediatorLiveData<>();


    private MakeModel makeAndModel;
    private List<MakeModel> makeAndModelList = new ArrayList<>();
    private MainRepository repository;
    public LiveData<User> authenticatedUserLiveData;
    private GoogleSignInClient signInClient;
    private MakeAdapter makeAdapter;
    private Context context;

    @Inject
    MainViewModel(MainRepository repository, @Named("application.context") Context context) {
        this.repository = repository;
        makeAdapter = new MakeAdapter(R.layout.recycle_listitem_make, this);
        signInClient = repository.getSignInClient();
        authenticatedUserLiveData = repository.firebaseSignInWithGoogle(null);
        this.context = context;

    }

    public GoogleSignInClient getSignInClient() {
        return signInClient;
    }

    public LiveData<DataSnapshot> getLiveDataSnap(String dbRef) {

        return repository.getDataSnapshotLiveData(dbRef);
    }


    public MediatorLiveData<List<MakeModel>> getLiveData(String dbRef) {
        liveDataSnapToObj(dbRef);
        return makeLiveData;
    }


    /*MediatorLiveData is built on top of a map transform,
     and allows us to observe changes other LiveData sources, deciding what to do with each event.
     *
     */

    public void liveDataSnapToObj(String dbRef) {


        // Set up the MediatorLiveData to convert DataSnapshot objects into HotStock objects


        makeLiveData.addSource(getLiveDataSnap(dbRef), new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                makeAndModelList.clear();
                if (dataSnapshot != null) {

                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//            The Realtime Database SDK makes it really easy to convert a DataSnapshot into a JavaBean style object.
                                String key = postSnapshot.getKey();
//                    store snapshot value in UploadMakeAndModel object
                                makeAndModel = postSnapshot.getValue(MakeModel.class);
                                makeAndModel.setDocumentKey(key);

                                //   add those object value in list.
                                makeAndModelList.add(makeAndModel);


                            }


                            makeLiveData.postValue(makeAndModelList);


                        }
                    });
                }
            }
        });

    }

    public void signInWithGoogle(AuthCredential googleAuthCredential) {
        authenticatedUserLiveData = repository.firebaseSignInWithGoogle(googleAuthCredential);
    }


    public void signOut() {
        repository.signOut();
    }

    public void OnClickMake(String selectedmake) {
        Log.d("OnClickMake: ", selectedmake);


        Intent intent = new Intent(context, LatestModelActivity.class);
        intent.putExtra("brand", selectedmake);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    public void setAdapterForMakeList(List<MakeModel> resultMakeList) {
        this.makeAdapter.setMakeList(resultMakeList);
        this.makeAdapter.notifyDataSetChanged();

    }

    public MakeAdapter getMakeAdapter() {
        return makeAdapter;
    }


    public TextWatcher textWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing.
            }
        };
    }

    public void filter(String text) {


        ArrayList<MakeModel> filteredList = new ArrayList<>();

        //makeAndModelList is the items displayed in recyclerview
        for (MakeModel item : makeAndModelList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {

                //add the item to the arraylist which contains search text
                filteredList.add(item);
            }
        }

        //send the filtered value to the adapter to display in recyclerview
        makeAdapter.filterList(filteredList);

    }
}
