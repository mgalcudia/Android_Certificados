package com.proyecto.Certificado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyecto.Certificado.modelo.Certificados;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class MostrarCertificado extends AppCompatActivity {
    //Iniciamos variable fechafinal
    // EditText fechaFin;
    private int dia, mes, anio;
    //variables numberPicker
    private NumberPicker numberPicker;
    String strnumberpicker;
    //variables titulo
    private EditText nombreCertificado, entidadEmisora, horasCertificado, creditosCertificado, fechaFinCertificado;
    int anioCorte;
    String strnombreCertificado, strentidadEmisora, strhorasCertificado,
            strcreditosCertificado, srtfechaFinCertificado, stridCertificado, strIdUser;

    //variables Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_certificado);
        //obtenemos el valor de nombre certificado
        nombreCertificado = findViewById(R.id.editTextTituloCertificado);
        entidadEmisora = findViewById(R.id.editTextEntidadCertificado);
        horasCertificado = findViewById(R.id.editTextHorasCertificado);
        creditosCertificado = findViewById(R.id.editTextCreditoCertificado);
        fechaFinCertificado = findViewById(R.id.editTextFechaFin);
        numberPicker = findViewById(R.id.numberPickerAnoCorte);
        //hacemos referencia a firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        recibirDatos();
        numberpicker();


        //Hace que el datepicker se muestre al clickear el edixText
        fechaFinCertificado.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Asignamos la fecha de hoy al EditText
                final Calendar calendario = Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio = calendario.get(Calendar.YEAR);

                DatePickerDialog dialogoDeFecha =
                        new DatePickerDialog(MostrarCertificado.this, listenerDatepicker, anio, mes, dia);
                //Muestra
                dialogoDeFecha.show();
            }
        });


    }

    /**
     * Recibe el objeto Certificados y lo escribe en pantalla
     */
    private void recibirDatos() {
        Bundle objetoEnviado = getIntent().getExtras();
        Certificados certificado = null;
        if (objetoEnviado != null) {
            certificado = (Certificados) objetoEnviado.getSerializable("certificado");
            Toast.makeText(this, "certificado"+certificado.getNombreCertificado(), Toast.LENGTH_SHORT).show();

            assert certificado != null;
            nombreCertificado.setText(certificado.getNombreCertificado());

            entidadEmisora.setText(certificado.getEntidadEmisora());

            horasCertificado.setText(certificado.getHorasCertificado());

            creditosCertificado.setText(certificado.getCreditosCertificado());
            fechaFinCertificado.setText(certificado.getFechaFinCertificado());

            anioCorte = Integer.parseInt(certificado.getAnioCorte());

            stridCertificado = certificado.getIdCertificado();
            strIdUser = certificado.getIdUser();

        }

    }

    private void numberpicker() {
        numberPicker.setTextSize(55);
        numberPicker.setMinValue(2000);
        numberPicker.setMaxValue(2020);
        numberPicker.setValue(anioCorte);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                strnumberpicker = String.valueOf(numberPicker.getValue());
            }
        });
    }


    //Creacion de listener para el datepicker
    private DatePickerDialog.OnDateSetListener listenerDatepicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            //Se llama cuando se selecciona la fecha. Nos pasa la vista y asignamos valor a las variables
            anio = year;
            mes = month;
            dia = dayOfMonth;
            //Refrescamos la fecha
            asignarFechaEnEditText();
        }
    };

    public void asignarFechaEnEditText() {
        String fecha = String.format(Locale.getDefault(), "%02d-%02d-%02d", dia, mes + 1, anio);
        //asigamos el valor del editText
        fechaFinCertificado.setText(fecha);
    }


    public void borrarCertificado(View view) {
        mDatabase.child("certificado/" + strIdUser).child(stridCertificado).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if (task2.isSuccessful()){

                    mDatabase.child("historicoCorte/" + strIdUser).child(stridCertificado).removeValue();
                    Toast.makeText(MostrarCertificado.this, "Curso borrado correctamente", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MostrarCertificado.this, InicioActivity.class));
                    finish();
                }else{

                    Toast.makeText(MostrarCertificado.this, "Curso no se ha borrado correctamente", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void modificarCertificado(View view) {
        Toast.makeText(MostrarCertificado.this, "Año corte" + stridCertificado, Toast.LENGTH_LONG).show();
        strnombreCertificado = nombreCertificado.getText().toString().trim();
        strentidadEmisora = entidadEmisora.getText().toString().trim();
        strhorasCertificado = horasCertificado.getText().toString().trim();
        strcreditosCertificado = creditosCertificado.getText().toString().trim();
        srtfechaFinCertificado = fechaFinCertificado.getText().toString().trim();
        if(strnumberpicker== null){
            strnumberpicker= "2020";
        }
        Certificados c = new Certificados();
        c.setAnioCorte(strnumberpicker);
        c.setCreditosCertificado(strcreditosCertificado);
        c.setEntidadEmisora(strentidadEmisora);
        c.setFechaFinCertificado(srtfechaFinCertificado);
        c.setHorasCertificado(strhorasCertificado);
        c.setIdCertificado(stridCertificado);
        c.setIdUser(strIdUser);
        c.setNombreCertificado(strnombreCertificado);

        if (!strnombreCertificado.isEmpty() && !strentidadEmisora.isEmpty() && !strhorasCertificado.isEmpty()
                && !strcreditosCertificado.isEmpty() && !srtfechaFinCertificado.isEmpty()) {

            mDatabase.child("certificado/" + strIdUser).child(stridCertificado).setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task2) {
                    if (task2.isSuccessful()) {
                        Map<String, Object> map2 = new HashMap<>();
                        map2.put("idUser", strIdUser);
                        map2.put("nombreCertificado", strnombreCertificado);
                        map2.put("idCertificado", strIdUser);
                        map2.put("anioCorte", strnumberpicker);

                        mDatabase.child("historicoCorte/" + strIdUser).child(stridCertificado).setValue(map2);
                        Toast.makeText(MostrarCertificado.this, "Curso modificado correctamente", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(MostrarCertificado.this,
                                "No se pudo modificar el curso", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

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

    @Override
    public void onBackPressed() {
        //Si llamas super.onBackPressed(), esto internamente ejecuta finish().
        super.onBackPressed();
    }

}