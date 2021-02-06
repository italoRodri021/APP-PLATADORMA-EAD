package com.italo.sistemaead.model;

import com.google.firebase.database.DatabaseReference;
import com.italo.sistemaead.config.ConfigFirebase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    String idUser;
    String name;
    String registration;
    String email;
    String password;
    String notes;
    String noteEvaluetion;
    String obsEvaluetion;
    String statusEvaluetion;
    String urlPhoto;
    boolean blockEvaluetion;

    public void saveDataUser() {

        DatabaseReference database = ConfigFirebase.getFirebaseDatabase();

        Map<String, Object> map = new HashMap<>();
        map.put("idUser", getIdUser());
        map.put("name", getName());
        map.put("registration", getRegistration());
        map.put("email", getEmail());
        map.put("password", getPassword());
        map.put("notes", getNoteEvaluetion());
        map.put("obsEvaluetion", getObsEvaluetion());
        map.put("statusEvaluetion", getStatusEvaluetion());
        map.put("blockEvaluetion", blockEvaluetion);

        database.child("User")
                .child("Profile")
                .child(getIdUser())
                .updateChildren(map);
    }

    public void updatePhotoUser(){

        DatabaseReference database = ConfigFirebase.getFirebaseDatabase();

        Map<String, Object> map = new HashMap<>();
        map.put("urlPhoto",getUrlPhoto());

        database.child("User")
                .child("Profile")
                .child(getIdUser())
                .updateChildren(map);
    }

    public void saveUpdateAnnotations(){

        DatabaseReference database = ConfigFirebase.getFirebaseDatabase();

        HashMap<String, Object> map = new HashMap<>();;
        map.put("notes",getNotes());

        database .child("User").child("Profile").child(getIdUser()).child("Anotations").updateChildren(map);

    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNoteEvaluetion() {
        return noteEvaluetion;
    }

    public void setNoteEvaluetion(String noteEvaluetion) {
        this.noteEvaluetion = noteEvaluetion;
    }

    public String getObsEvaluetion() {
        return obsEvaluetion;
    }

    public void setObsEvaluetion(String obsEvaluetion) {
        this.obsEvaluetion = obsEvaluetion;
    }

    public String getStatusEvaluetion() {
        return statusEvaluetion;
    }

    public void setStatusEvaluetion(String statusEvaluetion) {
        this.statusEvaluetion = statusEvaluetion;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public boolean isBlockEvaluetion() {
        return blockEvaluetion;
    }

    public void setBlockEvaluetion(boolean blockEvaluetion) {
        this.blockEvaluetion = blockEvaluetion;
    }
}
