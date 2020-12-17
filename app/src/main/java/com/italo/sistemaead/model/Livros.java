package com.italo.sistemaead.model;

public class Livros {

    private String idLivro;
    private String tituloLivro;
    private String descricaoLivro;

    public Livros(){
    }

    public Livros(String idLivro, String tituloLivro, String descricaoLivro){

        this.idLivro = idLivro;
        this.tituloLivro = tituloLivro;
        this.descricaoLivro = descricaoLivro;

    }

    public String getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(String idLivro) {
        this.idLivro = idLivro;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public String getDescricaoLivro() {
        return descricaoLivro;
    }

    public void setDescricaoLivro(String descricaoLivro) {
        this.descricaoLivro = descricaoLivro;
    }
}
