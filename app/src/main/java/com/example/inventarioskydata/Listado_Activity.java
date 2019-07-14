package com.example.inventarioskydata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import android.widget.Toast;

import com.example.inventarioskydata.model.Inventario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Listado_Activity extends AppCompatActivity {

    ListView listView;
    Inventario Selected;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String [] estantes = {"Estante A", "Estante B", "Estante C", "Estante E", "Estante H", "Estante I"};
    String [] Armario = {"ArmarioA", "ArmarioB", "ArmarioC"};

    private List<Inventario> listInventario = new ArrayList<Inventario>();
    private List<String> listCodigos = new ArrayList<String>();

    ArrayAdapter<Inventario> arrayAdapter;
    ArrayAdapter<String> arrayCodgios;

    EditText nomI, cantI, campoI;
    TextView textV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher_round);
        actionBar.setDisplayShowHomeEnabled(true);
        inicializarFirebase();

        String id = getIntent().getStringExtra("codigo");

        if (id != null){
            listaInventario(id);
        }else {
            listaInventario();
        }


        nomI = (EditText) findViewById(R.id.nombre);
        cantI = (EditText) findViewById(R.id.cantidad);
        campoI = (EditText) findViewById(R.id.campo);
        textV = (TextView) findViewById(R.id.id);

//        final Intent intent = new Intent (this,Show_Activity.class);

        final Intent intent = new Intent (this,Inventario_Activity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Selected = (Inventario) adapterView.getItemAtPosition(position);
                    intent.putExtra("nombre", Selected.getNombre());
                    intent.putExtra("cantidad", Selected.getCantidad());
                    intent.putExtra("id", Selected.getId());
                    intent.putExtra("armario",Selected.getArmario());
                    intent.putExtra("estante", Selected.getEstante());
                    startActivity(intent);
            }
        });


    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void listaInventario() {
        listView = (ListView) findViewById(R.id.ListView);

        for (int i=0; i< Armario.length; i++){
            for (int j=0; j< estantes.length; j++)
                databaseReference.child("Inventario").child(Armario[i]).child(estantes[j]).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                            Inventario i = objSnaptshot.getValue(Inventario.class);
                            if (i!=null) {
                                listInventario.add(i);
                            }
                            arrayAdapter = new ArrayAdapter<Inventario>(Listado_Activity.this, android.R.layout.simple_list_item_1, listInventario);
                            listView.setAdapter(arrayAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }

    public void listaInventario(String id){
        listView = (ListView) findViewById(R.id.ListView);
        String codigo = id;

        if (codigo.equals("ArmarioA") || codigo.equals("ArmarioB") || codigo.equals("ArmarioC")){
            armarios(codigo);
        }else if (codigo.length() == 9){
            estantes(codigo);
        }else {
            String armarioId = "Armario" + codigo.charAt(0);
            String estanteId = "Estante " + codigo.charAt(1);

            DatabaseReference ref = databaseReference.child("Inventario").child(armarioId).child(estanteId);
            Query idQuery = ref.orderByChild("id").equalTo(codigo);

            idQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        Inventario i = singleSnapshot.getValue(Inventario.class);
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
    }


    public void listaChild(){
        listView = (ListView) findViewById(R.id.ListView);
        DatabaseReference ref = databaseReference.child("Inventario");
//        Query query = ref.equalTo("ArmarioA");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSanpshot : dataSnapshot.getChildren()){
                    String codigos = objSanpshot.getKey();
                    listCodigos.add(codigos);

//                    arrayCodgios = new ArrayAdapter<String>(Listado_Activity.this, android.R.layout.simple_list_item_1, listCodigos);
//                    listView.setAdapter(arrayCodgios);
                }
                Toast.makeText(Listado_Activity.this, listCodigos.get(0), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void armarios(String Armario){
        Toast.makeText(this, Armario, Toast.LENGTH_SHORT).show();

        for (int i=0; i<estantes.length; i++){

            databaseReference.child("Inventario").child(Armario).child(estantes[i]).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
    }


    private void estantes(String Estante) {

        Toast.makeText(this, Estante, Toast.LENGTH_SHORT).show();

        for (int i=0; i<Armario.length; i++){

            databaseReference.child("Inventario").child(Armario[i]).child(Estante).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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

    }


}
