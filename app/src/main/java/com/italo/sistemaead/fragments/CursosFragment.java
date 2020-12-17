package com.italo.sistemaead.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.italo.sistemaead.R;
import com.italo.sistemaead.activitys.DetalhesCursoActivity;
import com.italo.sistemaead.adapter.AdapterCursos;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.helper.GerenciadorClicks;
import com.italo.sistemaead.model.Cursos;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;


public class CursosFragment extends Fragment {

    int[] banners = {R.drawable.imagem_banner, R.drawable.banner_2, R.drawable.banner_3, R.drawable.banner_1};
    final ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            imageView.setImageResource(banners[position]);

        }
    };
    private RecyclerView recyclerCursos;
    private ProgressBar progressBar;
    private CarouselView carouselView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterCursos adapter;
    private final List<Cursos> cursosLista = new ArrayList<>();
    private DatabaseReference database;

    public CursosFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cursos, container, false);

        iniComponentes(view);
        itemClick();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getCursos();
    }

    public void getCursos() {

        progressBar.setVisibility(View.VISIBLE);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                cursosLista.clear();

                for (DataSnapshot snapshotCursos : dataSnapshot.getChildren()) {
                    Cursos cursos = snapshotCursos.getValue(Cursos.class);

                    cursosLista.add(cursos);
                    progressBar.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.d("ERROR", databaseError.getMessage());
            }
        });

    }

    public void itemClick() {

        recyclerCursos.addOnItemTouchListener(
                new GerenciadorClicks(getActivity(), recyclerCursos,
                        new GerenciadorClicks.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Cursos curso = cursosLista.get(position); // -> Recuperando posicao do item

                                Intent i = new Intent(getContext(), DetalhesCursoActivity.class); // -> Passando dados para a DetalhesCursoActivity
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

        recyclerCursos = view.findViewById(R.id.recyclerViewCursos);
        progressBar = view.findViewById(R.id.progressBarCursos);
        carouselView = view.findViewById(R.id.carouselView);

        layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
        recyclerCursos.setLayoutManager(layoutManager);
        recyclerCursos.setHasFixedSize(true);
        adapter = new AdapterCursos(getActivity(), cursosLista);
        recyclerCursos.setAdapter(adapter);

        database = ConfigFirebase.getFirebaseDatabase().child("Cursos");

        carouselView.setPageCount(banners.length);
        carouselView.setImageListener(imageListener);

    }

}