package com.proyecto.Certificado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Toast;

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

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    final ArrayList<String> anios = new ArrayList<>();
     String[] contador;
    private NumberPicker numberPicker;
    String strnumberpicker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_certificado);

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth= FirebaseAuth.getInstance();
        obtenerAniosCorte();

    }

    private void obtenerAniosCorte() {

        String idUSer = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        databaseReference.child("historicoCorte/" + idUSer).orderByChild("anioCorte").addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    String  c = objSnaptshot.child("anioCorte").getValue(String.class);
                    anios.add(c);

                }
                contador = new String[anios.size()];
                contador= anios.toArray(contador);
                int i = 0;
                for (String s : anios){
                    contador[i]=s;
                    i++;
                    Toast.makeText(HistoricoCertificado.this, "Contador es"+s, Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(HistoricoCertificado.this, "Contador es"+contador[0], Toast.LENGTH_SHORT).show();
                numberpicker(contador);
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
