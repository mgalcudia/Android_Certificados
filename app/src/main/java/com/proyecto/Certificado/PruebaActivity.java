package com.proyecto.Certificado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PruebaActivity extends AppCompatActivity {
    private Button mButtonSingout;
    private TextView mTextViewName;
    private TextView mTextViewEmail;
    private TextView mTextViewIdUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mButtonSingout = (Button) findViewById(R.id.btnCerrarSesion);
        mTextViewName = (TextView) findViewById(R.id.textViewName);
        mTextViewEmail = (TextView) findViewById(R.id.textViewEmail);
        mTextViewIdUser = (TextView) findViewById(R.id.textViewIdUser);
        getUserInfo();
    }

    private void getUserInfo() {

        final String id= mAuth.getCurrentUser().getUid();

        mDatabase.child("user").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    String name= dataSnapshot.child("name").getValue().toString();
                    String email= dataSnapshot.child("email").getValue().toString();

                    mTextViewName.setText(name);
                    mTextViewEmail.setText(email);
                    mTextViewIdUser.setText(id);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void cerrarSesion(View view) {
        mAuth.signOut();
        startActivity(new Intent(PruebaActivity.this,MainActivity.class));
        finish();

    }
}
