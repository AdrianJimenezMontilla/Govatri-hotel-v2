package com.example.adri.ajm;

import java.util.Observable;

public class tObservable extends java.util.Observable {

    private String mensaje = "Esto es para añadir hoteles";

    public tObservable() {

    }
    public void setMensaje(String msg){
        this.mensaje = msg;
        setChanged();
        notifyObservers();
    }
}
