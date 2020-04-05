package com.proyecto.Certificado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.proyecto.Certificado.modelo.Certificados;

public class MostrarCertificado extends AppCompatActivity {
    //Iniciamos variable fechafinal
    private EditText fechaFin;
    private int dia,mes,anio;
    //variables numberPicker
    private NumberPicker numberPicker;
    String strnumberpicker;
    //variables titulo
    private EditText nombreCertificado, entidadEmisora, horasCertificado, creditosCertificado,fechaFinCertificado;

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

        //instanciamos el objeto
        fechaFin= findViewById(R.id.editTextFechaFin);
        recibirDatos();

    }

    private void recibirDatos() {

        Bundle objetoEnviado= getIntent().getExtras();
    //   String idCertificado= extras.getString("idCertificado");
      // nombre.setText(idCertificado);
        Certificados certificado =null;
        if (objetoEnviado!= null) {
            certificado = (Certificados) objetoEnviado.getSerializable("certificado");
            nombreCertificado.setText(certificado.getNombreCertificado());
            entidadEmisora.setText(certificado.getEntidadEmisora());
            horasCertificado.setText(certificado.getHorasCertificado());
            creditosCertificado.setText(certificado.getCreditosCertificado());
            fechaFinCertificado.setText(certificado.getFechaFinCertificado());
        }

    }
}
