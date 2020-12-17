package com.italo.sistemaead.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.italo.sistemaead.R;
import com.italo.sistemaead.activitys.MainActivity;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.model.Usuario;


public class LoginFragment extends Fragment {

    private EditText campoEmail, campoSenha;
    private Button botaoContinuar;
    private ProgressBar progressBar;
    private FirebaseAuth autenticacao;

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

        botaoContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validarLogin();

            }
        });

    }

    public void validarLogin() {

        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                progressBar.setVisibility(View.VISIBLE);

                Usuario usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setSenha(senha);

                logarUsuario(usuario);

            } else {
                snackBar("Digite sua senha!");
            }
        } else {
            snackBar("Qual seu endereço de e-mail?");
        }

    }

    public void logarUsuario(final Usuario usuario) {

        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    Intent i = new Intent(getContext(), MainActivity.class);
                    startActivity(i);

                } else {

                    progressBar.setVisibility(View.GONE);

                    String mensagem = "Mensagem";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        mensagem = "Ops! Cadastro não encontrado.";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        mensagem = "Ops! E-mail ou senha não corresponde ao usuário.";
                    } catch (Exception e) {
                        mensagem = "Ops! Erro ao realizar o login. Tente novamente! ERRO: " + e.getMessage();
                        Log.d("ERRO LOGIN", e.getMessage());
                        e.printStackTrace();
                    }
                    snackBar(mensagem);

                }

            }
        });

    }

    public void snackBar(String mensagem) {

        Snackbar.make(getView(), mensagem, BaseTransientBottomBar.LENGTH_SHORT).show();

    }

    public void iniComponentes(View v) {

        campoEmail = v.findViewById(R.id.editEmailLogin);
        campoSenha = v.findViewById(R.id.editSenhaLogin);
        botaoContinuar = v.findViewById(R.id.btnContinuarLogin);
        progressBar = v.findViewById(R.id.progressBarLogin);

        autenticacao = ConfigFirebase.getFirebaseAuth();

    }

}