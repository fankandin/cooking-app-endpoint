package info.palamarchuk.api.cooking.validation;

import info.palamarchuk.api.cooking.ErrorResponseData;
import info.palamarchuk.api.cooking.RecipeInfoService;
import info.palamarchuk.api.cooking.entity.IngredientInfo;
import info.palamarchuk.api.cooking.entity.RecipeInfo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RecipeInfoUpdateValidator implements Validator {

    private RecipeInfoService service;
    private RecipeInfo existing;

    public RecipeInfoUpdateValidator(RecipeInfoService service, RecipeInfo existing) {
        this.service = service;
        this.existing = existing;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RecipeInfo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RecipeInfo candidate = (RecipeInfo)target;

        if (candidate.getId() != existing.getId()) {
            errors.rejectValue("id", "inconsistent.recipe.translation");
        }
        if (candidate.getRecipeId() != null) {
            errors.rejectValue("recipeId", "readonly.recipe.translation");
        }

        if (candidate.getLanguageId() != null && existing.getLanguageId() != candidate.getLanguageId()) { // language change request
            // change in the fields constrained by an unique key
            RecipeInfo possiblyConflicting = service.getByRecipeIdAndLangId(existing.getRecipeId(), candidate.getLanguageId());
            if (possiblyConflicting != null) { // there is already ingredient info with this language
                errors.rejectValue("languageId", "duplicated.ingredient.translation");
            }
        }
    }
}
