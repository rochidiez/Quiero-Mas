package com.android.quieromas.model.receta;

/**
 * Created by lucas on 28/6/17.
 */

public class RecipeStepElement {

    private String text;
    private String basicRecipe;

    public RecipeStepElement(String text, String basicRecipe) {
        this.text = text;
        this.basicRecipe = basicRecipe;
    }

    public RecipeStepElement(String text) {
        this.text = text;
        this.basicRecipe = null;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBasicRecipe() {
        return basicRecipe;
    }

    public void setBasicRecipe(String basicRecipe) {
        this.basicRecipe = basicRecipe;
    }
}
