package com.italo.sistemaead.config;

import com.google.firebase.auth.FirebaseAuth;

public class IdUser {

    public static String getIdUser() { // -> Recuperando id do usuario logado

        FirebaseAuth usuarioRef = ConfigFirebase.getFirebaseAuth();
        return usuarioRef.getCurrentUser().getUid();
    }

}
