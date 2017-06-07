package info.palamarchuk.api.cooking.dao;

import info.palamarchuk.api.cooking.entity.RecipeIngredientInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class RecipeIngredientInfoRepository extends AbstractDao<RecipeIngredientInfo> implements RecipeIngredientInfoDao {

    @Override
    public List<RecipeIngredientInfo> findByRecipeIngredientId(long recipeIngredientId) {
        return this.em.createQuery("SELECT r FROM IngredientInfo r WHERE r.ingredientId = :ingredientId")
            .setParameter("recipeIngredientId", recipeIngredientId)
            .getResultList();
    }

    @Override
    public RecipeIngredientInfo findByRecipeIngredientIdAndLanguageId(long recipeIngredientId, short languageId) {
        try {
            RecipeIngredientInfo info = (RecipeIngredientInfo) this.em.createQuery("SELECT r FROM RecipeIngredientInfo r WHERE r.recipeIngredientId = :recipeIngredientId AND r.languageId = :languageId")
                .setParameter("recipeIngredientId", recipeIngredientId)
                .setParameter("languageId", languageId)
                .getSingleResult();
            return info;
        } catch (NoResultException e) {
            return null;
        }
    }
}
