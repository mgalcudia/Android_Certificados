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

import java.util.ArrayList;
import java.util.Objects;

public class HistoricoCertificado extends AppCompatActivity {

    //variables Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    //Lista para almacenar los diferentes años de corte
    final ArrayList<String> anios = new ArrayList<>();
    //variables para mostrar la lista de años y mandar la seleccionada a otra actividad
    String stranioselecionado;
    ListView listViewCertificado;
    ArrayAdapter<String> arrayAdapterCertificado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_certificado);
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth= FirebaseAuth.getInstance();
        listViewCertificado = findViewById(R.id.listaDatosCertificados);
        obtenerAniosCorte();

        listViewCertificado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                stranioselecionado = (String) parent.getItemAtPosition(position);
                Intent intent= new Intent(HistoricoCertificado.this,ListarHistoricos.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("anio",stranioselecionado);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });

    }

    /**
     * Funcion que obtiene los años de corte que tiene asociado algun certificado
     */
    private void obtenerAniosCorte() {

        String idUSer = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        databaseReference.child("historicoCorte/" + idUSer).orderByChild("anioCorte").addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    String  c = objSnaptshot.child("anioCorte").getValue(String.class);
                    //si hay algun año agregado no lo vuelve a agregar
                    if(!anios.contains(c)){
                        anios.add(c);
                    }
                    arrayAdapterCertificado = new ArrayAdapter<>
                            (HistoricoCertificado.this,android.R.layout.simple_list_item_activated_1, anios);
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
