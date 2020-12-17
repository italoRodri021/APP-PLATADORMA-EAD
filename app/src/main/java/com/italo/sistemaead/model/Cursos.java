package com.italo.sistemaead.model;

public class Cursos {

    private String idCurso;
    private String tituloCurso;
    private String descricaoCurso;
    private String tituloPesquisa;

    public Cursos() {
    }

    public Cursos(String idCurso, String tituloCurso, String descricaoCurso, String tituloPesquisa) {

        this.idCurso = idCurso;
        this.tituloCurso = tituloCurso;
        this.descricaoCurso = descricaoCurso;
        this.tituloPesquisa = tituloPesquisa;

    }

    public String getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(String idCurso) {
        this.idCurso = idCurso;
    }

    public String getTituloCurso() {
        return tituloCurso;
    }

    public void setTituloCurso(String tituloCurso) {
        this.tituloCurso = tituloCurso;
    }

    public String getDescricaoCurso() {
        return descricaoCurso;
    }

    public void setDescricaoCurso(String descricaoCurso) {
        this.descricaoCurso = descricaoCurso;
    }

    public String getTituloPesquisa() {
        return tituloPesquisa;
    }

    public void setTituloPesquisa(String tituloPesquisa) {
        this.tituloPesquisa = tituloPesquisa;
    }

}
