package com.android.quieromas.model.user;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by lucas on 11/7/17.
 */
@IgnoreExtraProperties
public class UserData {

    public String nombre_completo;
    public String email;
    public String fecha_de_nacimiento;

    public UserData(){}

    public UserData(String nombre_completo, String email, String fecha_de_nacimiento) {
        this.nombre_completo = nombre_completo;
        this.email = email;
        this.fecha_de_nacimiento = fecha_de_nacimiento;
    }

    public String getNombreCompleto() {
        return nombre_completo;
    }

    public String getEmail() {
        return email;
    }

    public String getFechaDeNacimiento() {
        return fecha_de_nacimiento;
    }
}
