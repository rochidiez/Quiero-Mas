package com.android.quieromas.model.user;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by lucas on 18/5/17.
 */
@IgnoreExtraProperties
public class Bebe {

    public String nombre;
    public String apodo;
    public String fecha_de_nacimiento;

    public Bebe(){}

    public Bebe(String nombre, String apodo, String fecha_de_nacimiento){
        this.nombre = nombre;
        this.apodo = apodo;
        this.fecha_de_nacimiento = fecha_de_nacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApodo() {
        return apodo;
    }

    public String getFechaDeNacimiento() {
        return fecha_de_nacimiento;
    }
}
