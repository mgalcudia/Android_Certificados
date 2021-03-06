package com.proyecto.Certificado;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Objects;
import java.util.UUID;

public class AgregarActivity extends AppCompatActivity {
    //Iniciamos variable fechafinal
    private EditText fechaFin;
    private int dia, mes, anio;
    //variables numberPicker
    private NumberPicker numberPicker;
    String strnumberpicker;
    //variables titulo
    private EditText nombreCertificado, entidadEmisora, horasCertificado, creditosCertificado;
    private TextView fechaFinCertificado;
    String strnombreCertificado, strentidadEmisora, strhorasCertificado, strcreditosCertificado, srtfechaFinCertificado;
    //variables Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        //obtenemos el valor de nombre certificado
        nombreCertificado = findViewById(R.id.editTextTituloCertificado);
        entidadEmisora = findViewById(R.id.editTextEntidadCertificado);
        horasCertificado = findViewById(R.id.editTextHorasCertificado);
        creditosCertificado = findViewById(R.id.editTextCreditoCertificado);
        fechaFinCertificado = findViewById(R.id.editTextFechaFin);
        numberPicker = findViewById(R.id.numberPickerAnoCorte);
/*
        //instanciamos el objeto
        fechaFin= findViewById(R.id.editTextFechaFin);
*/
        //Asignamos la fecha de hoy al EditText
        final Calendar calendario = Calendar.getInstance();
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH);
        anio = calendario.get(Calendar.YEAR);
        asignarFechaEnEditText();
        numberpicker();

        //hacemos referencia a firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        /**
         * Hace que el datepicker se muestre al clickear el TextView
         */
                fechaFinCertificado.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //ocultarTeclado();
                DatePickerDialog dialogoDeFecha =
                        new DatePickerDialog(AgregarActivity.this, listenerDatepicker, anio, mes, dia);
                //Muestra
                dialogoDeFecha.show();
            }
        });
    }
    /**
     * Creacion de listener para el datepicker
     */
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
    /**
     *  Asigna el valor al TextView con la fecha
     */
    public void asignarFechaEnEditText() {
        String fecha = String.format(Locale.getDefault(), "%02d-%02d-%02d", dia, mes + 1, anio);
        //asigamos el valor del editText
        fechaFinCertificado.setText(fecha);
    }

    /**
     * Genera el numberpicker para seleccionar el año de corte de bolsa
     */
    private void numberpicker() {

        numberPicker.setTextSize(55);
        numberPicker.setMinValue(2000);
        numberPicker.setMaxValue(anio);
        numberPicker.setValue(anio);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                strnumberpicker = String.valueOf(numberPicker.getValue());

            }
        });

    }

    /**
     *  Función para agregar un certificado al nodo del usuario dentro del nodo certificado.
     *  a su vez registra los datos oportunos para generar el historico de cortes de bolsa a cada usuario
     * @param view
     */
    public void AgregarUnTitulo(View view) {
        strnombreCertificado = nombreCertificado.getText().toString().trim();
        strentidadEmisora = entidadEmisora.getText().toString().trim();
        strhorasCertificado = horasCertificado.getText().toString().trim();
        strcreditosCertificado = creditosCertificado.getText().toString().trim();
        srtfechaFinCertificado = fechaFinCertificado.getText().toString().trim();
        final String idUsuario = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        if (strnumberpicker == null) {
            strnumberpicker = "2020";
        }
        Certificados c = new Certificados();
        c.setNombreCertificado(strnombreCertificado);
        c.setEntidadEmisora(strentidadEmisora);
        c.setHorasCertificado(strhorasCertificado);
        c.setCreditosCertificado(strcreditosCertificado);
        c.setFechaFinCertificado(srtfechaFinCertificado);
        c.setAnioCorte(strnumberpicker);
        c.setIdUser(idUsuario);

        if (!strnombreCertificado.isEmpty() && !strentidadEmisora.isEmpty() && !strhorasCertificado.isEmpty()
                && !strcreditosCertificado.isEmpty() && !srtfechaFinCertificado.isEmpty()) {
            final String idCertificado = (UUID.randomUUID().toString());
            c.setIdCertificado(idCertificado);
            nombreCertificado.setText("");
            entidadEmisora.setText("");
            horasCertificado.setText("");
            creditosCertificado.setText("");
            //Añade los datos del certificado
            mDatabase.child("certificado/" + idUsuario).child(idCertificado).setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task2) {
                    if (task2.isSuccessful()) {
                        Map<String, Object> map2 = new HashMap<>();
                        map2.put("idUser", idUsuario);
                        map2.put("nombreCertificado", strnombreCertificado);
                        map2.put("idCertificado", idCertificado);
                        map2.put("anioCorte", strnumberpicker);
                        // Anade los datos al historico
                        mDatabase.child("historicoCorte/" + idUsuario).child(idCertificado).setValue(map2);
                        Toast.makeText(AgregarActivity.this, R.string.cursoReg, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AgregarActivity.this,
                                R.string.NoSePuedeReg, Toast.LENGTH_LONG).show();
                    }
                }
            });


        }

    }

    /**
     *  Al crear la actividad se inyecta el menu personalizado
     * @param menu
     * @return super.onCreateOptionsMenu(menu);
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     *   Funcion para  identificar el item seleccionado por el usuario en el menu superior
     *   personalizado.
     * @param item
     * @return true
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
