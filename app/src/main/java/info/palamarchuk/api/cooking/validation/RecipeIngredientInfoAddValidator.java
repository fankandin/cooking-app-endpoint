package info.palamarchuk.api.cooking.validation;

import info.palamarchuk.api.cooking.RecipeIngredientInfoService;
import info.palamarchuk.api.cooking.entity.IngredientInfo;
import info.palamarchuk.api.cooking.entity.RecipeIngredientInfo;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RecipeIngredientInfoAddValidator implements Validator {

    private RecipeIngredientInfoService service;

    public RecipeIngredientInfoAddValidator(RecipeIngredientInfoService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return IngredientInfo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RecipeIngredientInfo candidate = (RecipeIngredientInfo)target;
        RecipeIngredientInfo existing = service.getByRecipeIngredientIdAndLangId(candidate.getRecipeIngredientId(), candidate.getLanguageId());
        if (existing != null) {
            errors.rejectValue("languageId", "duplicated.recipe-ingredient.translation");
        }
    }
}
