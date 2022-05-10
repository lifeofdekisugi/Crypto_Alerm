package com.shahir.cryptoalerm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.shahir.cryptoalerm.MainFragments.FragmentAddNewAlerm;
import com.shahir.cryptoalerm.MainFragments.FragmentDashboard;
import com.shahir.cryptoalerm.MainFragments.FragmentPreviousAlerms;
import com.shahir.cryptoalerm.MainFragments.FragmentProfile;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new FragmentDashboard()).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId()) {

                    case R.id.dashboardMenuID:

                        fragment = new FragmentDashboard();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment).commit();
                        return true;

                    case R.id.alermsMenuID:

                        fragment = new FragmentPreviousAlerms();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment).commit();
                        return true;

                    case R.id.addNewMenuID:

                        fragment = new FragmentAddNewAlerm();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment).commit();
                        return true;

                    case R.id.profileMenuID:

                        fragment = new FragmentProfile();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment).commit();

                    default: return true;

                }
                //return false;
            }
        });
    }
}