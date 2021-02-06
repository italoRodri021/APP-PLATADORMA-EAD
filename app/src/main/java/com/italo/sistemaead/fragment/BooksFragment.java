package com.italo.sistemaead.fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.italo.sistemaead.adapter.AdapterBooks;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.model.Books;

import java.util.ArrayList;
import java.util.List;

public class BooksFragment extends Fragment {

    private final List<Books> listBooks = new ArrayList<>();
    private AdapterBooks adapter;
    private RecyclerView recyclerBooks;
    private SearchView searchView;
    private DatabaseReference database;

    public BooksFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        iniComponents(view);
        configInterface();
        getLivros();

        return view;
    }

    public void configInterface() {

        searchView.setQueryHint("Buscar Livros");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText.toUpperCase();

                pesquisar(text);
                return true;
            }
        });

    }

    public void getLivros() {

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listBooks.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Books livros = ds.getValue(Books.class);

                    listBooks.add(livros);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ERROR", error.getMessage());
            }
        });

    }

    public void pesquisar(String text) {

        listBooks.clear();

        Query query = database.orderByChild("titleBook").startAt("\uf8ff").endAt(text);

        if (text.length() >= 3) {

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    listBooks.clear();

                    for (DataSnapshot snapshotLivros : snapshot.getChildren()) {
                        Books livros = snapshotLivros.getValue(Books.class);

                        listBooks.add(livros);

                    }
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("ERROR", error.getMessage());
                }
            });

        }

    }

    public void iniComponents(View view) {

        recyclerBooks = view.findViewById(R.id.recyclerViewLivros);
        searchView = view.findViewById(R.id.searchViewLivros);

        recyclerBooks.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerBooks.setHasFixedSize(true);
        recyclerBooks.setAdapter(adapter);
        adapter = new AdapterBooks(getContext(), listBooks);

        database = ConfigFirebase.getFirebaseDatabase().child("Bibliotec").child("Books");

    }


}