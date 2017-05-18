package com.android.quieromas.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by lucas on 18/5/17.
 */
@IgnoreExtraProperties
public class Bebe {

    public String name;
    public String nickname;
    public Date birthdate;

    public Bebe(){}

    public Bebe(String name, String nickname, Date birthdate){
        this.name = name;
        this.nickname = nickname;
        this.birthdate = birthdate;
    }


}
