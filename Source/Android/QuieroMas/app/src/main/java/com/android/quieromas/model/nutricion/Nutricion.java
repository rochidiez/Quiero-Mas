package com.android.quieromas.model.nutricion;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lucas on 24/7/17.
 */
@IgnoreExtraProperties
public class Nutricion {

    @PropertyName("Secciones")
    HashMap<String,String> secciones;

    @PropertyName("Meses")
    HashMap<String,ArrayList<NutricionItem>> meses;

    @PropertyName("Título")
    String titulo;


    public Nutricion(){}

    public Nutricion(String titulo, HashMap<String, String> secciones, HashMap<String,ArrayList<NutricionItem>> meses) {
        this.titulo = titulo;
        this.secciones = secciones;
        this.meses = meses;
    }


    public String getTitulo() {
        return titulo;
    }

    public HashMap<String, String> getSecciones() {
        return secciones;
    }

    public HashMap<String,ArrayList<NutricionItem>> getMeses() {
        return meses;
    }
}
