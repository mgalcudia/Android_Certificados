package com.proyecto.Certificado;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class AgregarActivity extends AppCompatActivity {
    //Iniciamos variable fechafinal
    private EditText fechaFin;
    private int dia,mes,anio;
    //variables numberPicker
    private NumberPicker numberPicker;
    String strnumberpicker;
    //variables titulo
    private EditText nombreCertificado, entidadEmisora, horasCertificado, creditosCertificado,fechaFinCertificado;

    String strnombreCertificado, strentidadEmisora,strhorasCertificado,strcreditosCertificado,srtfechaFinCertificado;
    //variables Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    //Creacion de listener para el datepicker
    private DatePickerDialog.OnDateSetListener listenerDatepicker= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            //Se llama cuando se selecciona la fecha. Nos pasa la vista y asignamos valor a las variables
            anio= year;
            mes= month;
            dia= dayOfMonth;
            //Refrescamos la fecha
            refrescarFechaEnEditText();

        }
    };


    public void refrescarFechaEnEditText(){
        final Calendar calendario= Calendar.getInstance();
        dia= calendario.get(Calendar.DAY_OF_MONTH);
        mes= calendario.get(Calendar.MONTH);
        anio= calendario.get(Calendar.YEAR);
        String fecha= String.format(Locale.getDefault(),"%02d-%02d-%02d", dia, mes+1, anio);
        //asigamos el valor del editText

        fechaFin.setText(fecha);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        //obtenemos el valor de nombre certificado
        nombreCertificado= findViewById(R.id.editTextTituloCertificado);
        entidadEmisora= findViewById(R.id.editTextEntidadCertificado);
        horasCertificado = findViewById(R.id.editTextHorasCertificado);
        creditosCertificado =findViewById(R.id.editTextCreditoCertificado);
        fechaFinCertificado= findViewById(R.id.editTextFechaFin);

        //instanciamos el objeto
        fechaFin= findViewById(R.id.editTextFechaFin);
        //Asignamos la fecha de hoy al editText
        /*
        final Calendar calendario= Calendar.getInstance();
        dia= calendario.get(Calendar.DAY_OF_MONTH);
        mes= calendario.get(Calendar.MONTH);
        anio= calendario.get(Calendar.YEAR);
*/

        //Asignamos la fecha de hoy al EditText
        refrescarFechaEnEditText();
        numberpicker();

        //hacemos referencia a firebase
        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        //Hace que el datepicker se muestre al clickear el edixText

        fechaFin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DatePickerDialog dialogoDeFecha = new DatePickerDialog(AgregarActivity.this,listenerDatepicker,anio,mes,dia);

                //Muestra

                dialogoDeFecha.show();
            }
        });

    }

    private void numberpicker() {

        final Calendar calendar= Calendar.getInstance();
        //anio= calendar.get(Calendar.YEAR);
        numberPicker= findViewById(R.id.numberPickerAnoCorte);
        numberPicker.setMinValue(2000);
        numberPicker.setMaxValue(anio);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //texto.setText("Valor anterior: "+i+" Valor Nuevo: "+i1);

                strnumberpicker =String.valueOf(numberPicker.getValue());

            }
        });

    }

    public void AgregarUnTitulo(View view) {
        strnombreCertificado=nombreCertificado.getText().toString().trim();
        strentidadEmisora=entidadEmisora.getText().toString().trim();
        strhorasCertificado= horasCertificado.getText().toString().trim();
        strcreditosCertificado = creditosCertificado.getText().toString().trim();
        srtfechaFinCertificado=fechaFinCertificado.getText().toString().trim();
        final String id = mAuth.getCurrentUser().getUid();

        //mapa de valores
        Map<String, Object> map= new HashMap<>();
        map.put("idUser", id);
        map.put("nombreCertificado", strnombreCertificado);
        map.put("entidadEmisora", strentidadEmisora);
        map.put("horasCertificado", strhorasCertificado);
        map.put("anioCorte", strnumberpicker);
        map.put("creditosCertificado", strcreditosCertificado);
        map.put("fechaFinCertificado", srtfechaFinCertificado);

        if(!strnombreCertificado.isEmpty() && !strentidadEmisora.isEmpty() && !strhorasCertificado.isEmpty()
                && !strcreditosCertificado.isEmpty() && !srtfechaFinCertificado.isEmpty() ){
            final String idCertificado= (UUID.randomUUID().toString());
            mDatabase.child("certificado").child(idCertificado).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task2) {
                    if(task2.isSuccessful()){
                        Map<String, Object> map2= new HashMap<>();
                        map2.put("idUser", id);
                        map2.put("idCertificado", idCertificado);
                        map2.put("anioCorte", strnumberpicker);
                        String idhistorico= (UUID.randomUUID().toString());
                        mDatabase.child("historicoCorte").child(idhistorico).setValue(map2);
                        Toast.makeText(AgregarActivity.this, "Usuario Registado", Toast.LENGTH_LONG).show();
                        nombreCertificado.setText("");
                        entidadEmisora.setText("");
                        horasCertificado.setText("");
                        creditosCertificado.setText("");
                        refrescarFechaEnEditText();

                    }else {
                        Toast.makeText(AgregarActivity.this,
                                "No se puede ir a la otra actividad", Toast.LENGTH_LONG).show();
                    }
                }
            });



        }


/*
            mDatabase.child("certificado").push(map).addOnCompleteListener(new OnCompleteListener<>() {
                @Override
                public void onComplete(@NonNull Task task) {

                }
            });
*/


    }
}
