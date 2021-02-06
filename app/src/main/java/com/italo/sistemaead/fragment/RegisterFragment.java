package com.italo.sistemaead.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.italo.sistemaead.R;
import com.italo.sistemaead.activity.WelcomeActivity;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.model.User;

public class RegisterFragment extends Fragment {

    private EditText editName, editRegistration, editEmail, editPassword, editConfPassword;
    private Button btnContinue;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        iniComponentes(view);
        configInteface();

        return view;
    }

    public void configInteface() {

        btnContinue.setOnClickListener((v) -> validateDataUser());

    }

    public void validateDataUser() {

        String name = editName.getText().toString();
        String registration = editRegistration.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        String confimPass = editConfPassword.getText().toString();

        if (!name.isEmpty()) {
            if (!registration.isEmpty()) {
                if (!email.isEmpty()) {
                    if (!password.isEmpty()) {
                        if (name.length() >= 15) {
                            if (confimPass.equals(password)) {

                                btnContinue.setText("");
                                progressBar.setVisibility(View.VISIBLE);
                                User u = new User();
                                u.setName(name);
                                u.setRegistration(registration);
                                u.setEmail(email);
                                u.setPassword(password);
                                u.setStatusEvaluetion("Prova Bloqueada");
                                u.setNotes("Anotações");
                                u.setNoteEvaluetion("Nota");
                                u.setObsEvaluetion("Observações");

                                registerUser(u);

                            } else {
                                snackBar("As senhas não batem!");
                            }
                        } else {
                            snackBar("Preciso do seu nome completo!");
                        }
                    } else {
                        snackBar("Digite sua senha!");
                    }
                } else {
                    snackBar("Qual seu endereço de e-mail?");
                }
            } else {
                snackBar("Qual sua matricula?");
            }
        } else {
            snackBar("Qual seu nome?");
        }

    }

    public final void registerUser(final User user) {

        auth.createUserWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(getActivity(), task -> {

            try {

                String id = task.getResult().getUser().getUid();
                user.setIdUser(id);
                user.saveDataUser();

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (task.isSuccessful()) {

                Intent i = new Intent(getContext(), WelcomeActivity.class);
                i.putExtra("NAME_USER", user.getName());
                startActivity(i);

            } else {

                btnContinue.setText("CONTINUAR CADASTRO");
                progressBar.setVisibility(View.GONE);
                String message;

                try {
                    throw task.getException();
                } catch (FirebaseAuthWeakPasswordException e) {
                    message = "Ops! Por favor defina uma senha mais forte.";
                } catch (FirebaseAuthInvalidCredentialsException e) {
                    message = "Ops! Por favor defina um e-mail válido.";
                } catch (FirebaseAuthUserCollisionException e) {
                    message = "Ops! Já existe uma conta cadastrada com esse e-mail!, Por favor defina um novo e-mail.";
                } catch (Exception e) {
                    message = "Ops! Erro ao realizar o cadastro. Tente mais tarde!";
                    e.printStackTrace();
                }
                snackBar(message);

            }

        });

    }

    public void snackBar(String message) {

        Snackbar.make(getView(), message, BaseTransientBottomBar.LENGTH_SHORT)
                .setBackgroundTint(getResources().getColor(R.color.colorBackSnack))
                .setTextColor(getResources().getColor(R.color.colorTextSnack))
                .show();

    }

    public void iniComponentes(View v) {

        editName = v.findViewById(R.id.editNomeCadastro);
        editRegistration = v.findViewById(R.id.editMatriculaCadastro);
        editEmail = v.findViewById(R.id.editEmailCadastro);
        editPassword = v.findViewById(R.id.editSenhaCadastro);
        editConfPassword = v.findViewById(R.id.editConfSenhaCadastro);
        btnContinue = v.findViewById(R.id.btnContinuarCadastro);
        progressBar = v.findViewById(R.id.progressBarCadastro);

        auth = ConfigFirebase.getFirebaseAuth();

    }

}