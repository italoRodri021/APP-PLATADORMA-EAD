package com.italo.sistemaead.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.italo.sistemaead.R;

public class WelcomeActivity extends AppCompatActivity {

    private TextView textNameUser;
    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        iniComponentes();
    }

    public void iniComponentes() {

        textNameUser = findViewById(R.id.textViewNomeUsuarioCadastrado);
        btnContinue = findViewById(R.id.btnContinuarCadastro);

        Intent i = getIntent(); // -> Recuperando nome do usuario cadastrado da CadastroActivity
        String nameUser = i.getStringExtra("NAME_USER");

        textNameUser.setText(nameUser);// -> COnfigurando textView

        btnContinue.setOnClickListener(view -> {

            Intent intro = new Intent(getApplicationContext(), IntroActivity.class);
            startActivity(intro);
            finish();
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        });

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