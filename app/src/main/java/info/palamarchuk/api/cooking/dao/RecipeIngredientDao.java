package info.palamarchuk.api.cooking.dao;

import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class RecipeIngredientDao extends AbstractDao<RecipeIngredient> implements IRecipeIngredientDao {

    @Override
    public List<RecipeIngredient> findByRecipeId(long recipeId) {
        return this.em.createQuery("SELECT r FROM RecipeIngredient r WHERE r.recipeId = :recipeId")
            .setParameter("recipeId", recipeId)
            .getResultList();
    }


}
