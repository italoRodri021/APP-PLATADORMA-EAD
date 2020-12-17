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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.italo.sistemaead.R;
import com.italo.sistemaead.activitys.BoasVindasActivity;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.model.Usuario;

public class CadastroFragment extends Fragment {

    private EditText campoNome, campoMatricula, campoEmail, campoSenha, campoConfirmar;
    private Button botaoContinuar;
    private FirebaseAuth autenticacao;
    private ProgressBar progressBar;

    public CadastroFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro, container, false);

        iniComponentes(view);
        configInteface();

        return view;
    }

    public void configInteface() {

        botaoContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validarCadastro();

            }
        });

    }

    public void validarCadastro() {

        String nome = campoNome.getText().toString();
        String matricula = campoMatricula.getText().toString();
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();
        String confirmar = campoConfirmar.getText().toString();

        String status = "Prova Bloqueada";
        String anotacoes = "Anotações";
        String nota = "Nota";
        String obsAvaliacao = "Observações";

        if (!nome.isEmpty()) {
            if (!matricula.isEmpty()) {
                if (!email.isEmpty()) {
                    if (!senha.isEmpty()) {
                        if (nome.length() >= 15) {
                            if (confirmar.equals(senha)) {

                                progressBar.setVisibility(View.VISIBLE);

                                Usuario usuario = new Usuario();
                                usuario.setNome(nome);
                                usuario.setMatricula(matricula);
                                usuario.setEmail(email);
                                usuario.setSenha(senha);
                                usuario.setStatusAvaliacao(status);
                                usuario.setAnotacoes(anotacoes);
                                usuario.setNotaAvaliacao(nota);
                                usuario.setObsAvaliacao(obsAvaliacao);

                                cadastrarUsuario(usuario);

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

    public final void cadastrarUsuario(final Usuario usuario) {

        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        try {

                            String id = task.getResult().getUser().getUid();
                            usuario.setId(id);
                            usuario.salvarDadosCadastro();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (task.isSuccessful()) {

                            Intent i = new Intent(getContext(), BoasVindasActivity.class);
                            i.putExtra("NOME_USUARIO_CADASTRADO", usuario.getNome());
                            startActivity(i);

                        } else {

                            progressBar.setVisibility(View.GONE);

                            String mensagem = "Mensagem";

                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                mensagem = "Ops! Por favor defina uma senha mais forte.";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                mensagem = "Ops! Por favor defina um e-mail válido.";
                            } catch (FirebaseAuthUserCollisionException e) {
                                mensagem = "Ops! Já existe uma conta cadastrada com esse e-mail!, Por favor defina um novo e-mail.";
                            } catch (Exception e) {
                                mensagem = "Ops! Erro ao realizar o cadastro. Tente mais tarde! ERRO: " + e.getMessage();
                                Log.d("ERRO CADASTRO", e.getMessage());
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

        campoNome = v.findViewById(R.id.editNomeCadastro);
        campoMatricula = v.findViewById(R.id.editMatriculaCadastro);
        campoEmail = v.findViewById(R.id.editEmailCadastro);
        campoSenha = v.findViewById(R.id.editSenhaCadastro);
        campoConfirmar = v.findViewById(R.id.editConfSenhaCadastro);
        botaoContinuar = v.findViewById(R.id.btnContinuarCadastro);
        progressBar = v.findViewById(R.id.progressBarCadastro);

        autenticacao = ConfigFirebase.getFirebaseAuth();

    }

}