package com.shahir.cryptoalerm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.shahir.cryptoalerm.Auth.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(SplashScreen.this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());


        int SPLASH_SCREEN = 3500;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (account != null){
                    startActivity(new Intent(SplashScreen.this, HomeActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    finish();
                }


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