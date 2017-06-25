package info.palamarchuk.api.cooking.validation;

import info.palamarchuk.api.cooking.service.IngredientTranslationService;
import info.palamarchuk.api.cooking.entity.IngredientTranslation;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class IngredientInfoUpdateValidator implements Validator {

    private IngredientTranslationService service;
    private IngredientTranslation existing;

    public IngredientInfoUpdateValidator(IngredientTranslationService service, IngredientTranslation existing) {
        this.service = service;
        this.existing = existing;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return IngredientTranslation.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IngredientTranslation candidate = (IngredientTranslation)target;

        if (candidate.getId() != existing.getId()) {
            errors.rejectValue("id", "inconsistent.ingredient.translation");
        }
        if (candidate.getIngredientId() != null) {
            errors.rejectValue("ingredientId", "readonly.ingredient.translation");
        }

        if (candidate.getLanguageId() != null && existing.getLanguageId() != candidate.getLanguageId()) { // language change request
            // change in the fields constrained by an unique key
            IngredientTranslation possiblyConflicting = service.getByIngredientIdAndLangId(existing.getIngredientId(), candidate.getLanguageId());
            if (possiblyConflicting != null) { // there is already ingredient info with this language
                errors.rejectValue("languageId", "duplicated.ingredient.translation");
            }
        }
    }
}
