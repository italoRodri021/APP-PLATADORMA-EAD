package com.italo.sistemaead.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.italo.sistemaead.R;
import com.italo.sistemaead.activitys.AvaliacaoActivity;
import com.italo.sistemaead.activitys.EditarPerfilActivity;
import com.italo.sistemaead.activitys.MeusCursosActivity;
import com.italo.sistemaead.activitys.NotasActivity;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.config.IdUser;
import com.italo.sistemaead.model.Usuario;


public class AlunoFragment extends Fragment {

    private Button botaoEditarPerfil, botaoMeusCursos, botaoMinhasNotas, botaoAvaliacao;
    private ImageView imageFotoAluno;
    private TextView textNome;
    private DatabaseReference database;
    private StorageReference storage;
    private String idUsuario;

    public AlunoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aluno, container, false);

        iniComponentes(view);
        configInterface();
        getDadosUsuario();

        return view;
    }

    public void configInterface() {

        idUsuario = IdUser.getIdUser(); // -> Recuperando id do usuário

        database = ConfigFirebase.getFirebaseDatabase()
                .child("Usuarios")
                .child("Perfil")
                .child(idUsuario);

        storage = ConfigFirebase.getFirebaseStorage()
                .child("Usuarios")
                .child("Perfil");


        botaoEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getContext(), EditarPerfilActivity.class);
                startActivity(i);
            }
        });


        botaoMeusCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), MeusCursosActivity.class);
                startActivity(i);
            }
        });

        botaoMinhasNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), NotasActivity.class);
                startActivity(i);
            }
        });

        botaoAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), AvaliacaoActivity.class);
                startActivity(i);
            }
        });

    }

    public void getDadosUsuario() {


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue() != null) {
                    Usuario usuario = snapshot.getValue(Usuario.class);

                    textNome.setText(usuario.getNome());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        StorageReference imagemRef = storage.child(idUsuario + ".JPEG");


        long MAXIMOBYTES = 1080 * 1080;

        imagemRef.getBytes(MAXIMOBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {


                Bitmap b = BitmapFactory.decodeByteArray(bytes, 0, bytes.length); // Convetendo imagem para Bitmap
                imageFotoAluno.setImageBitmap(b);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.i("LOG ERRO DADOS", e.getMessage());

            }
        });

    }

    public void iniComponentes(View view) {

        imageFotoAluno = view.findViewById(R.id.imageViewFotoPerfil);
        botaoEditarPerfil = view.findViewById(R.id.btnEditarPerfil);
        botaoMeusCursos = view.findViewById(R.id.btnMeusCursos);
        botaoMinhasNotas = view.findViewById(R.id.btnMinhasNotas);
        textNome = view.findViewById(R.id.textViewNomeAluno);
        botaoAvaliacao = view.findViewById(R.id.btnAvaliacaoAluno);

    }


}