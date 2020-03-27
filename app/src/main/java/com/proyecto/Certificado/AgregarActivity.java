package com.proyecto.Certificado;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class AgregarActivity extends AppCompatActivity {
    //Iniciamos variable fechafinal
    private EditText fechaFin;
    private int dia,mes,anio;
    //variables numberPicker
    private NumberPicker numberPicker;

    //variables titulo
    private EditText nombreCertificado, entidadEmisora;

    String strnombreCertificado, strentidadEmisora;

    //Creacion de listener para el datepicker
    private DatePickerDialog.OnDateSetListener listenerDatepicker= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            //Se llama cuando se selecciona la fecha. Nos pasa la vista y asignamos valos a las variables
            anio= year;
            mes= month;
            dia= dayOfMonth;
            //Refrescamos la fecha
            refrescarFechaEnEditText();

        }
    };


    public void refrescarFechaEnEditText(){

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

        //instanciamos el objeto
        fechaFin= findViewById(R.id.editTextFechaFin);
        //Asignamos la fecha de hoy al editText
        final Calendar calendario= Calendar.getInstance();
        dia= calendario.get(Calendar.DAY_OF_MONTH);
        mes= calendario.get(Calendar.MONTH);
        anio= calendario.get(Calendar.YEAR);
        //Asignamos la fecha de hoy al EditText
        refrescarFechaEnEditText();

        numberpicker();

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


    }

    public void AgregarUnTitulo(View view) {
        strnombreCertificado=nombreCertificado.getText().toString().trim();
        strentidadEmisora=entidadEmisora.getText().toString().trim();
        Toast.makeText(AgregarActivity.this, ""+ strnombreCertificado+"  "+strentidadEmisora, Toast.LENGTH_LONG).show();
    }
}
