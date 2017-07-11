package com.android.quieromas.model.user;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.Date;

/**
 * Created by lucas on 18/5/17.
 */
@IgnoreExtraProperties
public class Bebe {

    @PropertyName("Nombre")
    public String nombre;

    @PropertyName("Apodo")
    public String apodo;

    @PropertyName("Fecha de Nacimiento")
    public String fechaDeNacimiento;

    public Bebe(){}

    public Bebe(String nombre, String apodo, String fechaDeNacimiento){
        this.nombre = nombre;
        this.apodo = apodo;
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

}
