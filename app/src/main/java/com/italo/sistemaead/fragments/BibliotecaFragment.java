package com.italo.sistemaead.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.italo.sistemaead.R;
import com.italo.sistemaead.adapter.AdapterBiblioteca;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.model.Livros;

import java.util.ArrayList;
import java.util.List;

public class BibliotecaFragment extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    private AdapterBiblioteca adapter;
    private RecyclerView recyclerLivros;
    private SearchView searchView;
    private DatabaseReference database;
    private final List<Livros> livrosLista = new ArrayList<>();

    public BibliotecaFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_biblioteca, container, false);

        iniComponentes(view);
        configInterface();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getLivros();
    }

    public void configInterface() {

        searchView.setQueryHint("Buscar Livros");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String texto) {
                String textoDigitado = texto.toUpperCase();

                pesquisar(textoDigitado);
                return true;
            }
        });

    }

    public void getLivros() {

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                livrosLista.clear();

                for (DataSnapshot snapshotLivros : dataSnapshot.getChildren()) {
                    Livros livros = snapshotLivros.getValue(Livros.class);

                    livrosLista.add(livros);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void pesquisar(String texto) {

        livrosLista.clear();

        Query queryPesquisa = database.orderByChild("tituloLivro").startAt("\uf8ff").endAt(texto);

        if (texto.length() >= 3) {

            queryPesquisa.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    livrosLista.clear();

                    for (DataSnapshot snapshotLivros : dataSnapshot.getChildren()) {
                        Livros livros = snapshotLivros.getValue(Livros.class);

                        livrosLista.add(livros);

                    }
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    public void iniComponentes(View view) {

        recyclerLivros = view.findViewById(R.id.recyclerViewLivros);
        searchView = view.findViewById(R.id.searchViewLivros);

        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerLivros.setLayoutManager(layoutManager);
        recyclerLivros.setHasFixedSize(true);
        recyclerLivros.setAdapter(adapter);
        adapter = new AdapterBiblioteca(getContext(), livrosLista);

        database = ConfigFirebase.getFirebaseDatabase().child("Biblioteca").child("Livros");


    }


}