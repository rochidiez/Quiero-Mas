package com.android.quieromas.model.planDeNutricion;

/**
 * Created by lucas on 12/7/17.
 */

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Comida {

    public String receta;
    public String postre;

    public Comida(){}

    public Comida(String receta, String postre) {
        this.receta = receta;
        this.postre = postre;
    }

    public String getReceta() {
        return receta;
    }

    public String getPostre() {
        return postre;
    }
}
