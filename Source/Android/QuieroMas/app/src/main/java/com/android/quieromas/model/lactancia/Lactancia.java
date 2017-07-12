package com.android.quieromas.model.lactancia;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lucas on 12/7/17.
 */
@IgnoreExtraProperties
public class Lactancia {

    ItemLactancia titulo;
    ArrayList<ItemLactancia> tabla;

    public Lactancia(){}

    public Lactancia(ItemLactancia titulo, ArrayList<ItemLactancia> tabla) {
        this.titulo = titulo;
        this.tabla = tabla;
    }

    public ItemLactancia getTitulo() {
        return titulo;
    }

    public ArrayList<ItemLactancia> getTabla() {
        return tabla;
    }
}
