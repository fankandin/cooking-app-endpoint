package info.palamarchuk.api.cooking.validation;

import info.palamarchuk.api.cooking.service.IngredientTranslationService;
import info.palamarchuk.api.cooking.entity.IngredientTranslation;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class IngredientInfoAddValidator implements Validator {

    private IngredientTranslationService service;

    public IngredientInfoAddValidator(IngredientTranslationService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return IngredientTranslation.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IngredientTranslation candidate = (IngredientTranslation)target;
        if (candidate.getIngredientId() == null || candidate.getLanguageId() == null) {
            if (candidate.getIngredientId() == null) {
                errors.rejectValue("ingredientId", "required.ingredient.translation");
            }
            if (candidate.getLanguageId() == null) {
                errors.rejectValue("languageId", "required.ingredient.translation");
            }
            return;
        }
        IngredientTranslation existing = service.getByIngredientIdAndLangId(candidate.getIngredientId(), candidate.getLanguageId());
        if (existing != null) {
            errors.rejectValue("languageId", "duplicated.ingredient.translation");
        }
    }
}
