package info.palamarchuk.api.cooking.data;

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
            errors.rejectValue("Amount of an ingredient cannot be empty", "INV_EMPTY_VAL");
        }
        if (candidate.measurement == null) {
            errors.rejectValue("Measurement unit of an ingredient cannot be empty", "INV_EMPTY_VAL");
        }
        if (!RecipeIngredient.MEASUREMENTS_VALID.contains(candidate.measurement)) {
            errors.rejectValue("Invalidt measurement unit of an ingredient", "INV_ENUM_VAL");
        }
    }
}
