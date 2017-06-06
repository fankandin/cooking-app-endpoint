package info.palamarchuk.api.cooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name="recipe_ingredient")
public class RecipeIngredient implements Serializable {

    public static final List<String> MEASUREMENTS_VALID = Arrays.asList("unit", "gram", "ml", "tsp", "tbsp", "handful", "pinch", "lug");

    /**
     * Surrogate primary key
     */
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="recipe_id")
    @JsonIgnore
    private long recipeId;

    @Column(name="ingredient_id")
    @JsonIgnore
    private int ingredientId;

    @Column(nullable = false)
    @Digits(integer=4, fraction=2)
    private BigDecimal amount;

    @Column(name="amount_netto", nullable = false)  // columnDefinition = "TINYINT(1)"
    private boolean isAmountNetto = false;

    @Column(nullable = false)
    private String measurement; // @todo make this ENUM

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Ingredient ingredient;

    public RecipeIngredient() {
    }

    public RecipeIngredient(long recipeId, int ingredientId, BigDecimal amount, String measurement) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.amount = amount;
        this.measurement = measurement;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
        return isAmountNetto;
    }

    public void setAmountNetto(boolean amountNetto) {
        this.isAmountNetto = amountNetto;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
         this.measurement = measurement;
    }

    @JsonIgnore
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
