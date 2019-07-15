package com.example.inventarioskydata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventarioskydata.model.Inventario;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;
import java.util.UUID;

public class Agregar_Activity extends AppCompatActivity {

    EditText nomI, cantI, campoI;
    TextView ID;
    Spinner Armario, Estante;

    Button Subir, Agregar;

    String nombreImagen;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference databaseStorage;
    static final int GALLERY_INTENT = 1;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_);

        nomI = (EditText) findViewById(R.id.nombre);
        cantI = (EditText) findViewById(R.id.cantidad);
        Armario = (Spinner) findViewById(R.id.Armario);
        Estante = (Spinner) findViewById(R.id.Estante);
//        Subir = (Button)findViewById(R.id.subir);
        Agregar = (Button)findViewById(R.id.agregar);

        ArrayAdapter<CharSequence> ArmarioAdapter = ArrayAdapter.createFromResource(this, R.array.armarios, android.R.layout.simple_spinner_item);
        ArmarioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Armario.setAdapter(ArmarioAdapter);

        ArrayAdapter<CharSequence> EstanteAdapter = ArrayAdapter.createFromResource(this, R.array.estantes, android.R.layout.simple_spinner_item);
        EstanteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Estante.setAdapter(EstanteAdapter);

        inicializarFirebase();

        mProgress = new ProgressDialog(this);

        Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent foto = new Intent(Intent.ACTION_PICK);
                foto.setType("image/*");
                    String nombre = nomI.getText().toString()+"-"+UUID.randomUUID().toString().substring(0,5);
                    nombreImagen = nombre.replaceAll("\\s","");
                CrearInventario();
                startActivityForResult(foto, GALLERY_INTENT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            mProgress.setMessage("Cargando Imagen");
            mProgress.show();

            Uri uri = data.getData();
            StorageReference filePath = databaseStorage.child("fotos").child(nombreImagen);

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgress.dismiss();
                    Toast.makeText(Agregar_Activity.this, "Se subio exitosamente la foto", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Agregar_Activity.this, "No se pudo subir la imagen", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
//        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
        databaseStorage = FirebaseStorage.getInstance().getReference();
    }

    public void CrearInventario() {
        String nombre = nomI.getText().toString();
        String cantidad = cantI.getText().toString();

        String armario = Armario.getSelectedItem().toString();
        String estante = Estante.getSelectedItem().toString();

        if (nombre.equals("") || cantidad.equals("")){
            validacion();
        }else {
            String id = armario.substring(armario.length()-1)+"-"+estante.substring(armario.length())+"-"+UUID.randomUUID().toString().substring(0,8);
            Inventario I = new Inventario();
            I.setNombre(nombre);
            I.setCantidad(Integer.parseInt(cantidad));
            I.setArmario(armario);
            I.setEstante(estante);
            I.setId(id);
            I.setRutaImage("gs://inventarioskydata.appspot.com/fotos/"+nombreImagen);

            databaseReference.child("Inventario").child(I.getArmario()).child(I.getEstante()).child(I.getId()).setValue(I);
            Toast.makeText(this, "Agregado al inventario", Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }

//        public void foto(View view) {
//        Intent foto = new Intent(this, subirFoto.class);
//        startActivity(foto);
//    }

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
