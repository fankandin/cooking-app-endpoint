package info.palamarchuk.api.cooking.data;

import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Setter
@Getter
@Accessors(chain = true)
public class RecipeIngredientEntityData implements EntityExportable {
    protected Long id;
    protected Long recipeId;
    protected Integer ingredientId;
    protected BigDecimal amount;
    protected boolean isAmountNetto = false;
    protected String measurement;
    protected String preparation;

    public RecipeIngredientEntityData setAmount(String amount) {
        this.amount = new BigDecimal(amount);
        return this;
    }

    public RecipeIngredient makeEntity() {
        RecipeIngredient entity = new RecipeIngredient();
        entity.setId(id);
        entity.setRecipeId(recipeId);
        entity.setIngredientId(ingredientId);
        entity.setAmount(amount);
        entity.setAmountNetto(isAmountNetto);
        entity.setMeasurement(measurement);
        entity.setPreparation(preparation);
        return entity;
    }
}
