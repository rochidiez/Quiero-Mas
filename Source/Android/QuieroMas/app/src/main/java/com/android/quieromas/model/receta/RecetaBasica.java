package com.android.quieromas.model.receta;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by lucas on 11/7/17.
 */

@IgnoreExtraProperties
public class RecetaBasica {

    public ArrayList<String> ingredientes;
    public ArrayList<String> pasos;
    public String nombre;
    public String thumbnail;
    public String video;

    public RecetaBasica(){}

    public RecetaBasica(ArrayList<String> ingredientes, ArrayList<String> pasos, String nombre, String thumbnail, String video ) {
        this.ingredientes = ingredientes;
        this.pasos = pasos;
        this.nombre = nombre;
        this.thumbnail = thumbnail;
        this.video = video;
    }

    public ArrayList<String> getIngredientes() {
        return ingredientes;
    }

    public ArrayList<String> getPasos() {
        return pasos;
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
