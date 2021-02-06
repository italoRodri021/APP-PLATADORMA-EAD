package com.italo.sistemaead.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.italo.sistemaead.R;
import com.italo.sistemaead.fragment.AnnotationsFragment;
import com.italo.sistemaead.fragment.ClassFragment;

public class MenuClassActivity extends AppCompatActivity {

    public static VideoView videoView;
    private Button btnClass, btnNotes;
    private AnnotationsFragment annotationsFrag;
    private ClassFragment classFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_class);

        iniComponents();
        configInterface();

    }

    public void configInterface() {

        videoView.setMediaController( // -> Configurando videoView
                new MediaController(MenuClassActivity.this));

        /**
         *   Fragment de inicio
         */

        classFrag = new ClassFragment();
        FragmentTransaction transInit = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.popenter, R.anim.popexit)
                .add(R.id.frameConteudoFragVideo, classFrag);
        transInit.commit();

        /**
         *   Fragment Aulas
         */

        btnClass.setOnClickListener((v) -> {

            classFrag = new ClassFragment();
            FragmentTransaction trans1 = getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.popenter, R.anim.popexit)
                    .replace(R.id.frameConteudoFragVideo, classFrag);
            trans1.commit();

        });

        /**
         *  Fragment Anotacoes
         */

        btnNotes.setOnClickListener(view -> {

            annotationsFrag = new AnnotationsFragment();
            FragmentTransaction trans2 = getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.popenter, R.anim.popexit)
                    .replace(R.id.frameConteudoFragVideo, annotationsFrag);
            trans2.commit();

        });

    }

    public void iniComponents() {

        videoView = findViewById(R.id.videoViewPlayer);
        btnClass = findViewById(R.id.btnFragmentAulas);
        btnNotes = findViewById(R.id.btnFragmentAnotacoes);
        getSupportActionBar().setTitle("Aulas do Curso");

    }

}