package com.italo.sistemaead.model;

import java.util.HashMap;
import java.util.Map;

public class Aulas {

    private String idAulas;
    private String tituloAula;
    private String urlAula;
    private boolean progresso;

    public Aulas() {
    }

    public Aulas(String idAulas, String tituloAula, String urlAula, boolean progresso) {

        this.idAulas = idAulas;
        this.tituloAula = tituloAula;
        this.urlAula = urlAula;
        this.progresso = progresso;

    }

    public Map<String, Object> mapAula() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("idAulas", getIdAulas());
        map.put("progresso", isProgresso());

        return map;
    }

    public String getIdAulas() {
        return idAulas;
    }

    public void setIdAulas(String idAulas) {
        this.idAulas = idAulas;
    }

    public String getTituloAula() {
        return tituloAula;
    }

    public void setTituloAula(String tituloAula) {
        this.tituloAula = tituloAula;
    }

    public String getUrlAula() {
        return urlAula;
    }

    public void setUrlAula(String urlAula) {
        this.urlAula = urlAula;
    }

    public boolean isProgresso() {
        return progresso;
    }

    public void setProgresso(boolean progresso) {
        this.progresso = progresso;
    }
}
