package info.palamarchuk.api.cooking.validation;

import info.palamarchuk.api.cooking.ErrorResponseData;
import info.palamarchuk.api.cooking.RecipeInfoService;
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
            errors.rejectValue("id", "inconsistent.recipe.translation.id");
        }

        if (existing.getLanguageId() != candidate.getLanguageId() || existing.getRecipeId() != candidate.getRecipeId()) {
            // change in the fields constrained by an unique key
            RecipeInfo possiblyConflicting = service.getByRecipeIdAndLangId(candidate.getRecipeId(), candidate.getLanguageId());
            if (possiblyConflicting.getId() != candidate.getId()) {
                errors.rejectValue("languageId", "duplicated.recipe.translation");
            }
        }
    }
}
