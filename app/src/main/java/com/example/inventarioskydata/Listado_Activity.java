package com.example.inventarioskydata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.inventarioskydata.model.Inventario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Listado_Activity extends AppCompatActivity {

    ListView listView;
    Inventario Selected;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Inventario> listInventario = new ArrayList<Inventario>();
    ArrayAdapter<Inventario> arrayAdapter;

    EditText nomI, cantI, campoI;
    TextView textV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_);
        inicializarFirebase();
        listaInventario();

        nomI = (EditText) findViewById(R.id.nombre);
        cantI = (EditText) findViewById(R.id.cantidad);
        campoI = (EditText) findViewById(R.id.campo);
        textV = (TextView) findViewById(R.id.id);

        final Intent intent = new Intent (this,Show_Activity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Selected = (Inventario) adapterView.getItemAtPosition(position);
                    intent.putExtra("nombre", Selected.getNombre());
                    intent.putExtra("cantidad", Selected.getCantidad());
                    intent.putExtra("campo", Selected.getCampo());
                    intent.putExtra("id", Selected.getId());

                    startActivity(intent);
//                nomI.setText(Selected.getNombre());
//                cantI.setText(Integer.toString(Selected.getCantidad()));
//                campoI.setText(Selected.getCampo());
//                textV.setText(Selected.getId());
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
//        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }
    public void listaInventario() {
        listView = (ListView) findViewById(R.id.ListView);

        databaseReference.child("Inventario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listInventario.clear();

                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    Inventario i = objSnaptshot.getValue(Inventario.class);
                    listInventario.add(i);

                    arrayAdapter = new ArrayAdapter<Inventario>(Listado_Activity.this, android.R.layout.simple_list_item_1, listInventario);
                    listView.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    public void actualizar(View view){
//        nomI = (EditText) findViewById(R.id.nombre);
//        cantI = (EditText) findViewById(R.id.cantidad);
//        campoI = (EditText) findViewById(R.id.campo);
//        textV = (TextView) findViewById(R.id.id);
//
//        String nombre = nomI.getText().toString().trim();
//        String cantidad = cantI.getText().toString().trim();
//        String campo = campoI.getText().toString().trim();
//        String id = textV.getText().toString().trim();
//
//        Inventario I = new Inventario();
//        I.setId(id);
//        I.setNombre(nombre);
//        I.setCantidad(Integer.parseInt(cantidad));
//        I.setCampo(campo);
//
//        databaseReference.child("Inventario").child(I.getId()).setValue(I);
//    }
}
