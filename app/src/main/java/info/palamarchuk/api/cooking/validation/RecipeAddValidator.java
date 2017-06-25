package info.palamarchuk.api.cooking.validation;

import info.palamarchuk.api.cooking.service.RecipeService;
import info.palamarchuk.api.cooking.entity.Recipe;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RecipeAddValidator implements Validator {

    private RecipeService service;

    public RecipeAddValidator(RecipeService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Recipe.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Recipe candidate = (Recipe)target;
        if (candidate.getId() != null) {
            errors.rejectValue("id", "forbidden.recipe.id");
        }
        if (candidate.getTitle() == "") {
            errors.rejectValue("title", "required.recipe.name");
        }
    }
}
