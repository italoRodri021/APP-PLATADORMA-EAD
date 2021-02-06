package com.italo.sistemaead.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.italo.sistemaead.R;
import com.italo.sistemaead.activity.EditProfileActivity;
import com.italo.sistemaead.activity.EvaluationActivity;
import com.italo.sistemaead.activity.MyCoursesActivity;
import com.italo.sistemaead.activity.NotesEvaluationActivity;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.config.UserFirebase;
import com.italo.sistemaead.model.User;


public class StudentFragment extends Fragment {

    private Button btnEditProfile, btnMyCourses, btnMyNotes, btnEvaluation;
    private ImageView imagePhoto;
    private TextView textName;
    private DatabaseReference database;
    private String idCurrentUser;

    public StudentFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student, container, false);

        iniComponents(view);
        configInterface();
        getDataUser();

        return view;
    }

    public void configInterface() {

        btnEditProfile.setOnClickListener(view -> {

            Intent i = new Intent(getContext(), EditProfileActivity.class);
            startActivity(i);
        });

        btnMyCourses.setOnClickListener(view -> {

            Intent i = new Intent(getActivity(), MyCoursesActivity.class);
            startActivity(i);
        });

        btnMyNotes.setOnClickListener(view -> {

            Intent i = new Intent(getActivity(), NotesEvaluationActivity.class);
            startActivity(i);
        });

        btnEvaluation.setOnClickListener(view -> {

            Intent i = new Intent(getActivity(), EvaluationActivity.class);
            startActivity(i);
        });

    }

    public void getDataUser() {

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue() != null) {
                    User u = snapshot.getValue(User.class);

                    textName.setText(u.getName());
                    Glide.with(getContext()).load(u.getUrlPhoto()).into(imagePhoto);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void iniComponents(View view) {

        imagePhoto = view.findViewById(R.id.imageViewFotoPerfil);
        btnEditProfile = view.findViewById(R.id.btnEditarPerfil);
        btnMyCourses = view.findViewById(R.id.btnMeusCursos);
        btnMyNotes = view.findViewById(R.id.btnMinhasNotas);
        textName = view.findViewById(R.id.textViewNomeAluno);
        btnEvaluation = view.findViewById(R.id.btnAvaliacaoAluno);

        idCurrentUser = UserFirebase.getIdUser();
        database = ConfigFirebase.getFirebaseDatabase().child("User").child("Profile").child(idCurrentUser);

    }


}