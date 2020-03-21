package com.proyecto.Certificado;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class AgregarActivity extends AppCompatActivity {
    //Iniciamos variable fechafinal
    private EditText fechaFin;
    private int dia,mes,anio;

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
        //instanciamos el objeto
        fechaFin= findViewById(R.id.editTextFechaFin);
        //Asignamos la fecha de hoy al editText
        final Calendar calendario= Calendar.getInstance();
        dia= calendario.get(Calendar.DAY_OF_MONTH);
        mes= calendario.get(Calendar.MONTH);
        anio= calendario.get(Calendar.YEAR);
        //Asignamos la fecha de hoy al EditText
        refrescarFechaEnEditText();

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

}
