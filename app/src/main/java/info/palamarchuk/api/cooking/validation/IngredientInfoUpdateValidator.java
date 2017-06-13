package info.palamarchuk.api.cooking.validation;

import info.palamarchuk.api.cooking.IngredientInfoService;
import info.palamarchuk.api.cooking.entity.IngredientInfo;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class IngredientInfoUpdateValidator implements Validator {

    private IngredientInfoService service;
    private IngredientInfo existing;

    public IngredientInfoUpdateValidator(IngredientInfoService service, IngredientInfo existing) {
        this.service = service;
        this.existing = existing;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return IngredientInfo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IngredientInfo candidate = (IngredientInfo)target;

        if (candidate.getId() != existing.getId()) {
            errors.rejectValue("id", "inconsistent.ingredient.translation.id");
        }
        if (candidate.getIngredientId() != null) {
            errors.rejectValue("ingredientId", "readonly.ingredient.ingredient");
        }

        if (candidate.getLanguageId() != null && existing.getLanguageId() != candidate.getLanguageId()) { // language change request
            // change in the fields constrained by an unique key
            IngredientInfo possiblyConflicting = service.getByIngredientIdAndLangId(existing.getIngredientId(), candidate.getLanguageId());
            if (possiblyConflicting != null) { // there is already ingredient info with this language
                errors.rejectValue("languageId", "duplicated.ingredient.translation");
            }
        }
    }
}
