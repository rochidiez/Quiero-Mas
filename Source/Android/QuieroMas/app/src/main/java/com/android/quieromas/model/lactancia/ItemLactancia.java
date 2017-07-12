package com.android.quieromas.model.lactancia;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by lucas on 12/7/17.
 */
@IgnoreExtraProperties
public class ItemLactancia {

    String titulo;
    String texto;
    String html;

    public ItemLactancia(){}

    public ItemLactancia(String titulo, String texto, String html) {
        this.titulo = titulo;
        this.texto = texto;
        this.html = html;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }

    public String getHtml() {
        return html;
    }
}
