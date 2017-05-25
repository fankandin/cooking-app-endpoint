package info.palamarchuk.api.cooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
@Table(name="recipe_ingredient")
@IdClass(RecipeIngredientId.class)
public class RecipeIngredient {

    @Id
    @Column(name="recipe_id")
    @JsonIgnore
    private long recipeId;

    @Id
    @Column(name="ingredient_id")
    @JsonIgnore
    private int ingredientId;

    @Column(nullable = false)
    @Digits(integer=4, fraction=2)
    private BigDecimal amount;

    @Column(name="amount_netto")  // columnDefinition = "TINYINT(1)"
    private boolean amountNetto = false;

    @Column(nullable = false)
    private String measurement;

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Ingredient ingredient;

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
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

    public boolean isAmountNetto() {
        return amountNetto;
    }

    public void setAmountNetto(boolean amountNetto) {
        this.amountNetto = amountNetto;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
