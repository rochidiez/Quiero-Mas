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
    public static final String INGREDIENTS = "Ingredientes";
    public static final String DEVELOPMENT = "Estimulación";
    public static final String MONTH = "mes ";
    public static final String LACTATION = "Lactancia";
    public static final String TERMS = "Términos y condiciones";
    public static final String ABOUT_US = "Sobre Quiero Más!";
    public static final String NUTRITION = "Nutrición";
    public static final String TITLE = "Título";
    public static final String SECTIONS = "Secciones";
    public static final String MONTHS = "Meses";
    public static final String SCORE = "puntaje";

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

    public DatabaseReference getScoreReference(String recipeName){
        return getRecipeReference(recipeName).child(SCORE);
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
        return getRecipesReference().child(BY_AGE);
    }

    public DatabaseReference getIngredientsReference(){
        return getRecipesReference().child(INGREDIENTS);
    }

    public DatabaseReference getTermsReference(){
        return FirebaseDatabase.getInstance().getReference(TERMS);
    }

    public DatabaseReference getAboutUsReference(){
        return FirebaseDatabase.getInstance().getReference(ABOUT_US);
    }

    public DatabaseReference getNutritionReference(){
        return FirebaseDatabase.getInstance().getReference(NUTRITION);
    }

    public DatabaseReference getNutritionSubtitleReference(){
        return getNutritionReference().child(TITLE);
    }

    public DatabaseReference getNutritionSectionsReference(){
        return getNutritionReference().child(SECTIONS);
    }

    public DatabaseReference getNutritionMonthsReference(){
        return getNutritionReference().child(MONTHS);
    }

    public DatabaseReference getNutritionMonthReference(String month){
        return getNutritionMonthsReference().child(month);
    }

}
