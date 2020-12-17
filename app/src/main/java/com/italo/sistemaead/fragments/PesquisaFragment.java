package com.italo.sistemaead.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.italo.sistemaead.R;
import com.italo.sistemaead.activitys.DetalhesCursoActivity;
import com.italo.sistemaead.adapter.AdapterPesquisa;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.helper.GerenciadorClicks;
import com.italo.sistemaead.model.Cursos;

import java.util.ArrayList;
import java.util.List;

public class PesquisaFragment extends Fragment {

    private RecyclerView recyclerPesquisa;
    private SearchView searchViewCurso;
    private DatabaseReference database;
    private AdapterPesquisa adapter;
    private final List<Cursos> cursosLista = new ArrayList<>();

    public PesquisaFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pesquisa, container, false);

        iniComponentes(view);
        configPesquisa();
        itemClick();

        return view;
    }

    public void configPesquisa() {

        database = ConfigFirebase.getFirebaseDatabase().child("Cursos");

        searchViewCurso.setQueryHint("Buscar cursos");
        searchViewCurso.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String novoTexto) {

                        String textoDigitado = novoTexto.toUpperCase();
                        pesquisar(textoDigitado);
                        return true;
                    }
                });

    }

    public void pesquisar(String texto) {

        cursosLista.clear();

        Query queryPesquisa = database.orderByChild("tituloPesquisa")
                .startAt(texto)
                .endAt("\uf8ff");

        if (texto.length() >= 3) {

            queryPesquisa.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    cursosLista.clear();

                    for (DataSnapshot snapshotPesquisa : dataSnapshot.getChildren()) {
                        Cursos cursos = snapshotPesquisa.getValue(Cursos.class);

                        cursosLista.add(cursos);

                    }

                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Log.i("ERRO", databaseError.getMessage());

                }
            });


        }

    }

    public void itemClick() {

        recyclerPesquisa.addOnItemTouchListener(
                new GerenciadorClicks(getActivity(), recyclerPesquisa,
                        new GerenciadorClicks.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Cursos curso = cursosLista.get(position);

                                Intent i = new Intent(getContext(), DetalhesCursoActivity.class);
                                i.putExtra("ID_CURSO", curso.getIdCurso());
                                i.putExtra("TITULO_CURSO", curso.getTituloCurso());
                                i.putExtra("DESCRICAO_CURSO", curso.getDescricaoCurso());
                                startActivity(i);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }));

    }

    public void iniComponentes(View view) {

        searchViewCurso = view.findViewById(R.id.searchViewCurso);
        recyclerPesquisa = view.findViewById(R.id.recyclerPesquisaCursos);

        adapter = new AdapterPesquisa(getContext(), cursosLista);
        recyclerPesquisa.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerPesquisa.setHasFixedSize(true);
        recyclerPesquisa.setAdapter(adapter);

    }
}