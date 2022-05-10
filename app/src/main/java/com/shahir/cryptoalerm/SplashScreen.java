package com.shahir.cryptoalerm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.shahir.cryptoalerm.Auth.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        int SPLASH_SCREEN = 3500;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
//                if (CheckNetwork.isInternetAvailable(getApplicationContext())){
//                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
//                    finish();
//                }else{
////            getSupportFragmentManager().beginTransaction().add(R.id.loginActivity, new fragNoInternet()).commit();
//                    Intent intent = new Intent(SplashScreen.this, NoInternet.class);
//                    startActivity(intent);
//                    finish();
//                }
            }
        }, SPLASH_SCREEN);
    }
}