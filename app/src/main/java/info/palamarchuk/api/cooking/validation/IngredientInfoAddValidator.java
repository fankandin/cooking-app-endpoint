package info.palamarchuk.api.cooking.validation;

import info.palamarchuk.api.cooking.IngredientInfoService;
import info.palamarchuk.api.cooking.entity.IngredientInfo;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class IngredientInfoAddValidator implements Validator {

    private IngredientInfoService service;

    public IngredientInfoAddValidator(IngredientInfoService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return IngredientInfo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IngredientInfo candidate = (IngredientInfo)target;
        IngredientInfo existing = service.getByIngredientIdAndLangId(candidate.getIngredientId(), candidate.getLanguageId());
        if (existing != null) {
            errors.rejectValue("languageId", "duplicated.ingredient.translation");
        }
    }
}
