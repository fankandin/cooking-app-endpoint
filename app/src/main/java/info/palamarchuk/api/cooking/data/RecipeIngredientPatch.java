package info.palamarchuk.api.cooking.data;

import java.math.BigDecimal;

public class RecipeIngredientPatch {
    public Long recipeId;
    public Integer ingredientId;
    public BigDecimal amount;
    public String measurement;
    public Boolean isAmountNetto;
}
