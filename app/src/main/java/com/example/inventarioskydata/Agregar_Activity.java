package com.example.inventarioskydata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventarioskydata.model.Inventario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Agregar_Activity extends AppCompatActivity {

    EditText nomI, cantI, campoI;
    TextView ID;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_);

        nomI = (EditText) findViewById(R.id.nombre);
        cantI = (EditText) findViewById(R.id.cantidad);
        campoI = (EditText) findViewById(R.id.campo);

        inicializarFirebase();

    }

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
}
