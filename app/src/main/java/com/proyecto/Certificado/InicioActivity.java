package com.proyecto.Certificado;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class InicioActivity extends AppCompatActivity {
    //variables de firebase
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        mAuth= FirebaseAuth.getInstance();

    }


    /**
     * Funcion que envia al usuario a la activity para agregar certificados
     * @param view
     */
    public void AgregarTitulo(View view) {
        startActivity(new Intent(InicioActivity.this, AgregarActivity.class));
    }

    /**
     * Funcion que envia al usuario a la activity para listar certificados
     * @param view
     */
    public void listarTitulos(View view) {
        startActivity(new Intent(InicioActivity.this, ListarCertificados.class));
    }

    /**
     * Funcion que envia al usuario a la activity para buscar certificados
     * @param view
     */
    public void buscarCertificados(View view) {
        startActivity(new Intent(InicioActivity.this, BuscarCertificado.class));
    }

    /**
     * Funcion que envia al usuario a la activity para mostrar el historico certificados
     * @param view
     */
    public void irHistorico(View view) {
        startActivity(new Intent(InicioActivity.this, HistoricoCertificado.class));
    }

    /**
     * Funcion para cerrar la sesion del usuario, envia al usuario a la primera activity
     * haciendo un finish() de esta
     * @param view
     */
    public void cerrarSesion(View view) {
        Toast.makeText(InicioActivity.this,
                R.string.sesionCerrada, Toast.LENGTH_LONG).show();
        mAuth.signOut();
        startActivity(new Intent(InicioActivity.this,MainActivity.class));
        finish();

    }


}
