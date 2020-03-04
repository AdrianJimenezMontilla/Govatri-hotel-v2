package com.example.adri.ajm;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.adri.ajm.modelos.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PerfilActivity extends AppCompatActivity {


    DatabaseReference bbdd;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        bbdd = FirebaseDatabase.getInstance().getReference("usuario");
        database = FirebaseDatabase.getInstance();


        final TextView nombre, apellido;
        nombre = findViewById(R.id.Nombre);
        apellido = findViewById(R.id.Apellido);

        database.getReference("usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()) {

                    Usuario usuario = new Usuario();

                    usuario = snapshot.getValue(Usuario.class);
                    usuario.setIdUsuario(snapshot.getKey());
                    String segundonombre = usuario.getApellidos();
                    String primernombre = usuario.getNombre();

                    String clave = dataSnapshot.getKey();

                    //Asociar los datos de la base de datos al Hit de los editText

                    nombre.setHint(primernombre);
                    apellido.setHint(segundonombre);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void goBack(View view) {
        onBackPressed();
    }
}
