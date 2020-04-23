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

public class LoginActivity extends AppCompatActivity {

    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private String password="",ecriptPass;
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
    }

    /**
     * Funcion que comprueba que los campos de textos este escritos y en caso afirmativo manda
     * a la funcion del loguin
     * @param view
     */
    public void Loguearse(View view) {
        password= mEditTextPassword.getText().toString().trim();
        email= mEditTextEmail.getText().toString().trim();
        if(!email.isEmpty()&& !password.isEmpty()){

            loginUser();

        }else{

            Toast.makeText(LoginActivity.this, R.string.compleCampos, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Funcion para loguear al usuario
     */
    private void loginUser() {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task2) {
                if (task2.isSuccessful()){

                    startActivity(new Intent(LoginActivity.this,InicioActivity.class));

                }else{

                    Toast.makeText(LoginActivity.this, R.string.noLoguin, Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    /**
     *  Funcion que envia al usuario a la activity para recuperar la contraseña
     * @param view
     */
    public void irRecupPass(View view) {
        startActivity(new Intent(LoginActivity.this, RecuperarPass.class));
    }


    /**
     *  Al crear la actividad se inyecta el menu personalizado
     * @param menu
     * @return boolean;
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
/*
  ///////////////////////////////////////////////////////////////////////////////////////////////
    /// Estas funciones no tienen utilidad en la aplicacion las he mantenido ya que me resultan
    /// interesante la forma en la que encripta.
    ///https://www.youtube.com/watch?v=Ik7YmSd6dRQ

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

/////////////////////////////////////////////////////////////////////////////////////////////////////
 */


}
