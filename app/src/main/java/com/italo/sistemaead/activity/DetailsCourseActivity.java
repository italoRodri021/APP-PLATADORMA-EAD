package com.italo.sistemaead.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.italo.sistemaead.R;
import com.italo.sistemaead.config.UserFirebase;
import com.italo.sistemaead.model.Course;

public class DetailsCourseActivity extends AppCompatActivity {

    private Course course;
    private ImageView imagePhoto;
    private TextView textTitle, textDescription;
    private Button btnAddMyCourses;
    private String idCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_course);

        iniComponents();
        configInterface();

    }

    public void configInterface() {

        btnAddMyCourses.setOnClickListener(view -> {

            Course c = new Course();
            c.setIdUser(idCurrentUser);
            c.setIdCourse(course.getIdCourse());
            c.setTitle(course.getTitle());
            c.setDescription(course.getDescription());
            c.setTitleSearch(course.getTitle().toUpperCase());
            c.setUrlPhoto(course.getUrlPhoto());
            c.saveCourse();

            Toast.makeText(this, "Adicionado com sucesso!",
                    Toast.LENGTH_SHORT).show();

            Handler h = new Handler();
            h.postDelayed(() -> {

                Intent i = new Intent(getApplicationContext(), MyCoursesActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }, 2000);

        });

    }

    public void iniComponents() {

        imagePhoto = findViewById(R.id.imageViewCapaCursoDetalhes);
        textTitle = findViewById(R.id.textViewTituloCursoDetalhes);
        textDescription = findViewById(R.id.textViewDescricaoCursoDetalhes);
        btnAddMyCourses = findViewById(R.id.btnAddMeusCursosDetalhes);

        idCurrentUser = UserFirebase.getIdUser();

        Intent i = getIntent();
        course = (Course) i.getSerializableExtra("DATA_COURSE");
        textTitle.setText(course.getTitle());
        textDescription.setText(course.getDescription());
        Glide.with(this).load(course.getUrlPhoto()).into(imagePhoto);
    }

}