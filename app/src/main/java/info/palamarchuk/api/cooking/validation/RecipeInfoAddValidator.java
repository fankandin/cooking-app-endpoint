package info.palamarchuk.api.cooking.validation;

import info.palamarchuk.api.cooking.RecipeInfoService;
import info.palamarchuk.api.cooking.entity.RecipeInfo;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RecipeInfoAddValidator implements Validator {

    private RecipeInfoService service;

    public RecipeInfoAddValidator(RecipeInfoService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RecipeInfo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RecipeInfo candidate = (RecipeInfo)target;
        RecipeInfo existing = service.getByRecipeIdAndLangId(candidate.getRecipeId(), candidate.getLanguageId());
        if (existing != null) {
            errors.rejectValue("languageId", "duplicated.recipe.translation");
        }
    }
}
