package com.proyecto.Certificado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class LoginActivity extends AppCompatActivity {

    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private String password="",ecriptPass;
    private String email="";
    //creamos el objeto firebase
    private FirebaseAuth mAuth;


    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth= FirebaseAuth.getInstance();
        mEditTextEmail =findViewById(R.id.editTexEmail);
        mEditTextPassword=findViewById(R.id.editPassword);
    }


    public void Loguearse(View view) {
        password= mEditTextPassword.getText().toString().trim();
        email= mEditTextEmail.getText().toString().trim();
        if(!email.isEmpty()&& !password.isEmpty()){

            loginUser();

        }else{

            Toast.makeText(LoginActivity.this, "Debe completar los campos", Toast.LENGTH_LONG).show();
        }
    }

    private void loginUser() {
      /*
        try {
            ecriptPass = encriptar(email,password);

        }catch (Exception e){
            e.printStackTrace();
        }

       */

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task2) {
                if (task2.isSuccessful()){

                    startActivity(new Intent(LoginActivity.this,InicioActivity.class));

                }else{

                    Toast.makeText(LoginActivity.this, "No se puede iniciar la sesion compruebe los datos", Toast.LENGTH_LONG).show();
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


    public void irRecupPass(View view) {


        startActivity(new Intent(LoginActivity.this, RecuperarPass.class));
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
