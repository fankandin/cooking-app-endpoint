package info.palamarchuk.api.cooking.service;

import info.palamarchuk.api.cooking.dao.IngredientTranslationDao;
import info.palamarchuk.api.cooking.entity.IngredientTranslation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IngredientTranslationService {

    IngredientTranslationDao dao;

    @Autowired
    public IngredientTranslationService(IngredientTranslationDao dao) {
        this.dao = dao;
        this.dao.setClazz(IngredientTranslation.class);
    }

    public List<IngredientTranslation> getAllByIngredientId(int ingredientId) {
        return dao.findByIngredientId(ingredientId);
    }

    public List<IngredientTranslation> getAll() {
        return dao.findAll();
    }

    public IngredientTranslation getById(int id) {
        return dao.findOne(id);
    }

    public IngredientTranslation getByIngredientIdAndLangId(int ingredientId, short languageId) {
        return dao.findByIngredientIdAndLanguageId(ingredientId, languageId);
    }

    public IngredientTranslation add(IngredientTranslation recipeInfo) {
        dao.create(recipeInfo);
        return recipeInfo;
    }

    public void update(IngredientTranslation recipeInfo) {
        dao.update(recipeInfo);
    }


    public void deleteById(int id) {
        dao.deleteById(id);
    }
}
