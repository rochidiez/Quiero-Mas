package com.android.quieromas.model.receta;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

/**
 * Created by lucas on 27/6/17.
 */

@IgnoreExtraProperties
public class Puntaje {
    public HashMap<String,Double> datos;
    public double total;

    public Puntaje(HashMap<String, Double> datos, double total) {
        this.datos = datos;
        this.total = total;
    }

    public Puntaje(){}

    public HashMap<String, Double> getDatos() {
        return datos;
    }

    public double getTotal() {
        return total;
    }
}



