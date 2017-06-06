package info.palamarchuk.api.cooking.dao;

import info.palamarchuk.api.cooking.entity.RecipeIngredient;

import java.util.List;

public interface RecipeIngredientDao extends GenericDao<RecipeIngredient> {
    List<RecipeIngredient> findByRecipeId(long recipeId);
}
