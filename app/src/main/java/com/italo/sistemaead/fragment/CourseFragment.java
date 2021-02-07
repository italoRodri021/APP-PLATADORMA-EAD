package com.italo.sistemaead.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.italo.sistemaead.activity.DetailsCourseActivity;
import com.italo.sistemaead.adapter.AdapterCourses;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.helper.RecyclerClicks;
import com.italo.sistemaead.model.Course;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;


public class CourseFragment extends Fragment {

    private final List<Course> listCourses = new ArrayList<>();
    int[] banners = {R.drawable.image_banner, R.drawable.banner_slide_2, R.drawable.banner_slide_3, R.drawable.banner_slide_1};
    private RecyclerView recyclerCourses;
    private ProgressBar progressBar;
    private CarouselView carouselView;
    private AdapterCourses adapter;
    private DatabaseReference database;

    public CourseFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, container, false);

        iniComponentes(view);
        getCourses();
        itemClick();

        return view;
    }

    public void getCourses() {

        progressBar.setVisibility(View.VISIBLE);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listCourses.clear();

                for (DataSnapshot snapshotCursos : snapshot.getChildren()) {
                    Course courses = snapshotCursos.getValue(Course.class);

                    listCourses.add(courses);
                    progressBar.setVisibility(View.GONE);
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

        recyclerCourses.addOnItemTouchListener(
                new RecyclerClicks(getActivity(), recyclerCourses,
                        new RecyclerClicks.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Course course = listCourses.get(position);

                                Intent i = new Intent(getContext(), DetailsCourseActivity.class);
                                i.putExtra("DATA_COURSE", course);
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

        recyclerCourses = view.findViewById(R.id.recyclerViewCursos);
        progressBar = view.findViewById(R.id.progressBarCursos);
        carouselView = view.findViewById(R.id.carouselView);

        recyclerCourses.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerCourses.setHasFixedSize(true);
        adapter = new AdapterCourses(getActivity(), listCourses);
        recyclerCourses.setAdapter(adapter);

        database = ConfigFirebase.getFirebaseDatabase().child("Courses");

        carouselView.setPageCount(banners.length);

        ImageListener imageListener = (position, imageView) -> imageView.setImageResource(banners[position]);
        carouselView.setImageListener(imageListener);

    }

}