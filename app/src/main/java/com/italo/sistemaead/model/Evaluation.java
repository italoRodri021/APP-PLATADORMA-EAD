package com.italo.sistemaead.model;

import com.google.firebase.database.DatabaseReference;
import com.italo.sistemaead.config.ConfigFirebase;

import java.util.HashMap;
import java.util.Map;

public class Evaluation {

    String dateHour;
    String urlGoogleForms;

    public void atualizarAvaliacao() {

        DatabaseReference database = ConfigFirebase.getFirebaseDatabase()
                .child("Evaluation");
        ;

        Map<String, Object> dadosAvaliacao = converterMap();
        database.updateChildren(dadosAvaliacao);
    }

    public Map<String, Object> converterMap() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("dateHour", getDateHour());
        map.put("urlGoogleForms", getUrlGoogleForms());

        return map;
    }

    public String getDateHour() {
        return dateHour;
    }

    public void setDateHour(String dateHour) {
        this.dateHour = dateHour;
    }

    public String getUrlGoogleForms() {
        return urlGoogleForms;
    }

    public void setUrlGoogleForms(String urlGoogleForms) {
        this.urlGoogleForms = urlGoogleForms;
    }
}
