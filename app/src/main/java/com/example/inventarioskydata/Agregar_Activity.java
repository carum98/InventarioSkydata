package com.example.inventarioskydata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventarioskydata.model.Inventario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;
import java.util.UUID;

public class Agregar_Activity extends AppCompatActivity {

    EditText nomI, cantI, campoI;
    TextView ID;
    Spinner Armario, Estante;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_);

        nomI = (EditText) findViewById(R.id.nombre);
        cantI = (EditText) findViewById(R.id.cantidad);
        Armario = (Spinner) findViewById(R.id.Armario);
        Estante = (Spinner) findViewById(R.id.Estante);

        ArrayAdapter<CharSequence> ArmarioAdapter = ArrayAdapter.createFromResource(this, R.array.armarios, android.R.layout.simple_spinner_item);
        ArmarioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Armario.setAdapter(ArmarioAdapter);

        ArrayAdapter<CharSequence> EstanteAdapter = ArrayAdapter.createFromResource(this, R.array.estantes, android.R.layout.simple_spinner_item);
        EstanteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Estante.setAdapter(EstanteAdapter);

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

        String armario = Armario.getSelectedItem().toString();
        String estante = Estante.getSelectedItem().toString();

        if (nombre.equals("") || cantidad.equals("")){
            validacion();
        }else {
            String id = armario.substring(armario.length()-1)+estante.substring(armario.length())+UUID.randomUUID().toString().substring(0,8);
            Inventario I = new Inventario();
            I.setNombre(nombre);
            I.setCantidad(Integer.parseInt(cantidad));
            I.setArmario(armario);
            I.setEstante(estante);
            I.setId(id);

            databaseReference.child("Inventario").child(I.getArmario()).child(I.getEstante()).child(I.getId()).setValue(I);
            Toast.makeText(this, "Agregado al inventario", Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }

    public void limpiar() {
        nomI.setText("");
        cantI.setText("");
    }

    public void validacion() {
        String nombre = nomI.getText().toString();
        String cantidad = cantI.getText().toString();

        if (nombre.equals("")) {
            nomI.setError("Ingrese el nombre");
        } else if (cantidad.equals("")) {
            cantI.setError("Ingrese cantidad");
        }

    }
}
