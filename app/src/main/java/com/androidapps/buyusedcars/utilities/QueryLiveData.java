package com.androidapps.buyusedcars.utilities;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.androidapps.buyusedcars.model.UsedCarDetailsModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public final class QueryLiveData
        extends LiveData implements EventListener<QuerySnapshot> {

    private final Query query;


    public QueryLiveData(Query query) {

        this.query = query;

    }

    public QueryLiveData(CollectionReference collectionReference) {
        this.query = collectionReference;

    }

    @Override
    public void onEvent(QuerySnapshot snapshots, FirebaseFirestoreException e) {
        if (e != null) {
            Log.e("onEvent: ", e.getMessage());
            setValue(snapshots);
            return;
        }
        setValue((documentToList(snapshots)));

    }


    @Override
    protected void onActive() {
        super.onActive();
        query.addSnapshotListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();

    }


    private List<UsedCarDetailsModel> documentToList(QuerySnapshot snapshots) {
        final List<UsedCarDetailsModel> retList = new ArrayList<>();
        if (snapshots.isEmpty()) {
            Log.d("documentToList: ", String.valueOf(retList.size()));
            return retList;
        }

        for (DocumentSnapshot document : snapshots.getDocuments()) {
            String documentkey = document.getId();
            UsedCarDetailsModel usedCarDetailsModel = document.toObject(UsedCarDetailsModel.class);
            usedCarDetailsModel.setDocumentKey(documentkey);
            retList.add(usedCarDetailsModel);

        }
        Log.d("documentToList: ", String.valueOf(retList.size()));
        return retList;
    }
}
