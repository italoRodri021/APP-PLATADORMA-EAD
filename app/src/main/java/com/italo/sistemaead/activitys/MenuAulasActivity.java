package com.italo.sistemaead.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.italo.sistemaead.R;
import com.italo.sistemaead.fragments.AnotacoesFragment;
import com.italo.sistemaead.fragments.AulasFragment;

public class MenuAulasActivity extends AppCompatActivity {

    public static VideoView videoView;
    private Button botaoAulas, botaoAnotacoes;
    private AnotacoesFragment fragAnotacoes;
    private AulasFragment fragAulas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_aulas);

        iniComponentes();
        configInterface();

    }

    public void configInterface() {

        videoView.setMediaController( // -> Configurando videoView
                new MediaController(MenuAulasActivity.this));

        /**
         *   Fragment de inicio
         */

        fragAulas = new AulasFragment();

        FragmentTransaction inicio = getSupportFragmentManager() // -> Iniciando transacao
                .beginTransaction().setCustomAnimations(R.anim.popenter, R.anim.popexit)
                .add(R.id.frameConteudoFragVideo, fragAulas);


        inicio.commit(); // -> Encerrando transacao


        /**
         *   Fragment Aulas
         */

        botaoAulas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragAulas = new AulasFragment();

                FragmentTransaction aulas = getSupportFragmentManager() // -> Inciando transacao
                        .beginTransaction().setCustomAnimations(R.anim.popenter, R.anim.popexit)
                        .replace(R.id.frameConteudoFragVideo, fragAulas);

                aulas.commit(); // -> Encerrando transacao

            }
        });

        /**
         *  Fragment Anotacoes
         */

        botaoAnotacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragAnotacoes = new AnotacoesFragment();

                FragmentTransaction anotacoes = getSupportFragmentManager() // -> Inciando transacao
                        .beginTransaction().setCustomAnimations(R.anim.popenter, R.anim.popexit)
                        .replace(R.id.frameConteudoFragVideo, fragAnotacoes);

                anotacoes.commit(); // -> Encerrando transacao

            }
        });

    }

    public void iniComponentes() {

        videoView = findViewById(R.id.videoViewPlayer);
        botaoAulas = findViewById(R.id.btnFragmentAulas);
        botaoAnotacoes = findViewById(R.id.btnFragmentAnotacoes);

        getSupportActionBar().setTitle("Aulas do Curso");


    }

}