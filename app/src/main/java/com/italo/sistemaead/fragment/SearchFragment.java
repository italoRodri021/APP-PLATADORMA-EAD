package com.italo.sistemaead.fragment;

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
import com.italo.sistemaead.activity.DetailsCourseActivity;
import com.italo.sistemaead.adapter.AdapterSearch;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.helper.RecyclerClicks;
import com.italo.sistemaead.model.Course;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private final List<Course> listCourse = new ArrayList<>();
    private RecyclerView recyclerSearch;
    private SearchView searchView;
    private DatabaseReference database;
    private AdapterSearch adapter;

    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        iniComponentes(view);
        configSearch();
        itemClick();

        return view;
    }

    public void configSearch() {

        searchView.setQueryHint("Buscar cursos");
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {

                        String textoDigitado = newText.toUpperCase();
                        search(textoDigitado);
                        return true;
                    }
                });

    }

    public void search(String text) {

        Query query = database.orderByChild("titleSearch").startAt(text).endAt("\uf8ff");
        listCourse.clear();

        if (text.length() >= 3) {

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    listCourse.clear();

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Course course = ds.getValue(Course.class);

                        listCourse.add(course);

                    }
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("ERROR", error.getMessage());
                }
            });


        } else {

            listCourse.clear();
            adapter.notifyDataSetChanged();
        }

    }

    public void itemClick() {

        recyclerSearch.addOnItemTouchListener(
                new RecyclerClicks(getActivity(), recyclerSearch,
                        new RecyclerClicks.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Course course = listCourse.get(position);

                                Intent i = new Intent(getContext(), DetailsCourseActivity.class);
                                i.putExtra("ID_CURSO", course.getIdCourse());
                                i.putExtra("TITULO_CURSO", course.getTitle());
                                i.putExtra("DESCRICAO_CURSO", course.getDescription());
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

        searchView = view.findViewById(R.id.searchViewCurso);
        recyclerSearch = view.findViewById(R.id.recyclerPesquisaCursos);

        adapter = new AdapterSearch(getContext(), listCourse);
        recyclerSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerSearch.setHasFixedSize(true);
        recyclerSearch.setAdapter(adapter);

        database = ConfigFirebase.getFirebaseDatabase().child("Courses");
    }
}