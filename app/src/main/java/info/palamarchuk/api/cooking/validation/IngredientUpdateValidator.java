package info.palamarchuk.api.cooking.validation;

import info.palamarchuk.api.cooking.entity.Ingredient;
import info.palamarchuk.api.cooking.service.IngredientService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class IngredientUpdateValidator implements Validator {

    private IngredientService service;

    public IngredientUpdateValidator(IngredientService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Ingredient.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Ingredient patch = (Ingredient)target;
        if (patch.getName() == null) { // the only possible value to patch
            errors.rejectValue("name", "required.ingredient");
        }
    }
}
