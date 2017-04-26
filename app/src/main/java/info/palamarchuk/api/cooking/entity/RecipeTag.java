package info.palamarchuk.api.cooking.entity;

public class RecipeTag {
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

        RecipeTag recipeTag = (RecipeTag) o;

        if (recipeId != recipeTag.recipeId) return false;
        if (tagId != recipeTag.tagId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = recipeId;
        result = 31 * result + tagId;
        return result;
    }
}
