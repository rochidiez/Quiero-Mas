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
    public static final String DESSERTS = "Postres";
    public static final String USERS = "Usuarios";
    public static final String BY_NAME = "Por Nombre";

    public FirebaseDatabaseHelper(){
        mAuth = FirebaseAuth.getInstance();
    }


    public DatabaseReference getCurrentUserReference(){
        return FirebaseDatabase.getInstance().getReference(USERS).child(mAuth.getCurrentUser().getUid());
    }

    public DatabaseReference getRecipesReference(){
        return FirebaseDatabase.getInstance().getReference(RECIPES);
    }

    public DatabaseReference getRecipeReference(String recipeName){
        return getRecipesReference().child(BY_NAME).child(recipeName);
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
}
