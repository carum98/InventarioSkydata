package com.example.inventarioskydata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inventarioskydata.model.Inventario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Inventario_Activity extends AppCompatActivity {

    Button salida, entrada;

    EditText canES;

    String nombre, campo, id;
    int cantidad;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario_);

        canES = (EditText)findViewById(R.id.cantidad);

        nombre = getIntent().getStringExtra("nombre");
        campo = getIntent().getStringExtra("campo");
        id = getIntent().getStringExtra("id");
        cantidad = getIntent().getIntExtra("cantidad",0);

        inicializarFirebase();

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void actualizar(int Resultado, String id){
        Inventario I = new Inventario();
        I.setCantidad(Resultado);
        I.setNombre(nombre);
        I.setId(id);
        I.setCampo(campo);

        databaseReference.child("Inventario").child(id).setValue(I);
        Toast.makeText(this, "Salida de inventario completada", Toast.LENGTH_SHORT).show();
    }

    public void salida(View view){
        String idBD = id;

        int canBD = cantidad;
        int canSa = Integer.parseInt(canES.getText().toString());

        int ResultSalida = canBD-canSa;

        actualizar(ResultSalida, idBD);
        Toast.makeText(this, "Salida de inventario completada", Toast.LENGTH_SHORT).show();
    }

    public void entrada(View view){
        String idBD = id;

        int canBD = cantidad;
        int canEn = Integer.parseInt(canES.getText().toString());

        int ResultEntrada = canBD+canEn;

        actualizar(ResultEntrada, idBD);
        Toast.makeText(this, "Entrada al inventario completada", Toast.LENGTH_SHORT).show();
    }
}
