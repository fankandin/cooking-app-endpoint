package info.palamarchuk.api.cooking.validation;

import info.palamarchuk.api.cooking.RecipeIngredientInfoService;
import info.palamarchuk.api.cooking.entity.RecipeIngredientInfo;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RecipeIngredientInfoUpdateValidator implements Validator {

    private RecipeIngredientInfoService service;
    private RecipeIngredientInfo existing;

    public RecipeIngredientInfoUpdateValidator(RecipeIngredientInfoService service, RecipeIngredientInfo existing) {
        this.service = service;
        this.existing = existing;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RecipeIngredientInfo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RecipeIngredientInfo candidate = (RecipeIngredientInfo)target;

        if (candidate.getId() != existing.getId()) {
            errors.rejectValue("id", "inconsistent.recipe-ingredient.translation.id");
        }

        if (existing.getLanguageId() != candidate.getLanguageId() || existing.getRecipeIngredientId() != candidate.getRecipeIngredientId()) {
            // change in the fields constrained by an unique key
            RecipeIngredientInfo possiblyConflicting = service.getByRecipeIngredientIdAndLangId(candidate.getRecipeIngredientId(), candidate.getLanguageId());
            if (possiblyConflicting.getId() != candidate.getId()) {
                errors.rejectValue("languageId", "duplicated.recipe-ingredient.translation");
            }
        }
    }
}
