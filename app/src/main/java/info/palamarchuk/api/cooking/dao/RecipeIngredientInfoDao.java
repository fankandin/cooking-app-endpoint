package info.palamarchuk.api.cooking.dao;

import info.palamarchuk.api.cooking.entity.IngredientInfo;
import info.palamarchuk.api.cooking.entity.RecipeIngredientInfo;

import java.util.List;

public interface RecipeIngredientInfoDao extends GenericDao<RecipeIngredientInfo> {
    List<RecipeIngredientInfo> findByRecipeIngredientId(long recipeIngredientId);
    RecipeIngredientInfo findByRecipeIngredientIdAndLanguageId(long recipeIngredientId, short languageId);
}
