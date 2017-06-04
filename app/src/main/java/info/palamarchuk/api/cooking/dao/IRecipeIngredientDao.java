package info.palamarchuk.api.cooking.dao;

import info.palamarchuk.api.cooking.entity.RecipeIngredient;

import java.util.List;

public interface IRecipeIngredientDao extends IGenericDao<RecipeIngredient> {
    List<RecipeIngredient> findByRecipeId(long recipeId);
}
