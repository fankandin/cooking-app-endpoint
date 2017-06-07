package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.dao.RecipeIngredientInfoDao;
import info.palamarchuk.api.cooking.entity.RecipeIngredientInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecipeIngredientInfoService {

    RecipeIngredientInfoDao dao;

    @Autowired
    public RecipeIngredientInfoService(RecipeIngredientInfoDao dao) {
        this.dao = dao;
        this.dao.setClazz(RecipeIngredientInfo.class);
    }

    public List<RecipeIngredientInfo> getAllByRecipeIngredientId(long recipeIngredientId) {
        return dao.findByRecipeIngredientId(recipeIngredientId);
    }

    public List<RecipeIngredientInfo> getAll() {
        return dao.findAll();
    }

    public RecipeIngredientInfo getById(long id) {
        return dao.findOne(id);
    }

    public RecipeIngredientInfo getByRecipeIngredientIdAndLangId(long recipeIngredientId, short languageId) {
        return dao.findByRecipeIngredientIdAndLanguageId(recipeIngredientId, languageId);
    }

    public RecipeIngredientInfo add(RecipeIngredientInfo recipeInfo) {
        dao.create(recipeInfo);
        return recipeInfo;
    }

    public void update(RecipeIngredientInfo recipeInfo) {
        dao.update(recipeInfo);
    }


    public void deleteById(long id) {
        dao.deleteById(id);
    }
}
