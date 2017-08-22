package com.android.quieromas.model.receta;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.HashMap;

/**
 * Created by lucas on 27/6/17.
 */

@IgnoreExtraProperties
public class Puntaje {

    @PropertyName("Datos")
    public HashMap<String,Integer> datos;
    public Integer total;

    public Puntaje(HashMap<String, Integer> datos, Integer total) {
        this.datos = datos;
        this.total = total;
    }

    public Puntaje(){}

}



