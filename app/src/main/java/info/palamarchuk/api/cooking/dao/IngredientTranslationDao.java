package info.palamarchuk.api.cooking.dao;

import info.palamarchuk.api.cooking.entity.IngredientTranslation;

import java.util.List;

public interface IngredientTranslationDao extends GenericDao<IngredientTranslation> {
    List<IngredientTranslation> findByIngredientId(int ingredientId);
    IngredientTranslation findByIngredientIdAndLanguageId(int ingredientId, short languageId);
}
