package info.palamarchuk.api.cooking.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import info.palamarchuk.api.cooking.entity.RecipeIngredient;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

    @Query("SELECT r FROM RecipeIngredient r WHERE r.recipeId = ?1")
    List<RecipeIngredient> findByRecipeId(long recipeId);

    @Query("SELECT r FROM RecipeIngredient r WHERE r.recipeId = ?1 AND r.ingredientId = ?2")
    RecipeIngredient findByRecipeIdAndIngredientId(long recipeId, int ingredientId);
}
