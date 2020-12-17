package com.italo.sistemaead.fragments;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.italo.sistemaead.R;
import com.italo.sistemaead.activitys.MenuAulasActivity;
import com.italo.sistemaead.activitys.PlayerVideoActivity;
import com.italo.sistemaead.adapter.AdapterAulas;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.config.IdUser;
import com.italo.sistemaead.helper.GerenciadorClicks;
import com.italo.sistemaead.model.Aulas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AulasFragment extends Fragment {

    private TextView textTitulo;
    private RecyclerView recyclerViewAulas;
    private AdapterAulas adapter;
    private DatabaseReference database;
    private final List<Aulas> aulasLista = new ArrayList<>();
    private final VideoView videoView = MenuAulasActivity.videoView;
    private String idUsuario;
    private String idCurso;

    public AulasFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_aulas, container, false);

        iniComponentes(view);
        itemClick();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getAulas();
    }

    public void getAulas() {

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                aulasLista.clear();

                for (DataSnapshot snapshotAulas : dataSnapshot.getChildren()) {
                    Aulas aulas = snapshotAulas.getValue(Aulas.class);

                    aulasLista.add(aulas);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.i("LOG ERRO DADOS", databaseError.getMessage());

            }
        });

    }

    public void itemClick() {

        recyclerViewAulas.addOnItemTouchListener(
                new GerenciadorClicks(getContext(), recyclerViewAulas,
                        new GerenciadorClicks.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                final Aulas aula = aulasLista.get(position); // -> Recuperando posicao do item
                                String url = aula.getUrlAula();

                                videoView.setVisibility(View.VISIBLE);  // -> Iniciando vídeo no videoView da MenuVideoActivity
                                videoView.setVideoURI(Uri.parse(url));
                                videoView.start();

                                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {

                                        String id = aula.getIdAulas();

                                        DatabaseReference database = ConfigFirebase.getFirebaseDatabase()
                                                .child("Usuarios")
                                                .child("Perfil")
                                                .child(idUsuario)
                                                .child("CursosUsuario")
                                                .child(id);

                                        // Lembrar de modificar o codigo para que as aulas tambem sejam salvas no id do usuario

                                        aula.setIdAulas(id);
                                        aula.setProgresso(true);

                                        Map<String, Object> dados = aula.mapAula();
                                        database.updateChildren(dados);


                                    }
                                });


                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                Aulas aula = aulasLista.get(position);

                                if (MenuAulasActivity.videoView != null) { // -> Escondendo videoView antes de iniciar a intent

                                    MenuAulasActivity.videoView.pause();
                                    MenuAulasActivity.videoView.setVisibility(View.GONE);
                                }

                                Intent i = new Intent(getActivity(), PlayerVideoActivity.class); // -> Passando dados para a PlayerActivity
                                i.putExtra("URL_VIDEO", aula.getUrlAula());
                                startActivity(i);

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }));

    }

    public void iniComponentes(View view) {

        textTitulo = view.findViewById(R.id.textViewTituloCursoAulas);
        recyclerViewAulas = view.findViewById(R.id.recyclerViewAulas);

        recyclerViewAulas.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAulas.setHasFixedSize(true);
        adapter = new AdapterAulas(getContext(), aulasLista);
        recyclerViewAulas.setAdapter(adapter);

        Intent i = getActivity().getIntent();
        idCurso = i.getStringExtra("ID_CURSO");
        String titulo = i.getStringExtra("TITULO_CURSO");

        idUsuario = IdUser.getIdUser();
        database = ConfigFirebase.getFirebaseDatabase().child("Aulas").child(idCurso);

        textTitulo.setText(titulo);


    }


}