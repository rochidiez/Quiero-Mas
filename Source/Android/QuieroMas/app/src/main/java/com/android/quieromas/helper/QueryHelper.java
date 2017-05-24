package com.android.quieromas.helper;

/**
 * Created by lucas on 24/5/17.
 */

public class QueryHelper {

    public static String buildQueryParameter(String uid, String name, String birthdate, String email, String babyBirthdate, String babyName, String babyNickname){
        //https://us-central1-quiero-mas.cloudfunctions.net/registrar
        // ?text=IBObsbDGMNRrC1ra5U50QpBhuyE3+lucas+10/5/1990+lucasputerman@gmail.com+10/5/2016+bebe+bebito
        StringBuilder builder = new StringBuilder();
        builder.append(uid);
        builder.append(" ");

        String[] splittedName = name.split(" ");
        for(int i = 0; i < splittedName.length; i++){
            builder.append(splittedName[i]);
            if(i < splittedName.length -1){
                builder.append("-");
            }
        }
        builder.append(" ");
        builder.append(birthdate);
        builder.append(" ");
        builder.append(email);
        builder.append(" ");
        builder.append(babyBirthdate);
        builder.append(" ");
        String[] splittedBabyName = babyName.split(" ");
        for(int i = 0; i < splittedBabyName.length; i++){
            builder.append(splittedBabyName[i]);
            if(i < splittedBabyName.length -1){
                builder.append("-");
            }
        }
        builder.append(" ");
        String[] splittedBabyNickname = babyNickname.split(" ");
        for(int i = 0; i < splittedBabyNickname.length; i++){
            builder.append(splittedBabyNickname[i]);
            if(i < splittedBabyNickname.length -1){
                builder.append("-");
            }
        }

        return builder.toString();

    }

}
