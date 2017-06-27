package com.android.quieromas.model.receta;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by lucas on 27/6/17.
 */
@IgnoreExtraProperties
public class Ingrediente {

    public String nombre;
    public String nombre_basico;

    public  Ingrediente(){}

    public Ingrediente(String nombre, String nombre_basico) {
        this.nombre = nombre;
        this.nombre_basico = nombre_basico;
    }

    public Ingrediente(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombre_basico() {
        return nombre_basico;
    }
}
