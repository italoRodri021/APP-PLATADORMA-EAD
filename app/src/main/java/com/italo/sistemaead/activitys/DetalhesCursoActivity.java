package com.italo.sistemaead.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.italo.sistemaead.R;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.config.IdUser;
import com.italo.sistemaead.model.Cursos;

public class DetalhesCursoActivity extends AppCompatActivity {

    private ImageView imageViewCapaCurso;
    private TextView textTitulo, textDescricao;
    private Button botaoAddMeusCursos;
    private StorageReference storage;
    private DatabaseReference database;
    private String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_curso);

        iniComponentes();
        getDetalhesCurso();

    }

    public void getDetalhesCurso() {

        idUsuario = IdUser.getIdUser();

        Intent i = getIntent(); // Recuperando dados da intent
        final String id = i.getStringExtra("ID_CURSO");
        final String titulo = i.getStringExtra("TITULO_CURSO");
        final String descricao = i.getStringExtra("DESCRICAO_CURSO");

        // Configurando textViews
        textTitulo.setText(titulo);
        textDescricao.setText(descricao);

        storage = ConfigFirebase.getFirebaseStorage(); // Recuperando imagem da capa

        StorageReference imagemRef = storage.child("Imagens")
                .child("CapaCursos")
                .child(id + ".JPEG");

        long MAXIMOBYTES = 1024 * 1024;

        imagemRef.getBytes(MAXIMOBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {


                Bitmap b = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);  // Convertendo imagem em Bitmap
                imageViewCapaCurso.setImageBitmap(b);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        });


        database = ConfigFirebase.getFirebaseDatabase() // -> Salvando curso no perfil do usuario
                .child("Usuarios")
                .child("Perfil")
                .child(idUsuario)
                .child("CursosUsuario");

        botaoAddMeusCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tituloUp = titulo.toUpperCase();

                Cursos addCurso = new Cursos(id, titulo, descricao, tituloUp); // Salvando curso no perfil do usuário
                database.child(id).setValue(addCurso);

                Toast.makeText(DetalhesCursoActivity.this,
                        "Adicionado com sucesso!",
                        Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(), MeusCursosActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

    }

    public void iniComponentes() {

        imageViewCapaCurso = findViewById(R.id.imageViewCapaCursoDetalhes);
        textTitulo = findViewById(R.id.textViewTituloCursoDetalhes);
        textDescricao = findViewById(R.id.textViewDescricaoCursoDetalhes);
        botaoAddMeusCursos = findViewById(R.id.btnAddMeusCursosDetalhes);

    }

}