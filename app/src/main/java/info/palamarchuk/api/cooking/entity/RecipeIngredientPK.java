package info.palamarchuk.api.cooking.entity;

import java.io.Serializable;

public class RecipeIngredientPK implements Serializable {
    private int recipeId;
    private int ingredientId;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeIngredientPK that = (RecipeIngredientPK) o;

        if (recipeId != that.recipeId) return false;
        if (ingredientId != that.ingredientId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = recipeId;
        result = 31 * result + ingredientId;
        return result;
    }
}
