package com.proyecto.Certificado;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth= FirebaseAuth.getInstance();

    }

    /**
     * Envia  al usuario a la activity para registrar un nuevo usuario
     * @param view
     */
    public void irRegistrar(View view) {

        startActivity(new Intent(MainActivity.this, RegistroActivity.class));
    }

    /**
     * Funcion que envia al usuario a la activity del loguin
     * @param view
     */
    public void irLoguin(View view) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    /**
     *  Funcion que comprueba si hay activa una sesion de usuario y en caso afirmativa manda al usuario
     *  al activity inicio para evitar el loguin
     */
    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() !=null){

            startActivity(new Intent(MainActivity.this,InicioActivity.class));
            finish();
        }
    }
}