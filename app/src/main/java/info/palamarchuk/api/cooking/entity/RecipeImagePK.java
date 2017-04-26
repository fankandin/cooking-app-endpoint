package info.palamarchuk.api.cooking.entity;

import java.io.Serializable;

public class RecipeImagePK implements Serializable {
    private int recipeId;
    private int imageId;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeImagePK that = (RecipeImagePK) o;

        if (recipeId != that.recipeId) return false;
        if (imageId != that.imageId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = recipeId;
        result = 31 * result + imageId;
        return result;
    }
}
