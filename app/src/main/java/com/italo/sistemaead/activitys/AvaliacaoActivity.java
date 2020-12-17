package com.italo.sistemaead.activitys;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.italo.sistemaead.R;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.model.Avaliacao;

public class AvaliacaoActivity extends AppCompatActivity {

    private WebView webView;
    private TextView textDataHora;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao);

        iniComponentes();
    }


    @Override
    protected void onStart() {
        super.onStart();

        getAvaliacao();
    }

    public void iniComponentes() {

        webView = findViewById(R.id.webViewProva);
        textDataHora = findViewById(R.id.textViewDataHoraProva);

        getSupportActionBar().setTitle("Avaliação");

        database = ConfigFirebase.getFirebaseDatabase().child("Avaliacao");

    }

    public void getAvaliacao() {


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    Avaliacao avaliacao = dataSnapshot.getValue(Avaliacao.class);

                    String url = avaliacao.getUrlGoogleForms();

                    textDataHora.setText(avaliacao.getDataHoraAvaliacao());

                    webView.setWebViewClient(new WebViewClient());
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl(url);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.d("LOG ERRO", databaseError.getMessage());

            }
        });

    }
}