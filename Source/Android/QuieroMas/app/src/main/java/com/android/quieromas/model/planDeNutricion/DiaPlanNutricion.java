package com.android.quieromas.model.planDeNutricion;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by lucas on 12/7/17.
 */
@IgnoreExtraProperties
public class DiaPlanNutricion {

    public Comida almuerzo;
    public Comida cena;

    @PropertyName("tip_desarrollo")
    public String tipDesarrollo;

    public DiaPlanNutricion(){}

    public DiaPlanNutricion(Comida almuerzo, Comida cena, String tipDesarrollo) {
        this.almuerzo = almuerzo;
        this.cena = cena;
        this.tipDesarrollo = tipDesarrollo;
    }

    public Comida getAlmuerzo() {
        return almuerzo;
    }

    public Comida getCena() {
        return cena;
    }

    public String getTipDesarrollo() {
        return tipDesarrollo;
    }
}
