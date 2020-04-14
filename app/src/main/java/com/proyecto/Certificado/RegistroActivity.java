package com.proyecto.Certificado;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyecto.Certificado.modelo.Persona;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistroActivity extends AppCompatActivity {

    //variables
    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;

    ///Variables que vamos a registrar
    private String name="";
    private String email="";
    private String password="";

    //Creamos el objeto FIrebase
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //Instanciamos el objeto firebase
        mAuth= FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference();
        mEditTextName =findViewById(R.id.editTexname);
        mEditTextEmail =findViewById(R.id.editTexEmail);
        mEditTextPassword=findViewById(R.id.editPassword);


    }

    public void registrar(View view) {
        name= mEditTextName.getText().toString().trim();
        email= mEditTextEmail.getText().toString().trim();
        password= mEditTextPassword.getText().toString().trim();

        if(!name.isEmpty()&& !email.isEmpty()&& !password.isEmpty()){

            if (password.length()>=6){

                //Si completo ok registramos al usuario
                RegisterUSer();

            }else{

                Toast.makeText(RegistroActivity.this, "La logitud del password debe mayor de 6 caracteres", Toast.LENGTH_LONG).show();
            }
        }else{

            Toast.makeText(RegistroActivity.this, "Debe completar los campos", Toast.LENGTH_LONG).show();

        }

    }



    private void RegisterUSer() {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String idUSer= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                    Persona p = new Persona();
                    p.setIdUSer(idUSer);
                    p.setEmail(email);
                    p.setName(name);
                    p.setPassword(password);
                    mDatabaseReference.child("user").child(idUSer).setValue(p).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                Toast.makeText(RegistroActivity.this, "Usuario Registado", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegistroActivity.this, InicioActivity.class));
                                finish();

                            }else {
                                Toast.makeText(RegistroActivity.this,
                                        "No se puede ir a la otra actividad", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }else{
                    Toast.makeText(RegistroActivity.this, "No se ha podido registra", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

