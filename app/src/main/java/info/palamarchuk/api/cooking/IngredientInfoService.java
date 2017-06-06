package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.dao.IngredientInfoDao;
import info.palamarchuk.api.cooking.entity.IngredientInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IngredientInfoService {

    IngredientInfoDao dao;

    @Autowired
    public IngredientInfoService(IngredientInfoDao dao) {
        this.dao = dao;
        this.dao.setClazz(IngredientInfo.class);
    }

    public List<IngredientInfo> getAllByIngredientId(int ingredientId) {
        return dao.findByIngredientId(ingredientId);
    }

    public List<IngredientInfo> getAll() {
        return dao.findAll();
    }

    public IngredientInfo getById(int id) {
        return dao.findOne(id);
    }

    public IngredientInfo getByIngredientIdAndLangId(int ingredientId, short languageId) {
        return dao.findByIngredientIdAndLanguageId(ingredientId, languageId);
    }

    public IngredientInfo add(IngredientInfo recipeInfo) {
        dao.create(recipeInfo);
        return recipeInfo;
    }

    public void update(IngredientInfo recipeInfo) {
        dao.update(recipeInfo);
    }


    public void deleteById(int id) {
        dao.deleteById(id);
    }
}
