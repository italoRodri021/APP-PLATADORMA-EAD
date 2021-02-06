package com.italo.sistemaead.activity;

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
import com.italo.sistemaead.model.Evaluation;

public class EvaluationActivity extends AppCompatActivity {

    private WebView webView;
    private TextView textDateHour;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        iniComponents();
        getEvaluation();
    }

    public void getEvaluation() {


        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue() != null) {
                    Evaluation e = snapshot.getValue(Evaluation.class);

                    textDateHour.setText(e.getDateHour());
                    webView.setWebViewClient(new WebViewClient());
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl(e.getUrlGoogleForms());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ERROR", error.getMessage());
            }
        });

    }

    public void iniComponents() {

        webView = findViewById(R.id.webViewProva);
        textDateHour = findViewById(R.id.textViewDataHoraProva);
        getSupportActionBar().setTitle("Avaliação");

        database = ConfigFirebase.getFirebaseDatabase().child("Evaluetion");

    }

}