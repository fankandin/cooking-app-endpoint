package info.palamarchuk.api.cooking.dao;

import info.palamarchuk.api.cooking.entity.IngredientInfo;

import java.util.List;

public interface IngredientInfoDao extends GenericDao<IngredientInfo> {
    List<IngredientInfo> findByIngredientId(int ingredientId);
    IngredientInfo findByIngredientIdAndLanguageId(int ingredientId, short languageId);
}
