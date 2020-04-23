package com.proyecto.Certificado;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.List;
import java.util.Objects;

public class ListarHistoricos extends AppCompatActivity {
    //Variables Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

   //Variables para mostrar los certificados del año
    private List<Certificados> listaCertificados = new ArrayList<>();
    ArrayAdapter<Certificados> arrayAdapterCertificado;
    Certificados certificadosSeleccionado;
    ListView listViewCertificado;
    TextView mensaje;
    String anioCorteRecibido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_historicos);
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth= FirebaseAuth.getInstance();
        anioCorteRecibido = Objects.requireNonNull(getIntent().getExtras()).getString("anio");
        listViewCertificado = findViewById(R.id.listaDatosCertificados);

        mensaje= findViewById(R.id.textViewListarhistorico);

        mensaje.setText(getString(R.string.textViewLlistarHistorico,anioCorteRecibido));
        listarcertificado(anioCorteRecibido);

        listViewCertificado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                certificadosSeleccionado = (Certificados) parent.getItemAtPosition(position);

                Intent intent= new Intent(ListarHistoricos.this,MostrarCertificado.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("certificado",certificadosSeleccionado);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    /**
     * Funcion obtiene los certificados asignado a un año de corte que recibe por parametro
     * @param anioCorteRecibido
     */
    private void listarcertificado(String anioCorteRecibido) {

        String idUSer= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        databaseReference.child("certificado/"+idUSer).orderByChild("anioCorte").equalTo(anioCorteRecibido).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaCertificados.clear();
                for (DataSnapshot objSnaptshot: dataSnapshot.getChildren()){
                    Certificados c= objSnaptshot.getValue(Certificados.class);
                    listaCertificados.add(c);
                    arrayAdapterCertificado = new ArrayAdapter<>
                            (ListarHistoricos.this, android.R.layout.simple_list_item_activated_1, listaCertificados);
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





