package com.example.inventarioskydata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventarioskydata.model.Inventario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Show_Activity extends AppCompatActivity {

    EditText nomI, cantI;
    TextView textV;

    String nombre, id, Armario, Estante;
    int cantidad;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_);
        inicializarFirebase();

        nomI = (EditText) findViewById(R.id.nombre);
        cantI = (EditText) findViewById(R.id.cantidad);
        textV = (TextView) findViewById(R.id.id);

        nombre = getIntent().getStringExtra("nombre");
        id = getIntent().getStringExtra("id");
        cantidad = getIntent().getIntExtra("cantidad",0);

        nomI.setText(nombre);
        cantI.setText(Integer.toString(cantidad));
        textV.setText(id);
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
//        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }
    public void actualizar(View view){
        nomI = (EditText) findViewById(R.id.nombre);
        cantI = (EditText) findViewById(R.id.cantidad);
        textV = (TextView) findViewById(R.id.id);

        String nombre = nomI.getText().toString().trim();
        String cantidad = cantI.getText().toString().trim();
        String id = textV.getText().toString().trim();

        Inventario I = new Inventario();
        I.setId(id);
        I.setNombre(nombre);
        I.setCantidad(Integer.parseInt(cantidad));

        databaseReference.child("Inventario").child("ArmarioA").child("EsA").child(I.getId()).setValue(I);
        Toast.makeText(this, "Inventario actualizado", Toast.LENGTH_SHORT).show();
        limpiar();
    }

    public void limpiar() {
        nomI.setText("");
        cantI.setText("");
    }
}
