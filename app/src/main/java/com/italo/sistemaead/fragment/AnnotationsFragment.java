package com.italo.sistemaead.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.italo.sistemaead.R;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.config.UserFirebase;
import com.italo.sistemaead.model.User;


public class AnnotationsFragment extends Fragment {

    private EditText editAnnotation;
    private FloatingActionButton btnSaveData;
    private DatabaseReference database;
    private String idCurrentUser;
    private ValueEventListener valueEventListener;

    public AnnotationsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_annotations, container, false);

        iniComponentes(view);
        configInterface();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getAnnotations();
    }

    @Override
    public void onStop() {
        super.onStop();

        database.removeEventListener(valueEventListener);
    }

    public void configInterface() {

        btnSaveData.setOnClickListener(view -> {

            Snackbar.make(view, "Deseja salvar está nota?", BaseTransientBottomBar.LENGTH_SHORT)
                    .setBackgroundTint(getResources().getColor(R.color.colorBackSnack))
                    .setTextColor(getResources().getColor(R.color.colorTextSnack))
                    .setAction("CONFIRMAR", action -> salvarAnotacao())
                    .show();

        });

    }

    public void getAnnotations() {

        DatabaseReference data = database.child("User").child("Profile").child(idCurrentUser).child("Anotations");
        valueEventListener = data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue() != null) {
                    User u = snapshot.getValue(User.class);

                    editAnnotation.setText(u.getNotes());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ERROR", error.getMessage());
            }
        });

    }

    public void salvarAnotacao() {

        String note = editAnnotation.getText().toString();

        if (!note.isEmpty()) {

            User u = new User();
            u.setIdUser(idCurrentUser);
            u.setNotes(note);
            u.saveUpdateAnnotations();

        } else {
            Toast.makeText(getContext(), "Ops! Faça uma anotação primeiro", Toast.LENGTH_SHORT).show();
        }

    }

    public void iniComponentes(View v) {

        editAnnotation = v.findViewById(R.id.editTextAnotacao);
        btnSaveData = v.findViewById(R.id.fabSalvarAnotacao);

        idCurrentUser = UserFirebase.getIdUser();
        database = ConfigFirebase.getFirebaseDatabase();
    }

}