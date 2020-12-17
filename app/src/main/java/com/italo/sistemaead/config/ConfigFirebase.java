package com.italo.sistemaead.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfigFirebase {

    private static FirebaseAuth auth;
    private static DatabaseReference database;
    private static StorageReference storage;


    public static FirebaseAuth getFirebaseAuth() { // -> Recuperando instancia do auth
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }


    public static DatabaseReference getFirebaseDatabase() {  // -> Recuperando instancia e referencia do database
        if (database == null) {
            database = FirebaseDatabase.getInstance().getReference();
        }
        return database;
    }


    public static StorageReference getFirebaseStorage() { // -> Recuperando instancia e referencia do storage
        if (storage == null) {
            storage = FirebaseStorage.getInstance().getReference();
        }
        return storage;
    }


}
