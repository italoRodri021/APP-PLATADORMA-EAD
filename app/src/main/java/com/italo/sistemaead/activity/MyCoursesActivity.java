package com.italo.sistemaead.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

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
import com.italo.sistemaead.adapter.AdapterMyCourses;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.config.UserFirebase;
import com.italo.sistemaead.helper.RecyclerClicks;
import com.italo.sistemaead.model.Course;

import java.util.ArrayList;
import java.util.List;

public class MyCoursesActivity extends AppCompatActivity {

    private final List<Course> listCourses = new ArrayList<>();
    private RecyclerView recyclerMyCourses;
    private AdapterMyCourses adapter;
    private DatabaseReference database;
    private String idCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        iniComponentes();
        itemClick();

    }

    @Override
    protected void onStart() {
        super.onStart();

        getCursosUsuario();
    }

    public void getCursosUsuario() {

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listCourses.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Course course = ds.getValue(Course.class);

                    listCourses.add(course);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ERROR", error.getMessage());
            }
        });

    }

    public void itemClick() {

        recyclerMyCourses.addOnItemTouchListener(
                new RecyclerClicks(getApplicationContext(), recyclerMyCourses,
                        new RecyclerClicks.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Course course = listCourses.get(position);

                                Intent i = new Intent(getApplicationContext(), MenuClassActivity.class);
                                i.putExtra("DATA_COURSE", course);
                                startActivity(i);
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                Course course = listCourses.get(position);

                                Snackbar.make(view, "Deseja remover: " + course.getTitle() + "?",
                                        BaseTransientBottomBar.LENGTH_SHORT)
                                        .setBackgroundTint(getResources().getColor(R.color.colorBackSnack))
                                        .setTextColor(getResources().getColor(R.color.colorTextSnack))
                                        .setAction("CONFIRMAR", view1 -> {

                                            database.child(course.getIdCourse()).removeValue();
                                            adapter.notifyDataSetChanged();

                                        }).show();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            }
                        }));

    }

    public void iniComponentes() {

        recyclerMyCourses = findViewById(R.id.recyclerViewMeusCursos);

        recyclerMyCourses.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerMyCourses.setHasFixedSize(true);
        adapter = new AdapterMyCourses(getApplicationContext(), listCourses);
        recyclerMyCourses.setAdapter(adapter);
        getSupportActionBar().setTitle("Meus Cursos");

        idCurrentUser = UserFirebase.getIdUser();
        database = ConfigFirebase.getFirebaseDatabase().child("User")
                .child("Profile").child(idCurrentUser).child("CourseUser");

    }

}