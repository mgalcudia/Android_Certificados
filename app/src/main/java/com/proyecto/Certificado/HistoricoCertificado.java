package com.proyecto.Certificado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
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
import java.util.Objects;

public class HistoricoCertificado extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    final ArrayList<String> anios = new ArrayList<>();

    private NumberPicker numberPicker;
    String strnumberpicker, stranioselecionado;
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

    private void obtenerAniosCorte() {

        String idUSer = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        databaseReference.child("historicoCorte/" + idUSer).orderByChild("anioCorte").addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    String  c = objSnaptshot.child("anioCorte").getValue(String.class);

                    if(!anios.contains(c)){

                        anios.add(c);
                    }


                }
                arrayAdapterCertificado = new ArrayAdapter<>
                (HistoricoCertificado.this,android.R.layout.simple_list_item_activated_1, anios);
                listViewCertificado.setAdapter(arrayAdapterCertificado);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }

    private void numberpicker(String[] contador) {

        numberPicker.setTextSize(55);
        numberPicker.setDisplayedValues(contador);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                strnumberpicker =String.valueOf(numberPicker.getValue());

            }
        });

    }

}
