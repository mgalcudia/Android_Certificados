package com.proyecto.Certificado;

import com.google.firebase.database.FirebaseDatabase;

public class Myfirebase extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
