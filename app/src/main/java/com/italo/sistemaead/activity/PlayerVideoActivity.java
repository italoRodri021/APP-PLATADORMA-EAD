package com.italo.sistemaead.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.italo.sistemaead.R;

public class PlayerVideoActivity extends AppCompatActivity {

    public static VideoView videoView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_video);

        iniComponentes();
        configInterface();

    }

    public void configInterface() {

        Intent i = getIntent();
        String URL_MOVIE = i.getStringExtra("URL_VIDEO");

        videoView.setVideoURI(Uri.parse(URL_MOVIE));

        Handler h = new Handler();

        h.postDelayed(() -> {

            progressBar.setVisibility(View.GONE);
            videoView.start();

        }, 3000);

    }

    public void iniComponentes() {

        progressBar = findViewById(R.id.progressBarPlayer);
        videoView = findViewById(R.id.videoViewTelaCheia);
        videoView.setMediaController(new MediaController(PlayerVideoActivity.this));

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
        super.onBackPressed();

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}