package com.example.adri.ajm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adri.ajm.modelos.Hotel;
import com.example.adri.ajm.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Observable;
import java.util.Observer;


public class NewActivity extends AppCompatActivity implements Observer {

    EditText Nombre, Tipo, descripcion, telefono, ubicacion;
    String foto;
    private Button boton;
    private NotificationManager nm;
    private final String CHANNEL_ID = "Channel2";
    private NotificationCompat.Builder not;
    private final int NOTIFICATION_ID = 666;
    private NotificationChannel ch;
    private final String CHANNEL_NAME = "Notificaciones";
    private Uri uri;
    String msg = "Hotel añadido";


    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);


        uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.audio);
        boton = findViewById(R.id.boton);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Nombre = findViewById(R.id.newNameRestaurant) ;
        Tipo = findViewById(R.id.newTipeRestaurant);
        descripcion = findViewById(R.id.newdescriptionRestaurant);
        telefono = findViewById(R.id.newtelefonoRestaurant);
        foto = "https://firebasestorage.googleapis.com/v0/b/proyectoandroidstudio-ma-6712a.appspot.com/o/hotel-posadas-de-espana-cartagena-exterior-81d3d09.jpg?alt=media&token=5e76e023-c5c2-4bd0-bf28-6e45544630ff";

        boton.setOnClickListener(v ->
        {

            nm =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);


            createNotificationChannel();


            not = new NotificationCompat.Builder(NewActivity.this, CHANNEL_ID);
            not.setSmallIcon(R.drawable.ic_bell); // Android me obliga a definir un icono
            not.setContentTitle("Govatri Hotel");
            not.setContentText("Nuevo hotel añadido!");
            not.setSound(uri);
            not.setPriority(NotificationCompat.PRIORITY_HIGH);



            nm.notify(NOTIFICATION_ID, not.build());
            String  nom = Nombre.getText().toString();
            String  tip = Tipo.getText().toString();
            String  des = descripcion.getText().toString();
            String  ubi = "geo:0,0?q=" + Nombre.getText().toString();
            String  tel = telefono.getText().toString();

            String id = databaseReference.push().getKey();

            Hotel rest = new Hotel(nom, tip, des, foto, ubi, tel);

            databaseReference.child("hotels").child(id).setValue(rest);

            Intent intent = new Intent(NewActivity.this, MainActivity.class) ;
            startActivity(intent) ;

            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

        });


    }

    private void createNotificationChannel ()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {


            AudioAttributes att = new AudioAttributes.Builder()
                    .setContentType((AudioAttributes.CONTENT_TYPE_SONIFICATION))
                    .setUsage((AudioAttributes.USAGE_NOTIFICATION))
                    .build();


            ch = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH );

            ch.setDescription(CHANNEL_NAME);


            ch.setSound(uri,att);


            ch.enableVibration(true);
            ch.setVibrationPattern(new long [] {10000l} );


            ch.enableLights(true);


            nm.createNotificationChannel(ch);


            ch.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }
}
