package com.android.quieromas.model.receta;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by lucas on 13/7/17.
 */
@IgnoreExtraProperties
public class Postre {
    public String nombre;
    public String descripcion;

    public Postre(){}

    public Postre(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
