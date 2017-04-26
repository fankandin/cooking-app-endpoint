package info.palamarchuk.api.cooking.entity;

import java.io.Serializable;

public class RecipeTagPK implements Serializable {
    private int recipeId;
    private int tagId;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeTagPK that = (RecipeTagPK) o;

        if (recipeId != that.recipeId) return false;
        if (tagId != that.tagId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = recipeId;
        result = 31 * result + tagId;
        return result;
    }
}
