package com.example.inventarioskydata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventarioskydata.model.Inventario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Inventario_Activity extends AppCompatActivity {

    Button salida, entrada;

    EditText canES;
    TextView txtNombre, txtArmario, txtEstamte;
    ImageView imageInventario;


    String nombre, campo, id, estante, armario, rutaImagen;
    int cantidad;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference databaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario_);

        canES = (EditText)findViewById(R.id.cantidad);
        txtNombre = (TextView)findViewById(R.id.txtNombre);
        txtArmario = (TextView)findViewById(R.id.txtArmario);
        txtEstamte = (TextView)findViewById(R.id.txtEstante);
        imageInventario = (ImageView)findViewById(R.id.imgInventario);

        nombre = getIntent().getStringExtra("nombre");
        id = getIntent().getStringExtra("id");
        cantidad = getIntent().getIntExtra("cantidad",0);
        estante = getIntent().getStringExtra("estante");
        armario = getIntent().getStringExtra("armario");
        rutaImagen = getIntent().getStringExtra("image");

        txtNombre.setText(nombre);
        txtArmario.setText(armario);
        txtEstamte.setText(rutaImagen);

        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/inventarioskydata.appspot.com/o/fotos%2Ftt8850-69ada?alt=media&token=d7392052-80fc-4b74-b03d-ea5c69aab667").into(imageInventario);

        inicializarFirebase();

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseStorage = FirebaseStorage.getInstance().getReference();
    }

    public void actualizar(int Resultado, String id){
        Inventario I = new Inventario();
        I.setCantidad(Resultado);
        I.setNombre(nombre);
        I.setId(id);
        I.setEstante(estante);
        I.setArmario(armario);

        databaseReference.child("Inventario").child(I.getArmario()).child(I.getEstante()).child(I.getId()).setValue(I);
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
