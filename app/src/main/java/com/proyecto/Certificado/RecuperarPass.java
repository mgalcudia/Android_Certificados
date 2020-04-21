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
            mAuth =FirebaseAuth.getInstance();
        mEditTextEmail= findViewById(R.id.editTexEmail);
    }
    /*
    OLVIDAR CONTRASEÑA
    https://www.youtube.com/watch?v=H8oeTzLWw_8&vl=es-419


     */
    public void RecuperarPass(View view) {

        email= mEditTextEmail.getText().toString();

        if(!email.isEmpty()){
        ResetPass();

        }else {
            Toast.makeText(this, "Introduzca un Email Válido para recuperar la contraseña", Toast.LENGTH_SHORT).show();
        }


    }

    private void ResetPass() {

        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(RecuperarPass.this, "Se ha enviado un correo electronico para reestablecer su contraseña", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(RecuperarPass.this, "No se ha podido mandar el correo para restableces si contraseña", Toast.LENGTH_SHORT).show();

                }




            }
        });
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
