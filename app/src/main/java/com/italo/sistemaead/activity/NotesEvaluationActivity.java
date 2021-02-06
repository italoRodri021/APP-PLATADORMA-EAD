package com.italo.sistemaead.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.italo.sistemaead.R;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.config.UserFirebase;
import com.italo.sistemaead.model.User;

public class NotesEvaluationActivity extends AppCompatActivity {

    private TextView textNote, textObs;
    private DatabaseReference database;
    private String idCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        iniComponents();
        getNotas();
    }

    public void getNotas() {

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue() != null) {

                    User usuario = snapshot.getValue(User.class);
                    textNote.setText(usuario.getNoteEvaluetion());
                    textObs.setText(usuario.getObsEvaluetion());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ERROR", error.getMessage());
            }
        });


    }

    public void iniComponents() {

        textNote = findViewById(R.id.textViewNotaAluno);
        textObs = findViewById(R.id.textViewObservacaoProfessor);
        getSupportActionBar().setTitle("Notas");

        idCurrentUser = UserFirebase.getIdUser();
        database = ConfigFirebase.getFirebaseDatabase().child("User").child("Profile").child(idCurrentUser);
    }

}