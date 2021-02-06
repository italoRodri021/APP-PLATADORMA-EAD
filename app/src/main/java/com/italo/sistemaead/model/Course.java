package com.italo.sistemaead.model;

import com.google.firebase.database.DatabaseReference;
import com.italo.sistemaead.config.ConfigFirebase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Course implements Serializable {

    private String idUser;
    private String idCourse;
    private String title;
    private String description;
    private String titleSearch;
    private String urlPhoto;

    public void saveCourse(){

        DatabaseReference database = ConfigFirebase.getFirebaseDatabase();

        Map<String, Object> map = new HashMap<>();
        map.put("idCourse",getIdCourse());
        map.put("title",getTitle());
        map.put("titleSearch",getTitleSearch());
        map.put("description",getDescription());
        map.put("urlPhoto",getUrlPhoto());

        database.child("User")
                .child("Profile")
                .child(getIdUser())
                .child("CourseUser")
                .child(getIdCourse())
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitleSearch() {
        return titleSearch;
    }

    public void setTitleSearch(String titleSearch) {
        this.titleSearch = titleSearch;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }
}
