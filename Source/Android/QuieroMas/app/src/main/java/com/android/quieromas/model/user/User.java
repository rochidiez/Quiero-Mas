package com.android.quieromas.model.user;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by lucas on 11/7/17.
 */
@IgnoreExtraProperties
public class User {

    public UserData datos;
    public Bebe bebe;
    public ArrayList<String> recetas_favoritas;

    public User(){}

    public User(UserData datos, Bebe bebe, ArrayList<String> recetas_favoritas) {
        this.datos = datos;
        this.bebe = bebe;
        this.recetas_favoritas = recetas_favoritas;
    }

    public UserData getDatos() {
        return datos;
    }

    public Bebe getBebe() {
        return bebe;
    }

    public ArrayList<String> getRecetasFavoritas() {
        return recetas_favoritas;
    }
}
