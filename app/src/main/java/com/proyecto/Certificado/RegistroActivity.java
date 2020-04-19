package com.proyecto.Certificado;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyecto.Certificado.modelo.Persona;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class RegistroActivity extends AppCompatActivity {

    //variables
    private EditText mEditTextName;

    private EditText mEditTextEmail;
    private EditText mEditTextPassword;

    ///Variables que vamos a registrar
    private String name="";
    private String email="";
    private String password="",ecriptPass;

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

        try {
            ecriptPass = encriptar(email,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        mAuth.createUserWithEmailAndPassword(email,ecriptPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String idUSer= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                    Persona p = new Persona();
                    p.setIdUSer(idUSer);
                    p.setEmail(email);
                    p.setName(name);
                    p.setPassword(ecriptPass);
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

    private String encriptar(String email, String password) throws Exception {

        SecretKeySpec secretKey = generateKey(email);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] datosEncriptadosBytes = cipher.doFinal(password.getBytes());
        String datosEncriptadosString = Base64.encodeToString(datosEncriptadosBytes, Base64.DEFAULT);
        return datosEncriptadosString;

    }

    private SecretKeySpec generateKey(String llave) throws Exception {

        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = llave.getBytes("UTF-8");
        key = sha.digest(key);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }



}

