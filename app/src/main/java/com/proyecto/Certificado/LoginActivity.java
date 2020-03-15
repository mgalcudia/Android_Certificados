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

public class LoginActivity extends AppCompatActivity {

    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button btnLoguin;
    private String password="";
    private String email="";
    //creamos el objeto firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth= FirebaseAuth.getInstance();
        mEditTextEmail =findViewById(R.id.editTexEmail);
        mEditTextPassword=findViewById(R.id.editPassword);
        btnLoguin=findViewById(R.id.btnLoguear);
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
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    startActivity(new Intent(LoginActivity.this,InicioActivity.class));

                }else{

                    Toast.makeText(LoginActivity.this, "No se puede iniciar la sesion compruebe los datos", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


}
