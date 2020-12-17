package com.italo.sistemaead.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.italo.sistemaead.R;
import com.italo.sistemaead.fragments.CadastroFragment;
import com.italo.sistemaead.fragments.LoginFragment;

public class InicioActivity extends AppCompatActivity {

    private LoginFragment loginFrag;
    private CadastroFragment cadastroFrag;
    private Button botaoContinuar, botaoCadastro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        botaoContinuar = findViewById(R.id.btnContinuarIniciar);
        botaoCadastro = findViewById(R.id.btnFrazerCadastro);


        botaoContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginFrag = new LoginFragment();

                FragmentTransaction login = getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.popenter, R.anim.popexit)
                        .replace(R.id.frameConteudo, loginFrag)
                        .addToBackStack(null);

                login.commit();

            }
        });

        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cadastroFrag = new CadastroFragment();

                FragmentTransaction cadastro = getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.popenter, R.anim.popexit)
                        .replace(R.id.frameConteudo, cadastroFrag)
                        .addToBackStack(null);

                cadastro.commit();

            }
        });


    }


}