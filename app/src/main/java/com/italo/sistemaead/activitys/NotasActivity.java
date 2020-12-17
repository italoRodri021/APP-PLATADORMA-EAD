package com.italo.sistemaead.activitys;

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
import com.italo.sistemaead.config.IdUser;
import com.italo.sistemaead.model.Usuario;

public class NotasActivity extends AppCompatActivity {

    private TextView textNota, textObservacao;
    private DatabaseReference database;
    private String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        iniComponentes();
        getNotas();
    }

    public void getNotas() {

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    textNota.setText(usuario.getNotaAvaliacao());
                    textObservacao.setText(usuario.getObsAvaliacao());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.d("ERROR", databaseError.getMessage());
            }
        });


    }

    public void iniComponentes() {

        getSupportActionBar().setTitle("Notas");

        textNota = findViewById(R.id.textViewNotaAluno);
        textObservacao = findViewById(R.id.textViewObservacaoProfessor);

        idUsuario = IdUser.getIdUser();

        database = ConfigFirebase.getFirebaseDatabase()
                .child("Usuarios")
                .child("Perfil")
                .child(idUsuario);

    }

}