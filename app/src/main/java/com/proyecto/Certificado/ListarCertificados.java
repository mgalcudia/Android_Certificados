package com.proyecto.Certificado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyecto.Certificado.modelo.Certificado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListarCertificados extends AppCompatActivity {
    private List <Certificado> listaCertificado = new ArrayList<Certificado>();
    ArrayAdapter<Certificado> arrayAdapterCertificado;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView listViewCertificado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_certificados);
        FirebaseApp.initializeApp(this);
         firebaseDatabase = FirebaseDatabase.getInstance();
         databaseReference = firebaseDatabase.getReference();
         listViewCertificado = findViewById(R.id.listaDatosCertificados);

         listarcertificado();
    }

    private void listarcertificado() {
        databaseReference.child("certificado").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaCertificado.clear();
                for (DataSnapshot objSnaptshot: dataSnapshot.getChildren()){
                    Certificado c= objSnaptshot.getValue(Certificado.class);
                    listaCertificado.add(c);
                    arrayAdapterCertificado = new ArrayAdapter<Certificado>
                        (ListarCertificados.this,android.R.layout.simple_list_item_activated_1,listaCertificado);
                    listViewCertificado.setAdapter(arrayAdapterCertificado);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
