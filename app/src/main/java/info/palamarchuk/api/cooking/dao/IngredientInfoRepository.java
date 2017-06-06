package info.palamarchuk.api.cooking.dao;

import info.palamarchuk.api.cooking.entity.IngredientInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IngredientInfoRepository extends AbstractDao<IngredientInfo> implements IngredientInfoDao {

    @Override
    public List<IngredientInfo> findByIngredientId(int ingredientId) {
        return this.em.createQuery("SELECT r FROM IngredientInfo r WHERE r.ingredientId = :ingredientId")
            .setParameter("ingredientId", ingredientId)
            .getResultList();
    }

    @Override
    public IngredientInfo findByIngredientIdAndLanguageId(int ingredientId, short languageId) {
        return (IngredientInfo)this.em.createQuery("SELECT r FROM IngredientInfo r WHERE r.ingredientId = :ingredientId AND r.languageId = :languageId")
            .setParameter("ingredientId", ingredientId)
            .setParameter("languageId", languageId)
            .getSingleResult();
    }
}
