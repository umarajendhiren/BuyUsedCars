package com.androidapps.buyusedcars.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.androidapps.buyusedcars.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        //we need to display home fragment as a default fragment in Homepage activity

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_container, new HomeFragement()).commit();


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:

                            selectedFragment = new HomeFragement();
                            break;

                        case R.id.nav_search:

                            selectedFragment = new SearchFragment();

                            break;

                        case R.id.nav_favorites:
                            selectedFragment = new WishListFragement();

                            break;


                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.content_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}

