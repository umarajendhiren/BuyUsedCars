package com.androidapps.buyusedcars.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidapps.buyusedcars.model.Filter;
import com.androidapps.buyusedcars.model.UsedCarDetailsModel;
import com.androidapps.buyusedcars.model.User;
import com.androidapps.buyusedcars.utilities.FirebaseDatabaseLiveData;
import com.androidapps.buyusedcars.utilities.QueryLiveData;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import javax.inject.Inject;
import javax.inject.Named;

public final class MainRepository {

    private final FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private final CollectionReference carDetailCollectionRef;
    private final CollectionReference userCollectionRef;
    CollectionReference userWishListCollectionRef;
    DocumentReference userWishListDocumentRef;

    private FirebaseDatabaseLiveData databaseLiveData;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String firebaseUserUid;

    public static MutableLiveData<User> authenticatedUserMutableLiveData = new MutableLiveData<>();
    private GoogleSignInClient signInClient;
    public static MutableLiveData<UsedCarDetailsModel> wishListDocumentSnapShot = new MutableLiveData<>();
    public static MutableLiveData<UsedCarDetailsModel> moreDetailDocumentSnapShot = new MutableLiveData<>();


    private Query query;
    private Context context;
    boolean changeStatus;


    @Inject
    public MainRepository(@Named("application.context") Context applicationContext, FirebaseAuth auth, FirebaseDatabase database, FirebaseFirestore firebaseFirestore, @Named("usedCarDetailRef") CollectionReference collectionReference, @Named("firebaseUserRef") CollectionReference uerCollectionReference, GoogleSignInClient signInClient, FirebaseUser firebaseUser) {

        this.firebaseDatabase = database;
        this.carDetailCollectionRef = collectionReference;
        this.userCollectionRef = uerCollectionReference;
        this.signInClient = signInClient;
        this.firebaseAuth = auth;
        this.context = applicationContext;
        this.firebaseUser = firebaseUser;
        getCurrentFirebaseUser();
        userWishListCollectionRef = userCollectionRef.document(firebaseUserUid).collection("WishList");
    }

    public MutableLiveData<UsedCarDetailsModel> getWishListDocumentSnapShot() {

        return wishListDocumentSnapShot;
    }

    public MutableLiveData<UsedCarDetailsModel> getMoreDetailDocumentSnapShot() {

        return moreDetailDocumentSnapShot;
    }
    public MutableLiveData<User> getLoggedInUserLiveData() {

        return authenticatedUserMutableLiveData;
    }

    public Context getContext() {
        return context;
    }

    public GoogleSignInClient getSignInClient() {
        return signInClient;
    }

    public LiveData<DataSnapshot> getDataSnapshotLiveData(String dbRef) {

        this.databaseReference = firebaseDatabase.getReference(dbRef);

        databaseLiveData = new FirebaseDatabaseLiveData(databaseReference);

        return databaseLiveData;
    }


    public QueryLiveData queryLiveData(final Filter filter) {
        return new QueryLiveData(toQuery(filter));
    }


    private Query toQuery(final Filter filters) {
        // Construct query basic query
        query = carDetailCollectionRef;

        if (filters.hasMake()) {

            Log.d("getFilter: ", filters.getMake());

            query = query.whereEqualTo(UsedCarDetailsModel.FIELD_MAKE, filters.getMake());
        }

        if (filters.hasModel()) {

            Log.d("getFilter: ", filters.getModel());

            query = query.whereEqualTo(UsedCarDetailsModel.FIELD_MODEL, filters.getModel());
        }
        if (filters.hasMiniPrice()) {

            Log.d("getFilter: ", String.valueOf(filters.getPriceMini()));

            query = query.whereGreaterThanOrEqualTo(UsedCarDetailsModel.FIELD_PRICE, filters.getPriceMini());

        }

        if (filters.hasMaxiPrice()) {

            Log.d("getFilter: ", String.valueOf(filters.getPriceMaxi()));

            query = query.whereLessThanOrEqualTo(UsedCarDetailsModel.FIELD_PRICE, filters.getPriceMaxi());
        }
        if (filters.hasYear()) {

            Log.d("getFilter: ", String.valueOf(filters.getYear()));

            query = query.whereEqualTo(UsedCarDetailsModel.FIELD_YEAR, filters.getYear());
        }
        if (filters.hasExteriorColor()) {

            Log.d("getFilter: ", String.valueOf(filters.getExteriorColor()));
            query = query.whereEqualTo(UsedCarDetailsModel.FIELD_COLOR, filters.getExteriorColor());

            Log.d("QueryIs: ", String.valueOf(query));
        }

        if (filters.hasBodyType()) {

            Log.d("getFilter: ", String.valueOf(filters.getBodyType()));
            query = query.whereEqualTo(UsedCarDetailsModel.FIELD_BODY, filters.getBodyType());

            Log.d("QueryIs: ", String.valueOf(query));
        }

        return query;
    }


    public MutableLiveData<User> firebaseSignInWithGoogle(AuthCredential googleAuthCredential) {
        if (googleAuthCredential != null) {
            firebaseAuth.signInWithCredential(googleAuthCredential).addOnCompleteListener(authTask -> {
                if (authTask.isSuccessful()) {

                    boolean isNewUser = authTask.getResult().getAdditionalUserInfo().isNewUser();

                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        String uid = firebaseUser.getUid();
                        String name = firebaseUser.getDisplayName();
                        String email = firebaseUser.getEmail();
                        User user = new User(uid, name, email);
                        user.isNew = isNewUser;
                        user.isAuthenticated = true;
                        authenticatedUserMutableLiveData.setValue(user);
                        Log.e("firebaseSignIn", "SignInSucess");
                    }
                } else {
                    Log.e("firebaseSignIn", authTask.getException().getMessage());
                }
            });

        }
        return authenticatedUserMutableLiveData;
    }


    public void addToWishList(String documentKey) {
        Log.d("uid: ", firebaseUserUid);
        carDetailCollectionRef.document(documentKey).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                UsedCarDetailsModel model = documentSnapshot.toObject(UsedCarDetailsModel.class);
                userWishListCollectionRef.document(documentKey).set(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Added to wishlist", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }


    public void checkHeart(String documentId, boolean isHeartChecked) {
        getWishStatus(isHeartChecked);
        carDetailCollectionRef.document(documentId).update("wish", changeStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("onComplete:Success ", "updated to " + changeStatus);
                    if (changeStatus) {
                        //if the status is true ,we need do add document to the wishlist under user
                        addToWishList(documentId);
                    } else {
                        // if the status is false ,we need do remove document from the wishlist under user
                        removeFromWishList(documentId);
                    }
                } else Log.d("onComplete: exception", task.getException().getMessage());
            }
        });
    }


    public boolean getWishStatus(boolean isHeartChecked) {
        if (firebaseUserUid != null) {

            Log.d("CheckHeart: ", String.valueOf(isHeartChecked));
            if (isHeartChecked) {
                changeStatus = false;
            } else {
                changeStatus = true;
            }

        } else Log.d("checkHeart: ", "can not get Uid of Firebase user");
        return changeStatus;
    }

    private void removeFromWishList(String documentKey) {

        Log.d("uid: ", firebaseUserUid);

        userWishListDocumentRef = userWishListCollectionRef.document(documentKey);

        userWishListDocumentRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Removed from wishlist", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void signOut() {

        //log out from google,not from firebase,so that we can use uid of user for wish list.
        signInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("signOut", "Logged out from google sign in");

                    authenticatedUserMutableLiveData.setValue(null);
                } else Log.e("onComplete: ", task.getException().getMessage());
            }
        });

    }

    public String getCurrentFirebaseUser() {
        //even though user signed out,if user already signed in ,we can get firebse user uid.
        firebaseUserUid = firebaseUser.getUid();
        Log.d("getCurrentFirebaseUser", firebaseUserUid);
        return firebaseUserUid;

    }

    public QueryLiveData WishListQueryLiveData() {
        return new QueryLiveData(userWishListCollectionRef);
    }

    public LiveData<UsedCarDetailsModel> getDocumentFromWishList(String documentkey) {
        DocumentReference documentReference = userWishListCollectionRef.document(documentkey);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UsedCarDetailsModel wishList = documentSnapshot.toObject(UsedCarDetailsModel.class);
                wishListDocumentSnapShot.setValue(wishList);
            }
        });

        return wishListDocumentSnapShot;
    }

    public LiveData<UsedCarDetailsModel> getDocumentForMoreDetail(String documentkey) {
        DocumentReference documentReference = carDetailCollectionRef.document(documentkey);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UsedCarDetailsModel moreDetail = documentSnapshot.toObject(UsedCarDetailsModel.class);
                moreDetailDocumentSnapShot.setValue(moreDetail);
            }
        });

        return moreDetailDocumentSnapShot;
    }
}














