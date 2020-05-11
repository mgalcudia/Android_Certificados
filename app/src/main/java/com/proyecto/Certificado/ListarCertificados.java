package com.proyecto.Certificado;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyecto.Certificado.modelo.Certificados;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ListarCertificados extends AppCompatActivity {

    private List <Certificados> listaCertificados = new ArrayList<>();
    ArrayAdapter<Certificados> arrayAdapterCertificado;
    Certificados certificadosSeleccionado;
    //variables firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    ListView listViewCertificado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_certificados);
        FirebaseApp.initializeApp(this);
         firebaseDatabase = FirebaseDatabase.getInstance();
         databaseReference = firebaseDatabase.getReference();
          mAuth= FirebaseAuth.getInstance();

         listViewCertificado = findViewById(R.id.listaDatosCertificados);
         listarcertificado();

         listViewCertificado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  certificadosSeleccionado = (Certificados) parent.getItemAtPosition(position);
                 Intent intent= new Intent(ListarCertificados.this,MostrarCertificado.class);
                 Bundle bundle= new Bundle();
                 bundle.putSerializable("certificado",certificadosSeleccionado);
                 intent.putExtras(bundle);
                 startActivity(intent);
             }
         });



    }

    /**
     * Funcion para listar los certificados del usuario ordenados por el año de corte de mas reciente a mas antiguo
     */
    private void listarcertificado() {
        String idUSer= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        databaseReference.child("certificado/"+idUSer).orderByChild("anioCorte").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaCertificados.clear();
                for (DataSnapshot objSnaptshot: dataSnapshot.getChildren()){
                    Certificados c= objSnaptshot.getValue(Certificados.class);
                    listaCertificados.add(c);
                    //invierte el orden de la lista
                    Collections.reverse(listaCertificados);
                    arrayAdapterCertificado = new ArrayAdapter<>
                            (ListarCertificados.this, android.R.layout.simple_list_item_activated_1, listaCertificados);
                    listViewCertificado.setAdapter(arrayAdapterCertificado);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     *  Al crear la actividad se inyecta el menu personalizado
     * @param menu
     * @return boolean;
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    /**
     *   Funcion para  identificar el item seleccionado por el usuario en el menu superior
     *   personalizado.
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.atras:{
                onBackPressed();
                break;

            }

        }
        return true;
    }

    /**
     * Funcion que nos devuelve a la actividad anterior
     * internamente hace un finish de la actividad actual
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
