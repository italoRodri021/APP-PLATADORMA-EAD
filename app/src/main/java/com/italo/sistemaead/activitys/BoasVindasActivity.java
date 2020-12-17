package com.italo.sistemaead.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.italo.sistemaead.R;

public class BoasVindasActivity extends AppCompatActivity {

    private TextView textnomeUsuario;
    private Button botaoContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boas_vindas);

        iniComponentes();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {

            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        }
    }

    public void iniComponentes() {

        textnomeUsuario = findViewById(R.id.textViewNomeUsuarioCadastrado);
        botaoContinuar = findViewById(R.id.btnContinuarCadastro);

        Intent i = getIntent(); // -> Recuperando nome do usuario cadastrado da CadastroActivity
        String nome = i.getStringExtra("NOME_USUARIO_CADASTRADO");

        textnomeUsuario.setText(nome);// -> COnfigurando textView

        botaoContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}