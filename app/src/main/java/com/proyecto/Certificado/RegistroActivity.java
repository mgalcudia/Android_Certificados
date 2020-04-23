package com.proyecto.Certificado;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyecto.Certificado.modelo.Persona;

import java.util.Objects;

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

    /**
     *  Funcion que comprueba que los campos estan completos
     * @param view
     */
    public void registrar(View view) {
        name= mEditTextName.getText().toString().trim();
        email= mEditTextEmail.getText().toString().trim();
        password= mEditTextPassword.getText().toString().trim();

        if(!name.isEmpty()&& !email.isEmpty()&& !password.isEmpty()){

            if (password.length()>=6){
                RegisterUSer();
            }else{

                Toast.makeText(RegistroActivity.this, R.string.passLongtud, Toast.LENGTH_LONG).show();
            }
        }else{

            Toast.makeText(RegistroActivity.this, R.string.completaCampos, Toast.LENGTH_LONG).show();

        }

    }


    /**
     * Funcion que registra al usuario usando funcion de firebase
     */
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
                                Toast.makeText(RegistroActivity.this, R.string.usuarioRegistrado, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegistroActivity.this, InicioActivity.class));
                                finish();

                            }else {
                                Toast.makeText(RegistroActivity.this,
                                        R.string.usuarioNoBd, Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }else{

                    Toast.makeText(RegistroActivity.this,
                            R.string.usuarioNoRegistrado, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /**
     *  Al crear la actividad se inyecta el menu personalizado
     * @param menu
     * @return boolean;
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    /**
     *   Funcion para  identificar el item seleccionado por el usuario en el menu superior
     *   personalizado.
     * @param item
     * @return boolean
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

