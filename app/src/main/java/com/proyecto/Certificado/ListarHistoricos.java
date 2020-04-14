package com.proyecto.Certificado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class ListarHistoricos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_historicos);

        String objetoEnviado = getIntent().getExtras().getString("anio");
        Toast.makeText(this, "anio "+objetoEnviado, Toast.LENGTH_SHORT).show();
    }
}
