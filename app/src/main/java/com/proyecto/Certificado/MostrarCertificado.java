package com.proyecto.Certificado;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.proyecto.Certificado.modelo.Certificados;

import java.util.Calendar;
import java.util.Locale;

public class MostrarCertificado extends AppCompatActivity {
    //Iniciamos variable fechafinal
    private EditText fechaFin;
    private int dia,mes,anio;
    //variables numberPicker
    private NumberPicker numberPicker;
    String strnumberpicker;
    //variables titulo
    private EditText nombreCertificado, entidadEmisora, horasCertificado, creditosCertificado,fechaFinCertificado;
    int anioCorte;
    String strnombreCertificado, strentidadEmisora,strhorasCertificado,strcreditosCertificado,srtfechaFinCertificado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_certificado);
        //obtenemos el valor de nombre certificado
        nombreCertificado= findViewById(R.id.editTextTituloCertificado);
        entidadEmisora= findViewById(R.id.editTextEntidadCertificado);
        horasCertificado = findViewById(R.id.editTextHorasCertificado);
        creditosCertificado =findViewById(R.id.editTextCreditoCertificado);
        fechaFinCertificado= findViewById(R.id.editTextFechaFin);
        numberPicker = findViewById(R.id.numberPickerAnoCorte);

        recibirDatos();
        numberpicker();


        //Hace que el datepicker se muestre al clickear el edixText
        fechaFinCertificado.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Asignamos la fecha de hoy al EditText
                final Calendar calendario= Calendar.getInstance();
                dia= calendario.get(Calendar.DAY_OF_MONTH);
                mes= calendario.get(Calendar.MONTH);
                anio= calendario.get(Calendar.YEAR);

                DatePickerDialog dialogoDeFecha =
                        new DatePickerDialog(MostrarCertificado.this,listenerDatepicker,anio,mes,dia);
                //Muestra
                dialogoDeFecha.show();
            }
        });


    }

    private void recibirDatos() {
        Bundle objetoEnviado= getIntent().getExtras();
        Certificados certificado =null;
        if (objetoEnviado!= null) {
            certificado = (Certificados) objetoEnviado.getSerializable("certificado");
            nombreCertificado.setText(certificado.getNombreCertificado());
            entidadEmisora.setText(certificado.getEntidadEmisora());
            horasCertificado.setText(certificado.getHorasCertificado());
            creditosCertificado.setText(certificado.getCreditosCertificado());
            fechaFinCertificado.setText(certificado.getFechaFinCertificado());
            anioCorte = Integer.parseInt(certificado.getAnioCorte());
            Toast.makeText(MostrarCertificado.this, "Año corte"+anioCorte , Toast.LENGTH_LONG).show();
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
    private DatePickerDialog.OnDateSetListener listenerDatepicker= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            //Se llama cuando se selecciona la fecha. Nos pasa la vista y asignamos valor a las variables
            anio= year;
            mes= month;
            dia= dayOfMonth;
            //Refrescamos la fecha
            asignarFechaEnEditText();
        }
    };

    public void asignarFechaEnEditText(){
        String fecha= String.format(Locale.getDefault(),"%02d-%02d-%02d", dia, mes+1, anio);
        //asigamos el valor del editText
        fechaFinCertificado.setText(fecha);
    }


}
