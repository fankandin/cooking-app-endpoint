package info.palamarchuk.api.cooking.dao;

import info.palamarchuk.api.cooking.entity.IngredientTranslation;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class IngredientTranslationRepository extends AbstractDao<IngredientTranslation> implements IngredientTranslationDao {

    @Override
    public List<IngredientTranslation> findByIngredientId(int ingredientId) {
        return this.em.createQuery("SELECT r FROM IngredientTranslation r WHERE r.ingredientId = :ingredientId")
            .setParameter("ingredientId", ingredientId)
            .getResultList();
    }

    @Override
    public IngredientTranslation findByIngredientIdAndLanguageId(int ingredientId, short languageId) {
        try {
            IngredientTranslation info = (IngredientTranslation) this.em.createQuery("SELECT r FROM IngredientTranslation r WHERE r.ingredientId = :ingredientId AND r.languageId = :languageId")
                .setParameter("ingredientId", ingredientId)
                .setParameter("languageId", languageId)
                .getSingleResult();
            return info;
        } catch (NoResultException e) {
            return null;
        }
    }
}
