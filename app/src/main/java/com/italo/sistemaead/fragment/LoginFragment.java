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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.italo.sistemaead.R;
import com.italo.sistemaead.activity.MainActivity;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.model.User;


public class LoginFragment extends Fragment {

    private EditText editEmail, editPassword;
    private Button btnContinue;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        iniComponentes(view);
        configInterface();

        return view;
    }

    public void configInterface() {

        btnContinue.setOnClickListener((v) -> validateDataUser());

    }

    public void validateDataUser() {

        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        if (!email.isEmpty()) {
            if (!password.isEmpty()) {

                btnContinue.setText("");
                progressBar.setVisibility(View.VISIBLE);

                User u = new User();
                u.setEmail(email);
                u.setPassword(password);

                logarUsuario(u);

            } else {
                snackBar("Digite sua senha!");
            }
        } else {
            snackBar("Qual seu endereço de e-mail?");
        }

    }

    public void logarUsuario(final User user) {

        auth.signInWithEmailAndPassword(
                user.getEmail(),
                user.getPassword())
                .addOnCompleteListener(getActivity(), (task -> {

                    if (task.isSuccessful()) {

                        Intent i = new Intent(getContext(), MainActivity.class);
                        startActivity(i);

                    } else {

                        btnContinue.setText("CONTINUAR");
                        progressBar.setVisibility(View.GONE);

                        String message;

                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            message = "Ops! Cadastro não encontrado.";
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            message = "Ops! E-mail ou senha não corresponde ao usuário.";
                        } catch (Exception e) {
                            message = "Ops! Erro ao realizar o login. Tente novamente!";
                            e.printStackTrace();
                        }
                        snackBar(message);

                    }

                }));

    }

    public void snackBar(String message) {

        Snackbar.make(getView(), message, BaseTransientBottomBar.LENGTH_SHORT)
                .setBackgroundTint(getResources().getColor(R.color.colorBackSnack))
                .setTextColor(getResources().getColor(R.color.colorTextSnack))
                .show();

    }

    public void iniComponentes(View v) {

        editEmail = v.findViewById(R.id.editEmailLogin);
        editPassword = v.findViewById(R.id.editSenhaLogin);
        btnContinue = v.findViewById(R.id.btnContinuarLogin);
        progressBar = v.findViewById(R.id.progressBarLogin);

        auth = ConfigFirebase.getFirebaseAuth();


    }


}