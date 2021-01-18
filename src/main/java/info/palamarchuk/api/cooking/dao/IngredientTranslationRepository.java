package info.palamarchuk.api.cooking.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import info.palamarchuk.api.cooking.entity.IngredientTranslation;

public interface IngredientTranslationRepository extends JpaRepository<IngredientTranslation, Long> {

    @Query("SELECT r FROM IngredientTranslation r WHERE r.ingredientId = ?1")
    List<IngredientTranslation> findByIngredientId(int ingredientId);

    @Query("SELECT r FROM IngredientTranslation r WHERE r.ingredientId = ?1 AND r.languageId = ?2")
    IngredientTranslation findByIngredientIdAndLanguageId(int ingredientId, short languageId);

}
