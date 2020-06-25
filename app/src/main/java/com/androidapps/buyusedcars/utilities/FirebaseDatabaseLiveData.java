package com.androidapps.buyusedcars.utilities;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/*LiveData is an observable data holder class.
 *  I'll use it here to listen to changes to a database Query or DatabaseReference (note that a DatabaseReference itself is a Query).
 * notify the data changes to an observing Activity of those changes so it can update its UI.
 *  These notifications come in the form of DataSnapshot objects.
 * disadvantage of this class is,if the configuration changes,each time listener removes and re-adds.
 * Each re-add of the listener effectively requires another round trip with the server to fetch the data again,
 *  and I'd rather not do that, in order to avoid consuming the user's limited mobile data. */
public class FirebaseDatabaseLiveData extends LiveData<DataSnapshot> {
    private static final String LOG_TAG = "FirebaseQueryLiveData";


    private final Query query;

    private final MyValueEventListener listener = new MyValueEventListener();

    public FirebaseDatabaseLiveData(Query query) {
        this.query = query;
    }

    public FirebaseDatabaseLiveData(DatabaseReference ref) {
        Log.d("FirebaseDatabaseRef", String.valueOf(ref));
        this.query = ref;
    }

    @Override
    protected void onActive() {
        Log.d(LOG_TAG, "onActive");
        query.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(LOG_TAG, "onInactive");
        query.removeEventListener(listener);
    }


    /*whenever the query or database reference changes ,MyValueEventListener triggers with a new DataSnapshot.
     * And it notifies any observers of that using the setValue() method on LiveData.
     * whenever the Activity or Fragment associated with this LiveData object is on screen (in the STARTED or RESUMED state),
     * the LiveData object is "active", and the database listener will be added*/
    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            setValue(dataSnapshot);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(LOG_TAG, "Can't listen to query " + query, databaseError.toException());
        }
    }
}

