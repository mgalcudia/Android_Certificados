package com.proyecto.Certificado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.proyecto.Certificado.modelo.Certificados;

import java.util.ArrayList;
import java.util.List;

public class BuscarCertificado extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ArrayList<Certificados>listaCertificado= new ArrayList<>();
    ListView listViewCertificado;
    ArrayList<String>lista= new ArrayList<>();
    ArrayAdapter<String> arrayAdapterlista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_certificado);
        listViewCertificado = findViewById(R.id.listviewBuscarCertificado);
    }

    public boolean onCreateOptionsMenu(Menu menu){
      getMenuInflater().inflate(R.menu.menu_buscador,menu);
        MenuItem item= menu.findItem(R.id.buscador);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        Toast.makeText(BuscarCertificado.this,"Texto"+query,Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try {
          //  Toast.makeText(BuscarCertificado.this,"Texto"+newText,Toast.LENGTH_LONG).show();

           // ArrayList<Certificados>listaFiltrada= filter(listaCertificado,newText);

        }catch (Exception e){

            e.printStackTrace();
        }
        return false;

    }

    private ArrayList<Certificados> filter(ArrayList<Certificados> certificados, String texto){

    ArrayList<Certificados> listafiltrada= new ArrayList<>();
        try {
            //texto que queremos filtrar en la lista
            texto= texto.toLowerCase();

            for(Certificados certificado : certificados){

                String certificado1 = certificado.getNombreCertificado().toLowerCase();

                if(certificado1.contains(texto)){
                    listafiltrada.add(certificado);
                }
            }
        }catch (Exception e){

            e.printStackTrace();
        }

        return listafiltrada;
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
