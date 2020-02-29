package com.example.adri.ajm;

import java.util.Observable;

public class testObservable extends Observable {

    private String mensaje = "Aun no ha añadido un hotel";

    public testObservable() {

    }
    public void setMensaje(String msg){
        this.mensaje = msg;
        setChanged();
        notifyObservers();
    }
}
