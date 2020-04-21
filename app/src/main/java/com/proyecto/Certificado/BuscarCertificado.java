package com.proyecto.Certificado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyecto.Certificado.modelo.Certificados;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;

public class BuscarCertificado extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private List <Certificados> listaCertificados = new ArrayList<>();
    ArrayAdapter<Certificados> arrayAdapterCertificado;
    Certificados certificadosSeleccionado;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    ListView listViewCertificado;
    String cadena= null;
    Bundle bundleText= new Bundle();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_certificado);
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth= FirebaseAuth.getInstance();
        listViewCertificado = findViewById(R.id.listviewBuscarCertificado);
        listViewCertificado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                certificadosSeleccionado = (Certificados) parent.getItemAtPosition(position);
                Intent intent= new Intent(BuscarCertificado.this,MostrarCertificado.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("certificado",certificadosSeleccionado);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }



    private void listarcertificado( String newText) {
        listViewCertificado.setAdapter(null);
        String idUSer= requireNonNull(mAuth.getCurrentUser()).getUid();
        if(newText.length()>0){
            databaseReference.child("certificado/"+idUSer).orderByChild("nombreCertificado").startAt(newText).endAt(newText+'\uf8ff').addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listaCertificados.clear();
                    for (DataSnapshot objSnaptshot: dataSnapshot.getChildren()){
                        Certificados c= objSnaptshot.getValue(Certificados.class);
                        listaCertificados.add(c);
                        arrayAdapterCertificado = new ArrayAdapter<Certificados>
                                (BuscarCertificado.this,android.R.layout.simple_list_item_activated_1, listaCertificados);
                        listViewCertificado.setAdapter(arrayAdapterCertificado);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    public boolean onCreateOptionsMenu(Menu menu){
      getMenuInflater().inflate(R.menu.menu_buscador,menu);
     //   MenuItem item= menu.findItem(R.id.buscador);

       // searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.buscador:
                SearchView searchView = (SearchView) item.getActionView();
                if(cadena != null){
                    searchView.setOnQueryTextListener(this);
                    searchView.getImeOptions();
                    searchView.isSubmitButtonEnabled();
                    searchView.setQuery(cadena,true);
                }
                else {
                    searchView.setOnQueryTextListener(this);
                }

                break;
            case R.id.atras:
                onBackPressed();
                break;

        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        cadena= newText;
        //va mostrando las coincidencias
        listarcertificado(newText);
        return false;

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        //Si llamas super.onBackPressed(), esto internamente ejecuta finish().
        super.onBackPressed();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundleText) {
        super.onSaveInstanceState(Objects.<Bundle>requireNonNull(bundleText));
            bundleText.putString("busqueda",cadena);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(Objects.<Bundle>requireNonNull(savedInstanceState));
        cadena = savedInstanceState.getString("busqueda");

        onQueryTextChange(cadena);

    }
}
