package com.italo.sistemaead.config;

import com.google.firebase.auth.FirebaseAuth;

public class UserFirebase {

    public static String getIdUser() { // -> Recuperando id do usuario logado

        FirebaseAuth usuarioRef = ConfigFirebase.getFirebaseAuth();
        return usuarioRef.getCurrentUser().getUid();
    }

}
