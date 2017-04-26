package info.palamarchuk.api.cooking.entity;

public class RecipeReference {
    private int recipeId;
    private int referenceId;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeReference that = (RecipeReference) o;

        if (recipeId != that.recipeId) return false;
        if (referenceId != that.referenceId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = recipeId;
        result = 31 * result + referenceId;
        return result;
    }
}
