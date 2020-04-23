package com.proyecto.Certificado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarPass extends AppCompatActivity {
    private EditText mEditTextEmail;
    String email;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_pass);
        mAuth = FirebaseAuth.getInstance();
        mEditTextEmail = findViewById(R.id.editTexEmail);
    }
    /*
    OLVIDAR CONTRASEÑA
    https://www.youtube.com/watch?v=H8oeTzLWw_8&vl=es-419
     */

    /**
     * Funcion en la que si el campo email esta relleno llama a la funcion para
     * recuperar contraseña
     *
     * @param view
     */
    public void recuperarContrasenia(View view) {

        email = mEditTextEmail.getText().toString();

        if (!email.isEmpty()) {
            ResetPass();

        } else {
            Toast.makeText(this, R.string.emailNoValido, Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * Funcion que usa la funcion de firebase para recuperar la contraseña enviando un correo electronico
     */
    private void ResetPass() {
        //selecciona el lenguaje del correo electronico.
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(RecuperarPass.this, R.string.emailEnviadoPass, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RecuperarPass.this, R.string.emailNoEnviadoPass, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    /**
     * Al crear la actividad se inyecta el menu personalizado
     *
     * @param menu
     * @return boolean;
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Funcion para  identificar el item seleccionado por el usuario en el menu superior
     * personalizado.
     *
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.atras: {
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
