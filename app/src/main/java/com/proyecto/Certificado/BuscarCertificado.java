package com.proyecto.Certificado;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import static java.util.Objects.requireNonNull;

public class BuscarCertificado extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private List <Certificados> listaCertificados = new ArrayList<>();
    ArrayAdapter<Certificados> arrayAdapterCertificado;
    Certificados certificadosSeleccionado;

    //variables Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    ListView listViewCertificado;
    //variable que almacena lo que se escribe en el campo search
    String cadena= null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_certificado);
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth= FirebaseAuth.getInstance();
        listViewCertificado = findViewById(R.id.listviewBuscarCertificado);

        /**
         *  Cuando el usuario selecciona un certificado se le envia a otra actividad  que le muestra
         *  los datos del certificado para que pueda modificarlo o eliminarlo, se envia el objeto
         *  certificado a la otra actividad
         */
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


    /**
     *  Funcion para listar los certificados que comiencen por lo que introduce el usuario conforme
     *  introduce datos
     * @param newText
     */
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

    /**
     *  Al crear la actividad se inyecta el menu personalizado
     * @param menu
     * @return boolean;
     */
    public boolean onCreateOptionsMenu(Menu menu){
      getMenuInflater().inflate(R.menu.menu_buscador,menu);
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

        switch (item.getItemId()){
            case R.id.buscador:
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setFocusable(true);
                searchView.setIconified(false);
                searchView.setOnQueryTextListener(this);
                if(cadena != null){
                    searchView.setQuery(cadena,true);
                }
                break;
            case R.id.atras:
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *  Funcion sin uso. Puede hacer busquedas pero hay que pulsar el boton buscar para que se ejecute
     * @param query
     * @return boolean
     */
    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    /**
     *  Funcion que captura lo que va introduciendo el usuario, y  usando la funcion
     *  listarcertificado(newText) muestra los certificados. Lo almacena en el String cadena
     *  para tenerlo guardado al girar la pantalla no perder la informacion
     * @param newText
     * @return
     */
    @Override
    public boolean onQueryTextChange(String newText) {

        cadena= newText;
        //va mostrando las coincidencias
        listarcertificado(newText);
        return false;

    }

    /**
     * funcion sin uso
     * @param hasCapture
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * Funcion que nos devuelve a la actividad anterior
     * internamente hace un finish de la actividad actual
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Funcion para almacenar que almacena en un bundle lo introducido por el usuario en el campo
     * busqueda para que al girar no se pierda la informacion     *
     * @param bundleText
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle bundleText) {

        super.onSaveInstanceState(Objects.<Bundle>requireNonNull(bundleText));
            bundleText.putString("busqueda",cadena);
    }

    /**
     *  Funcion que al crear la activity cuando se gira la pantalla le asigna al search el valor
     *  que almacena el bundle que se generó en la funcion
     *  onSaveInstanceState(@NonNull Bundle bundleText)
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(Objects.<Bundle>requireNonNull(savedInstanceState));
        cadena = savedInstanceState.getString("busqueda");
        onQueryTextChange(cadena);

    }
}
