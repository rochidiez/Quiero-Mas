package com.android.quieromas.model.receta;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by lucas on 27/6/17.
 */
@IgnoreExtraProperties
public class Receta {

    public String thumbnail;
    public String tip_nutricional;
    public String variante;
    public String video;
    public Puntaje puntaje;
    public ArrayList<String> pasos;
    public ArrayList<Ingrediente> ingredientes;
    public String titulo;

    public Receta(String thumbnail, String tip_nutricional, String variante, String video, com.android.quieromas.model.receta.Puntaje puntaje, ArrayList<String> pasos, ArrayList<Ingrediente> ingredientes) {
        this.thumbnail = thumbnail;
        this.tip_nutricional = tip_nutricional;
        this.variante = variante;
        this.video = video;
        this.puntaje = puntaje;
        this.pasos = pasos;
        this.ingredientes = ingredientes;
    }

    public Receta(){}

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTip_nutricional() {
        return tip_nutricional;
    }

    public String getVariante() {
        return variante;
    }

    public String getVideo() {
        return video;
    }

    public Puntaje getPuntaje() {
        return puntaje;
    }

    public ArrayList<String> getPasos() {
        return pasos;
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public String getTitulo(){return titulo;}

}
