package com.android.quieromas.model.user;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.Date;

/**
 * Created by lucas on 11/7/17.
 */
@IgnoreExtraProperties
public class UserData {

    @PropertyName("Nombre Completo")
    public String nombreCompleto;

    @PropertyName("Email")
    public String email;

    @PropertyName("Fecha de Nacimiento")
    public String fechaDeNacimiento;

    public UserData(){}

    public UserData(String nombreCompleto, String email, String fechaDeNacimiento) {
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

}
