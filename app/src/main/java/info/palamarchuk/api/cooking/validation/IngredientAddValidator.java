package info.palamarchuk.api.cooking.validation;

import info.palamarchuk.api.cooking.IngredientService;
import info.palamarchuk.api.cooking.entity.Ingredient;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class IngredientAddValidator implements Validator {

    private IngredientService service;

    public IngredientAddValidator(IngredientService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Ingredient.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Ingredient candidate = (Ingredient)target;
        if (candidate.getId() != null) {
            errors.rejectValue("id", "forbidden.ingredient.id");
        }
        if (candidate.getName() == "") {
            errors.rejectValue("name", "required.ingredient.name");
        }
    }
}
