package com.android.quieromas.model.quienesSomos;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by lucas on 21/7/17.
 */
@IgnoreExtraProperties
public class QuienesSomos {

    @PropertyName("Título")
    String titulo;

    @PropertyName("Texto")
    String texto;

    public QuienesSomos(){}

    public QuienesSomos(String titulo, String texto) {
        this.titulo = titulo;
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }

}
