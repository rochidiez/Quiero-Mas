package com.android.quieromas.model;

/**
 * Created by lucas on 28/6/17.
 */

public class FirebaseDatabaseKeys {

    private static FirebaseDatabaseKeys mInstance= null;
    public static final String RECIPES = "Recetas";
    public static final String DESSERTS = "Postres";

    protected FirebaseDatabaseKeys(){}

    public static synchronized FirebaseDatabaseKeys getInstance(){
        if(null == mInstance){
            mInstance = new FirebaseDatabaseKeys();
        }
        return mInstance;
    }
}
