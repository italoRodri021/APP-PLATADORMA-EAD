package com.italo.sistemaead.model;

import com.google.firebase.database.DatabaseReference;
import com.italo.sistemaead.config.ConfigFirebase;

import java.util.HashMap;
import java.util.Map;

public class Usuario {

    private String id;
    private String nome;
    private String matricula;
    private String email;
    private String senha;
    private String anotacoes;
    private String notaAvaliacao;
    private String obsAvaliacao;
    private String statusAvaliacao;

    public Usuario() {
    }

    public void salvarDadosCadastro() {

        DatabaseReference database = ConfigFirebase.getFirebaseDatabase()
                .child("Usuarios")
                .child("Perfil")
                .child(getId());; // -> Salvando dados do usuario
        database.setValue(this);
    }


    public void salvarAnotacao() {

        DatabaseReference database = ConfigFirebase.getFirebaseDatabase() // -> Atualizando anotacao
                .child("Usuarios")
                .child("Perfil")
                .child(getId());

        Map<String, Object> dadosAnotacao = converterMapUsuario();
        database.updateChildren(dadosAnotacao);

    }

    public Map<String, Object> converterMapUsuario() { // -> Map da anotacao

        HashMap<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("anotacoes", getAnotacoes());

        return map;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(String anotacoes) {
        this.anotacoes = anotacoes;
    }

    public String getNotaAvaliacao() {
        return notaAvaliacao;
    }

    public void setNotaAvaliacao(String notaAvaliacao) {
        this.notaAvaliacao = notaAvaliacao;
    }

    public String getObsAvaliacao() {
        return obsAvaliacao;
    }

    public void setObsAvaliacao(String obsAvaliacao) {
        this.obsAvaliacao = obsAvaliacao;
    }

    public String getStatusAvaliacao() {
        return statusAvaliacao;
    }

    public void setStatusAvaliacao(String statusAvaliacao) {
        this.statusAvaliacao = statusAvaliacao;
    }
}
