package com.italo.sistemaead.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.italo.sistemaead.R;
import com.italo.sistemaead.adapter.AdapterMeusCursos;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.config.IdUser;
import com.italo.sistemaead.helper.GerenciadorClicks;
import com.italo.sistemaead.model.Cursos;

import java.util.ArrayList;
import java.util.List;

public class MeusCursosActivity extends AppCompatActivity {

    private RecyclerView recyclerMeusCursos;
    private AdapterMeusCursos adapter;
    private final List<Cursos> meusCursosLista = new ArrayList<>();
    private DatabaseReference database;
    private String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_cursos);

        iniComponentes();
        itemClick();

    }

    @Override
    protected void onStart() {
        super.onStart();

        getCursosUsuario();
    }

    public void getCursosUsuario() {

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                meusCursosLista.clear();

                for (DataSnapshot snapshotMeusCursos : dataSnapshot.getChildren()) {
                    Cursos curso = snapshotMeusCursos.getValue(Cursos.class);

                    meusCursosLista.add(curso);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.i("LOG ERRO", databaseError.getMessage());

            }
        });

    }

    public void itemClick() {

        recyclerMeusCursos.addOnItemTouchListener(
                new GerenciadorClicks(getApplicationContext(), recyclerMeusCursos,
                        new GerenciadorClicks.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Cursos cursos = meusCursosLista.get(position); // -> Recuperando posicao do item

                                Intent i = new Intent(getApplicationContext(), MenuAulasActivity.class);  // -> Iniciando nova activity e passando os dados
                                i.putExtra("ID_CURSO", cursos.getIdCurso());
                                i.putExtra("TITULO_CURSO", cursos.getTituloCurso());
                                i.putExtra("DESCRICAO_CURSO", cursos.getDescricaoCurso());
                                startActivity(i);
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                Cursos curso = meusCursosLista.get(position); // -> Recuperando posicao do item

                                final String titulo = curso.getTituloCurso();
                                final String id = curso.getIdCurso();

                                Snackbar.make(view, "Deseja remover: " + titulo + "?",  // -> Chamando SnackBar
                                        BaseTransientBottomBar.LENGTH_SHORT)
                                        .setBackgroundTint(getResources().getColor(R.color.colorBackSnack))
                                        .setTextColor(getResources().getColor(R.color.colorTextSnack))
                                        .setAction("Sim", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                database.child(id).removeValue(); // -> Removendo curso do perfil
                                                adapter.notifyDataSetChanged();

                                                Toast.makeText(getApplicationContext(),
                                                        "Curso removido com sucesso!",
                                                        Toast.LENGTH_LONG).show();

                                            }
                                        }).show();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }));

    }

    public void iniComponentes() {

        recyclerMeusCursos = findViewById(R.id.recyclerViewMeusCursos);

        recyclerMeusCursos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerMeusCursos.setHasFixedSize(true);
        adapter = new AdapterMeusCursos(getApplicationContext(), meusCursosLista);
        recyclerMeusCursos.setAdapter(adapter);

        idUsuario = IdUser.getIdUser();

        database = ConfigFirebase.getFirebaseDatabase()
                .child("Usuarios")
                .child("Perfil")
                .child(idUsuario)
                .child("CursosUsuario");

        getSupportActionBar().setTitle("Meus Cursos");
    }

}