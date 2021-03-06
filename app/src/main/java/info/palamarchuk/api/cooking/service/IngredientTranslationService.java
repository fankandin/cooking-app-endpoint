package info.palamarchuk.api.cooking.service;

import info.palamarchuk.api.cooking.dao.IngredientTranslationDao;
import info.palamarchuk.api.cooking.entity.IngredientTranslation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IngredientTranslationService implements ServiceDao<IngredientTranslation> {

    IngredientTranslationDao dao;

    @Autowired
    public IngredientTranslationService(IngredientTranslationDao dao) {
        this.dao = dao;
        this.dao.setClazz(IngredientTranslation.class);
    }

    public List<IngredientTranslation> getAll() {
        return dao.findAll();
    }

    public List<IngredientTranslation> getAllByIngredientId(long ingredientId) {
        return dao.findByIngredientId(Math.toIntExact(ingredientId));
    }

    public IngredientTranslation getById(long id) {
        return dao.findOne(Math.toIntExact(id));
    }

    public IngredientTranslation getByIngredientIdAndLangId(long ingredientId, long languageId) {
        return dao.findByIngredientIdAndLanguageId(Math.toIntExact(ingredientId), (short)languageId);
    }

    public IngredientTranslation add(IngredientTranslation recipeInfo) {
        dao.create(recipeInfo);
        return recipeInfo;
    }

    public void update(IngredientTranslation recipeInfo) {
        dao.update(recipeInfo);
    }


    public void deleteById(long id) {
        dao.deleteById(Math.toIntExact(id));
    }
}
