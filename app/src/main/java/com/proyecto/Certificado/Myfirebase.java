package com.proyecto.Certificado;

import com.google.firebase.database.FirebaseDatabase;

public class Myfirebase extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //activa la persistencia de datos de firebase
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
