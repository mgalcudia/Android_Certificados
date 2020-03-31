package com.proyecto.Certificado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InicioActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        mAuth= FirebaseAuth.getInstance();
    }


    public void AgregarTitulo(View view) {
        startActivity(new Intent(InicioActivity.this, AgregarActivity.class));
    }

    public void listarTitulos(View view) {
        startActivity(new Intent(InicioActivity.this, ListarCertificados.class));
    }

    public void buscarCertificados(View view) {
      //  startActivity(new Intent(InicioActivity.this, AgregarActivity.class));
    }

    public void cerrarSesion(View view) {
        Toast.makeText(InicioActivity.this,
                "Cerramos Sesion", Toast.LENGTH_LONG).show();
        mAuth.signOut();
        startActivity(new Intent(InicioActivity.this,MainActivity.class));
        finish();

    }
}
