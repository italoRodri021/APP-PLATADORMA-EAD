package com.italo.sistemaead.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.italo.sistemaead.R;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.config.IdUser;
import com.italo.sistemaead.model.Usuario;


public class AnotacoesFragment extends Fragment {

    private EditText campoAnotacao;
    private FloatingActionButton botaoSalvar;
    private DatabaseReference database;
    private String idUsuario;

    public AnotacoesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anotacoes, container, false);

        iniComponentes(view);
        configInterface();
        getAnotacao();


        return view;
    }

    public void configInterface() {

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Deseja salvar está nota?", BaseTransientBottomBar.LENGTH_SHORT)
                        .setBackgroundTint(getResources().getColor(R.color.colorBackSnack))
                        .setTextColor(getResources().getColor(R.color.colorTextSnack))
                        .setAction("Sim", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                salvarAnotacao();

                                Toast.makeText(getContext(),
                                        "Salvo com sucesso!",
                                        Toast.LENGTH_LONG).show();
                            }
                        }).show();

            }
        });

    }

    public void getAnotacao() {

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);

                    campoAnotacao.setText(usuario.getAnotacoes());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.i("LOG ERRO DADOS", databaseError.getMessage());

            }
        });

    }

    public void salvarAnotacao() {

        String anotacao = campoAnotacao.getText().toString(); // -> Recuperando texto dos campos

        if (!anotacao.isEmpty()) { // -> Atualizando anotacao

            Usuario usuario = new Usuario();
            usuario.setId(idUsuario);
            usuario.setAnotacoes(anotacao);
            usuario.salvarAnotacao();

        } else {

        }

    }

    public void iniComponentes(View v) {

        campoAnotacao = v.findViewById(R.id.editTextAnotacao);
        botaoSalvar = v.findViewById(R.id.fabSalvarAnotacao);

        idUsuario = IdUser.getIdUser();

        database = ConfigFirebase.getFirebaseDatabase()
                .child("Usuarios")
                .child("Perfil")
                .child(idUsuario);

    }

}