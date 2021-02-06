package com.italo.sistemaead.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.italo.sistemaead.R;

public class IntroActivity extends com.heinrichreimersoftware.materialintro.app.IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        slideConfig();

    }

    public void slideConfig() {

        setFullscreen(true);
        setButtonBackVisible(false);
        setButtonBackVisible(false);
        setButtonCtaVisible(false);
        autoplay(5000, INFINITE);


        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorSlide1)
                .backgroundDark(R.color.colorTextWhite)
                .fragment(R.layout.slide_intro_1)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorSlide2)
                .backgroundDark(R.color.colorTextWhite)
                .fragment(R.layout.slide_intro_2)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorSlide3)
                .backgroundDark(R.color.colorTextWhite)
                .fragment(R.layout.slide_intro_3).canGoForward(false)
                .build());


    }

    public void btnTelaInicioSlide3(View view) {

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }

    @Override
    public void onBackPressed() {

    }


}