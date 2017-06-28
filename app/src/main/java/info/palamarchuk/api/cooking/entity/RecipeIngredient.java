package info.palamarchuk.api.cooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name="recipe_ingredient")
public class RecipeIngredient implements IdNumerableEntity {

    public static final List<String> MEASUREMENTS_VALID = Arrays.asList("unit", "gram", "ml", "tsp", "tbsp", "handful", "pinch", "lug");

    /**
     * Surrogate primary key
     */
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="recipe_id")
    @JsonIgnore
    private Long recipeId;

    @Column(name="ingredient_id")
    @JsonIgnore
    private Integer ingredientId;

    @Column(nullable = false)
    @Digits(integer=4, fraction=2)
    private BigDecimal amount;

    @Column(name="amount_netto", nullable = false)  // columnDefinition = "TINYINT(1)"
    private boolean isAmountNetto = false;

    /**
     * In the DB this refers to a lookup table, which does not have a surrogate int key.
     * In practice the lookup table serves only as a constraint and replaces undesirable usage of ENUM.
     * So the application should never need to join this lookup table, and the constraint is maintained by the DB.
     * Also note that JPA is not responsible for generating schema, it's completely under the responsibility of Liquibase,
     *  which uncouples the entities from dealing with such nuances without any consistency leak.
     */
    @Size(max=8, message="too long measurement")
    private String measurement;

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Ingredient ingredient;

    @Size(max=255, message="too long preparation")
    private String preparation;

    public RecipeIngredient() {
    }

    public RecipeIngredient(Long recipeId, Integer ingredientId, BigDecimal amount, String measurement) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.amount = amount;
        this.measurement = measurement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
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

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
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
