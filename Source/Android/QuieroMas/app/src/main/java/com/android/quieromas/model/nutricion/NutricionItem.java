package com.android.quieromas.model.nutricion;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by lucas on 3/8/17.
 */
@IgnoreExtraProperties
public class NutricionItem {

    String descripcion;
    String titulo;

    public NutricionItem() {
    }

    public NutricionItem(String descripcion, String titulo) {
        this.descripcion = descripcion;
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTitulo() {
        return titulo;
    }
}
