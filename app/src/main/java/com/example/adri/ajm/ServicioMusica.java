package com.example.adri.ajm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class ServicioMusica extends Service
{
    private MediaPlayer media ;

    /**
     */
    @Override
    public void onCreate()
    {
        Log.i("MUSICA", "Servicio creado") ;
        media = MediaPlayer.create(this, R.raw.audioaa) ;
    }

    /**
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags,
                              int startId)
    {
        Log.i("MUSICA", "Servicio arrancado") ;
        media.start() ;


        return START_STICKY ;
    }


    @Override
    public void onDestroy()
    {
        Log.i("MUSICA", "Servicio destruido") ;
        media.stop() ;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

