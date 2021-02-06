package com.italo.sistemaead.fragment;

import android.content.Intent;
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
import com.italo.sistemaead.activity.MenuClassActivity;
import com.italo.sistemaead.activity.PlayerVideoActivity;
import com.italo.sistemaead.adapter.AdapterClass;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.config.UserFirebase;
import com.italo.sistemaead.helper.RecyclerClicks;
import com.italo.sistemaead.model.Class;
import com.italo.sistemaead.model.Course;

import java.util.ArrayList;
import java.util.List;

public class ClassFragment extends Fragment {

    private final List<Class> listClass = new ArrayList<>();
    private final VideoView videoView = MenuClassActivity.videoView;
    private Course course;
    private String idCurrentUser;
    private TextView textTitle;
    private RecyclerView recyclerClass;
    private AdapterClass adapter;
    private DatabaseReference database;

    public ClassFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_class, container, false);

        iniComponentes(view);
        configClickItemList();
        getClassCourse();

        return view;
    }

    public void getClassCourse() {

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Class classCourse = ds.getValue(Class.class);

                    listClass.add(classCourse);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ERROR", error.getMessage());
            }
        });

    }

    public void configClickItemList() {

        recyclerClass.addOnItemTouchListener(
                new RecyclerClicks(getContext(), recyclerClass,
                        new RecyclerClicks.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Class c = listClass.get(position);

                                videoView.setVisibility(View.VISIBLE);
                                videoView.setVideoURI(Uri.parse(c.getUrl()));
                                videoView.start();

                                videoView.setOnCompletionListener(mediaPlayer -> {

                                    Class aClass = new Class();
                                    aClass.setIdUser(idCurrentUser);
                                    aClass.setIdCourse(course.getIdCourse());
                                    aClass.setIdClass(c.getIdClass());
                                    aClass.setProgress(true);
                                    aClass.updateProgress();

                                });


                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                Class aula = listClass.get(position);

                                if (MenuClassActivity.videoView != null) {

                                    MenuClassActivity.videoView.pause();
                                    MenuClassActivity.videoView.setVisibility(View.GONE);
                                }

                                Intent i = new Intent(getActivity(), PlayerVideoActivity.class);
                                i.putExtra("URL_VIDEO", aula.getUrl());
                                startActivity(i);

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }));

    }

    public void iniComponentes(View view) {

        textTitle = view.findViewById(R.id.textViewTituloCursoAulas);
        recyclerClass = view.findViewById(R.id.recyclerViewAulas);

        recyclerClass.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerClass.setHasFixedSize(true);
        adapter = new AdapterClass(getContext(), listClass);
        recyclerClass.setAdapter(adapter);

        Intent i = getActivity().getIntent();
        course = (Course) i.getSerializableExtra("DATA_COURSE");


        idCurrentUser = UserFirebase.getIdUser();
        database = ConfigFirebase.getFirebaseDatabase().child("ClassCourse");

        textTitle.setText(course.getTitle());


    }


}