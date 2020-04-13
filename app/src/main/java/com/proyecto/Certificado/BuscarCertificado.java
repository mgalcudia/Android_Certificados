package com.proyecto.Certificado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
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

public class BuscarCertificado extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ArrayList<Certificados>listaCertificado= new ArrayList<>();
    //ListView listViewCertificado;
    ArrayList<String>lista= new ArrayList<>();
    ArrayAdapter<String> arrayAdapterlista;

    ///Con certificado
    private List <Certificados> listaCertificados = new ArrayList<Certificados>();
    ArrayAdapter<Certificados> arrayAdapterCertificado;
    Certificados certificadosSeleccionado;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    ListView listViewCertificado;
    String newText;


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

                String idcertificado= certificadosSeleccionado.getIdCertificado();
                Toast.makeText(BuscarCertificado.this, "Cursoe"+
                        certificadosSeleccionado.getIdCertificado() , Toast.LENGTH_LONG).show();

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
        String idUSer= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        if(newText.length()>0){

            databaseReference.child("certificado/"+idUSer).orderByChild("nombreCertificado").startAt(newText).endAt(newText+'\uf8ff').addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listaCertificados.clear();
                    for (DataSnapshot objSnaptshot: dataSnapshot.getChildren()){
                        Certificados c= objSnaptshot.getValue(Certificados.class);
                        //
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
        MenuItem item= menu.findItem(R.id.buscador);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
    //este es para darle al boton de buscar
        Toast.makeText(BuscarCertificado.this,"Texto"+query,Toast.LENGTH_LONG).show();
      //  listarcertificado(query);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listarcertificado(newText);
        return false;

    }

    private ArrayList<Certificados> filter(ArrayList<Certificados> certificados, String texto){

    ArrayList<Certificados> listafiltrada= new ArrayList<>();
        try {
            //texto que queremos filtrar en la lista
            texto= texto.toLowerCase();

            for(Certificados certificado : certificados){

                String certificado1 = certificado.getNombreCertificado().toLowerCase();

                if(certificado1.contains(texto)){
                    listafiltrada.add(certificado);
                }
            }
        }catch (Exception e){

            e.printStackTrace();
        }

        return listafiltrada;
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }






}
