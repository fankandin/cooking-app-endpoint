package info.palamarchuk.api.cooking.entity;

import java.io.Serializable;

public class RecipeIngredientId implements Serializable {

    private long recipeId;

    private int ingredientId;

    public int hashCode() {
        return (int)(recipeId + ingredientId);
    }

    public boolean equals(Object object) {
        if (object instanceof RecipeIngredientId) {
            RecipeIngredientId otherId = (RecipeIngredientId) object;
            return (otherId.recipeId == this.recipeId) && (otherId.ingredientId == this.ingredientId);
        }
        return false;
    }
}
