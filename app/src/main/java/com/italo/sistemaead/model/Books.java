package com.italo.sistemaead.model;

public class Books {

    private String idLivro;
    private String tituloLivro;
    private String descricaoLivro;
    private String urlLivro;

    public Books(){
    }

    public Books(String idLivro, String tituloLivro, String descricaoLivro, String urlLivro){

        this.idLivro = idLivro;
        this.tituloLivro = tituloLivro;
        this.descricaoLivro = descricaoLivro;
        this.urlLivro = urlLivro;

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

    public String getUrlLivro() {
        return urlLivro;
    }

    public void setUrlLivro(String urlLivro) {
        this.urlLivro = urlLivro;
    }
}
