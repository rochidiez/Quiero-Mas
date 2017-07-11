package com.android.quieromas.model.user;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.ArrayList;

/**
 * Created by lucas on 11/7/17.
 */
@IgnoreExtraProperties
public class User {

    @PropertyName("Datos")
    public UserData datos;

    @PropertyName("Bebé")
    public Bebe bebe;

    @PropertyName("Recetas Favoritas")
    public ArrayList<String> recetasFavoritas;

    public User(){}

    public User(UserData datos, Bebe bebe, ArrayList<String> recetasFavoritas) {
        this.datos = datos;
        this.bebe = bebe;
        this.recetasFavoritas = recetasFavoritas;
    }

}
