package com.proyecto.Certificado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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


    private List<Certificados> listaCertificados = new ArrayList<Certificados>();
    ArrayAdapter<Certificados> arrayAdapterCertificado;
    Certificados certificadosSeleccionado;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    ListView listViewCertificado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_historicos);


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

                String idcertificado= certificadosSeleccionado.getIdCertificado();
                Toast.makeText(ListarHistoricos.this, "Cursoe"+
                        certificadosSeleccionado.getIdCertificado() , Toast.LENGTH_LONG).show();

                Intent intent= new Intent(ListarHistoricos.this,MostrarCertificado.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("certificado",certificadosSeleccionado);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    private void listarcertificado() {
        String AnioCorteRecibido = Objects.requireNonNull(getIntent().getExtras()).getString("anio");
        Toast.makeText(this, "anio "+AnioCorteRecibido, Toast.LENGTH_SHORT).show();
        String idUSer= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        databaseReference.child("certificado/"+idUSer).orderByChild("anioCorte").equalTo(AnioCorteRecibido).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaCertificados.clear();
                for (DataSnapshot objSnaptshot: dataSnapshot.getChildren()){
                    Certificados c= objSnaptshot.getValue(Certificados.class);
                    listaCertificados.add(c);
                    Toast.makeText(ListarHistoricos.this, "Cursoe"+c , Toast.LENGTH_LONG).show();
                    arrayAdapterCertificado = new ArrayAdapter<Certificados>
                            (ListarHistoricos.this,android.R.layout.simple_list_item_activated_1, listaCertificados);
                    listViewCertificado.setAdapter(arrayAdapterCertificado);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}





