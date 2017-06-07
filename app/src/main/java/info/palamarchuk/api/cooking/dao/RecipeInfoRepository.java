package info.palamarchuk.api.cooking.dao;

import info.palamarchuk.api.cooking.entity.RecipeInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class RecipeInfoRepository extends AbstractDao<RecipeInfo> implements RecipeInfoDao {

    @Override
    public List<RecipeInfo> findByRecipeId(long recipeId) {
        return this.em.createQuery("SELECT r FROM RecipeInfo r WHERE r.recipeId = :recipeId")
            .setParameter("recipeId", recipeId)
            .getResultList();
    }

    @Override
    public RecipeInfo findByRecipeIdAndLanguageId(long recipeId, short languageId) {
        try {
            RecipeInfo info = (RecipeInfo) this.em.createQuery("SELECT r FROM RecipeInfo r WHERE r.recipeId = :recipeId AND r.languageId = :languageId")
                .setParameter("recipeId", recipeId)
                .setParameter("languageId", languageId)
                .getSingleResult();
            return info;
        } catch (NoResultException e) {
            return null;
        }
    }
}
