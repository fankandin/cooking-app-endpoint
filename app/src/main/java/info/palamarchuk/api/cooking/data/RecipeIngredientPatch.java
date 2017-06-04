package info.palamarchuk.api.cooking.data;

import java.math.BigDecimal;

public class RecipeIngredientPatch {
    public Integer ingredientId;
    public BigDecimal amount;
    public String measurement;
    public Boolean isAmountNetto;
}
