package com.italo.sistemaead.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.italo.sistemaead.R;
import com.italo.sistemaead.config.ConfigFirebase;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iniApp();

    }

    public void iniApp() {

        auth = ConfigFirebase.getFirebaseAuth();
        Handler handler = new Handler();
        handler.postDelayed(() -> {

            if (auth.getCurrentUser() != null) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            } else {

                Intent i = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        }, 5000);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {

            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        }
    }

    @Override
    public void onBackPressed() {

    }

}