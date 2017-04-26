package info.palamarchuk.api.cooking.entity;

import java.math.BigDecimal;

public class RecipeIngredient {
    private int recipeId;
    private int ingredientId;
    private BigDecimal amount;
    private String measurement;
    private String measurementNote;
    private String condition;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getMeasurementNote() {
        return measurementNote;
    }

    public void setMeasurementNote(String measurementNote) {
        this.measurementNote = measurementNote;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeIngredient that = (RecipeIngredient) o;

        if (recipeId != that.recipeId) return false;
        if (ingredientId != that.ingredientId) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (measurement != null ? !measurement.equals(that.measurement) : that.measurement != null) return false;
        if (measurementNote != null ? !measurementNote.equals(that.measurementNote) : that.measurementNote != null)
            return false;
        if (condition != null ? !condition.equals(that.condition) : that.condition != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = recipeId;
        result = 31 * result + ingredientId;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (measurement != null ? measurement.hashCode() : 0);
        result = 31 * result + (measurementNote != null ? measurementNote.hashCode() : 0);
        result = 31 * result + (condition != null ? condition.hashCode() : 0);
        return result;
    }
}
