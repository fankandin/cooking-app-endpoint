package info.palamarchuk.api.cooking.validation;

import info.palamarchuk.api.cooking.data.RecipeIngredientPatch;
import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RecipeIngredientAddValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return RecipeIngredientPatch.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RecipeIngredientPatch candidate = (RecipeIngredientPatch)target;

        if (candidate.amount == null) {
            errors.rejectValue("amount", "empty.ingredient.amount");
        }
        if (candidate.measurement == null) {
            errors.rejectValue("measurement", "empty.ingredient.measurement");
        }
        if (!RecipeIngredient.MEASUREMENTS_VALID.contains(candidate.measurement)) {
            errors.rejectValue("measurement","invalid.ingredient.measurement");
        }
    }
}
