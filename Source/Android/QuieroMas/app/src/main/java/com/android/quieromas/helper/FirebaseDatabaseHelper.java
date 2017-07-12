package com.android.quieromas.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by lucas on 29/6/17.
 */

public class FirebaseDatabaseHelper {

    FirebaseAuth mAuth;

    public static final String RECIPES = "Recetas";
    public static final String FAVORITE_RECIPES = "Recetas Favoritas";
    public static final String BASIC_RECIPES = "Recetas Básicas";
    public static final String DESSERTS = "Postres";
    public static final String USERS = "Usuarios";
    public static final String BY_NAME = "Por Nombre";
    public static final String BY_AGE = "Por Edad";
    public static final String DEVELOPMENT = "Estimulación";
    public static final String MONTH = "mes ";
    public static final String LACTATION = "Lactancia";

    public FirebaseDatabaseHelper(){
        mAuth = FirebaseAuth.getInstance();
    }


    public DatabaseReference getCurrentUserReference(){
        return FirebaseDatabase.getInstance().getReference(USERS).child(mAuth.getCurrentUser().getUid());
    }

    public DatabaseReference getRecipesReference(){
        return FirebaseDatabase.getInstance().getReference(RECIPES);
    }

    public DatabaseReference getBasicRecipesReference(){
        return FirebaseDatabase.getInstance().getReference(BASIC_RECIPES);
    }

    public DatabaseReference getBasicRecipeReference(String recipeName){
        return getBasicRecipesReference().child(recipeName);
    }

    public DatabaseReference getRecipesByNameReference(){
        return getRecipesReference().child(BY_NAME);
    }

    public DatabaseReference getRecipeReference(String recipeName){
        return getRecipesByNameReference().child(recipeName);
    }

    public DatabaseReference getFavoriteRecipesReference(){
        return getCurrentUserReference().child(FAVORITE_RECIPES);
    }

    public DatabaseReference getDessertsReference(){
        return FirebaseDatabase.getInstance().getReference(DESSERTS);
    }

    public DatabaseReference getDessertReference(String dessert){
        return FirebaseDatabase.getInstance().getReference(DESSERTS).child(dessert);
    }

    public DatabaseReference getDevelopmentReference(){
        return FirebaseDatabase.getInstance().getReference(DEVELOPMENT);
    }

    public DatabaseReference getDevelopmentReferenceByMonth(int month){
        return getDevelopmentReference().child(MONTH  + month);
    }

    public DatabaseReference getLactationReference(){
        return FirebaseDatabase.getInstance().getReference(LACTATION);
    }

    public DatabaseReference getPlanByAgeReference(){
        return FirebaseDatabase.getInstance().getReference(BY_AGE);
    }

    public DatabaseReference getPlanForDayReference(int day){
        return getPlanByAgeReference().child(Integer.toString(day));
    }
}
