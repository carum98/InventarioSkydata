package com.example.inventarioskydata;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventarioskydata.model.Inventario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

//    ListView listView;
    EditText nomI, cantI, campoI;
    TextView ID;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Inventario> listInventario = new ArrayList<Inventario>();
    ArrayAdapter<Inventario> arrayAdapter;

    Inventario Selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomI = (EditText) findViewById(R.id.nombre);
        cantI = (EditText) findViewById(R.id.cantidad);
        campoI = (EditText) findViewById(R.id.campo);
        ID = (TextView) findViewById(R.id.id);

//        listView = (ListView) findViewById(R.id.ListView);

        inicializarFirebase();
//        listaInventario();

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Selected = (Inventario) adapterView.getItemAtPosition(position);
//
//                nomI.setText(Selected.getNombre());
//                cantI.setText(Integer.toString(Selected.getCantidad()));
//                campoI.setText(Selected.getCampo());
//                ID.setText(Selected.getId());
//            }
//        });
    }

    public void listado(View view) {
        Intent listado = new Intent(this, Listado_Activity.class);
        startActivity(listado);
    }

//    public void listaInventario() {
//        databaseReference.child("Inventario").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                listInventario.clear();
//
//                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
//                    Inventario i = objSnaptshot.getValue(Inventario.class);
//                    listInventario.add(i);
//
//                    arrayAdapter = new ArrayAdapter<Inventario>(MainActivity.this, android.R.layout.simple_list_item_1, listInventario);
//                    listView.setAdapter(arrayAdapter);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
//        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    public void CrearInventario(View view) {
        String nombre = nomI.getText().toString();
        String cantidad = cantI.getText().toString();
        String campo = campoI.getText().toString();
        //String id = ID.getText().toString();

        if (nombre.equals("") || cantidad.equals("") || campo.equals("")){
            validacion();
        }else {
            Inventario I = new Inventario();
            I.setNombre(nombre);
            I.setCantidad(Integer.parseInt(cantidad));
            I.setCampo(campo);
            I.setId(UUID.randomUUID().toString());
            databaseReference.child("Inventario").child(I.getId()).setValue(I);
            Toast.makeText(this, "Agregado al inventario", Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }

    public void limpiar() {
        nomI.setText("");
        cantI.setText("");
        campoI.setText("");
    }

    public void validacion() {
        String nombre = nomI.getText().toString();
        String cantidad = cantI.getText().toString();
        String campo = campoI.getText().toString();

        if (nombre.equals("")) {
            nomI.setError("Ingrese el nombre");
        } else if (cantidad.equals("")) {
            cantI.setError("Ingrese cantidad");
        } else if (campo.equals("")) {
            campoI.setError("Ingrese el campo");
        }

    }

//    public void salidaInven(){
//        String nombre = nomI.getText().toString().trim();
//        String cantidad = cantI.getText().toString().trim();
//        String campo = campoI.getText().toString().trim();
//
//
//        Inventario I = new Inventario();
//        I.setNombre(nombre);
//        I.setCantidad(Integer.parseInt(cantidad));
//        I.setCampo(campo);
//
//        databaseReference.child("Inventario").child()
//    }

}
