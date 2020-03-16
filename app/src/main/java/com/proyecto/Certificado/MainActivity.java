package com.proyecto.Certificado;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth= FirebaseAuth.getInstance();

    }

    public void irRegistrar(View view) {

        startActivity(new Intent(MainActivity.this, RegistroActivity.class));
    }

    public void irLoguin(View view) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    @Override
    protected void onStart() {

        super.onStart();
        if(mAuth.getCurrentUser() !=null){

            startActivity(new Intent(MainActivity.this,InicioActivity.class));
            finish();
        }
    }
}