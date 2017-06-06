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

        if (existing.getLanguageId() != candidate.getLanguageId() || existing.getIngredientId() != candidate.getIngredientId()) {
            // change in the fields constrained by an unique key
            IngredientInfo possiblyConflicting = service.getByIngredientIdAndLangId(candidate.getIngredientId(), candidate.getLanguageId());
            if (possiblyConflicting.getId() != candidate.getId()) {
                errors.rejectValue("languageId", "duplicated.ingredient.translation");
            }
        }
    }
}
