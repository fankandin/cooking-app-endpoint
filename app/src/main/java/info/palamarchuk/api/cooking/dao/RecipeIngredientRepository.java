package info.palamarchuk.api.cooking.dao;

import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipeIngredientRepository extends AbstractDao<RecipeIngredient> implements RecipeIngredientDao {

    @Override
    public List<RecipeIngredient> findByRecipeId(long recipeId) {
        return this.em.createQuery("SELECT r FROM RecipeIngredient r WHERE r.recipeId = :recipeId")
            .setParameter("recipeId", recipeId)
            .getResultList();
    }


}
