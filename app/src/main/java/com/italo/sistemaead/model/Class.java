package com.italo.sistemaead.model;

import com.google.firebase.database.DatabaseReference;
import com.italo.sistemaead.config.ConfigFirebase;

import java.util.HashMap;
import java.util.Map;

public class Class {

    private String idUser;
    private String idCourse;
    private String idClass;
    private String title;
    private String url;
    private boolean progress;

    public void saveDataClass() {

        DatabaseReference database = ConfigFirebase.getFirebaseDatabase();

        Map<String, Object> map = new HashMap<>();
        map.put("idClass", getIdClass());
        map.put("title", getTitle());
        map.put("url", getUrl());

        database.child("ClassCourse")
                .child(getIdClass())
                .updateChildren(map);
    }


    public void updateProgress(){

        DatabaseReference database = ConfigFirebase.getFirebaseDatabase();

        Map<String, Object> map = new HashMap<>();
        map.put("progress", isProgress());

        database.child("User")
                .child("Profile")
                .child(getIdUser())
                .child("CourseUser")
                .child(getIdCourse())
                .child(getIdClass())
                .updateChildren(map);
    }


    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isProgress() {
        return progress;
    }

    public void setProgress(boolean progress) {
        this.progress = progress;
    }
}
