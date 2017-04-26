package info.palamarchuk.api.cooking.entity;

public class RecipeCousine {
    private int recipeId;
    private int cousineId;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getCousineId() {
        return cousineId;
    }

    public void setCousineId(int cousineId) {
        this.cousineId = cousineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeCousine that = (RecipeCousine) o;

        if (recipeId != that.recipeId) return false;
        if (cousineId != that.cousineId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = recipeId;
        result = 31 * result + cousineId;
        return result;
    }
}
