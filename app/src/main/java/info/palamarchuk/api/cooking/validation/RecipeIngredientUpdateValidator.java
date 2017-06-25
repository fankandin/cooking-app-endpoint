package info.palamarchuk.api.cooking.validation;

import info.palamarchuk.api.cooking.service.RecipeIngredientService;
import info.palamarchuk.api.cooking.data.RecipeIngredientPatch;
import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RecipeIngredientUpdateValidator implements Validator {

    private RecipeIngredientService service;
    private RecipeIngredient existing;

    public RecipeIngredientUpdateValidator(RecipeIngredientService service, RecipeIngredient existing) {
        this.service = service;
        this.existing = existing;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RecipeIngredient.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RecipeIngredientPatch patch = (RecipeIngredientPatch) target;

        if (existing.getIngredientId() != patch.ingredientId) {
            // change in the fields constrained by an unique key
            RecipeIngredient possiblyConflicting = service.getByRecipeIdAndIngredientId(patch.recipeId, patch.ingredientId);
            if (possiblyConflicting.getId() != existing.getId()) {
                errors.rejectValue("ingredientId", "duplicated.recipe-ingredient");
            }
        }
    }
}
