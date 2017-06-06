package info.palamarchuk.api.cooking.dao;

import info.palamarchuk.api.cooking.entity.RecipeInfo;

import java.util.List;

public interface RecipeInfoDao extends GenericDao<RecipeInfo> {
    List<RecipeInfo> findByRecipeId(long recipeId);
    RecipeInfo findByRecipeIdAndLanguageId(long recipeId, short languageId);
}
