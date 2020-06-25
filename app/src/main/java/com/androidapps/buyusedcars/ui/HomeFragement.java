package com.androidapps.buyusedcars.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.androidapps.buyusedcars.MyApplication;
import com.androidapps.buyusedcars.R;
import com.androidapps.buyusedcars.databinding.FragmentHomeBinding;
import com.androidapps.buyusedcars.model.User;
import com.androidapps.buyusedcars.viewmodels.MainViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;


public class HomeFragement extends Fragment implements View.OnClickListener {
    private static final int RC_SIGN_IN = 123;
    private FragmentHomeBinding binding;
    @Inject
    MainViewModel mainViewModel;
    private GoogleSignInClient googleSignInClient;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.signInButton.setOnClickListener(this);
        binding.signOut.setOnClickListener(this);
        View v = binding.getRoot();
        binding.setLifecycleOwner(this);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MyApplication) getActivity().getApplication().getApplicationContext()).component().inject(this);

        mainViewModel.authenticatedUserLiveData.observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    if (user.isAuthenticated)
                        updateUI(user);
                } else {

                    Log.d("signInWithGoogleCreden", "Not Authenticated");
                    updateUI(null);
                }
            }


        });
    }

    private void signIn() {
        googleSignInClient = mainViewModel.getSignInClient();
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {


            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);


            try {
                GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                if (googleSignInAccount != null) {
                    getGoogleAuthCredential(googleSignInAccount);
                }
            } catch (ApiException e) {

                Log.e("onActivityResult: ", e.getMessage());
            }
        }
    }


    private void getGoogleAuthCredential(GoogleSignInAccount googleSignInAccount) {
        String googleTokenId = googleSignInAccount.getIdToken();
        AuthCredential googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null);
        signInWithGoogleAuthCredential(googleAuthCredential);
    }

    private void signInWithGoogleAuthCredential(AuthCredential googleAuthCredential) {
        mainViewModel.signInWithGoogle(googleAuthCredential);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.sign_in_button:
                signIn();
                break;

            case R.id.sign_out:
                signOut();
                break;

        }
    }

    private void signOut() {
        mainViewModel.signOut();

    }


    private void updateUI(User user) {
        if (user != null) {


            String userName = user.name;

            binding.SignInGroup.setVisibility(View.GONE);
            binding.afterSignInGroup.setVisibility(View.VISIBLE);
            binding.userName.setText("Welcome  ".concat(userName));

        } else {
            binding.SignInGroup.setVisibility(View.VISIBLE);
            binding.afterSignInGroup.setVisibility(View.GONE);
        }
    }
}