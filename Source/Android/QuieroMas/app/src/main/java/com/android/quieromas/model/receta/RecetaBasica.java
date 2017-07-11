package com.android.quieromas.model.receta;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by lucas on 11/7/17.
 */

@IgnoreExtraProperties
public class RecetaBasica {

    public ArrayList<String> ingredientes;
    public String nombre;
    public String thumbnail;
    public String video;

    public RecetaBasica(){}

    public RecetaBasica(ArrayList<String> ingredientes, String nombre, String thumbnail, String video) {
        this.ingredientes = ingredientes;
        this.nombre = nombre;
        this.thumbnail = thumbnail;
        this.video = video;
    }

    public ArrayList<String> getIngredientes() {
        return ingredientes;
    }

    public String getNombre() {
        return nombre;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getVideo() {
        return video;
    }
}
