package com.shahir.cryptoalerm.Auth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.shahir.cryptoalerm.HomeActivity;
import com.shahir.cryptoalerm.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLoginWithGoogle;
    SignInButton signInButton;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signInButton = findViewById(R.id.signInButton);
        signInButton.setSize(signInButton.SIZE_STANDARD);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(LoginActivity.this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRequest();
            }
        });

    }



    private void loginRequest() {

        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent, 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 ){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                homeActivity();
                task.getResult(ApiException.class);
            } catch (ApiException e) {
                Toast.makeText(this, "    Error    ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void homeActivity() {
        finish();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}

/*
    <Button
        android:id="@+id/btnLoginWithGoogle"
        android:layout_width="match_parent"
        android:layout_height="75dp"
        android:layout_marginStart="30dp"
        android:layout_marginEnd="30dp"
        android:padding="10dp"
        android:textColor="@color/black"
        android:elevation="10dp"
        android:backgroundTint="@color/white"
        android:text="Login With Google"
        android:textSize="18sp"
        android:drawableLeft="@drawable/googleicon"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.83" />
 */