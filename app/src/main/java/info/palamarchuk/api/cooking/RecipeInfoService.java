package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.dao.RecipeInfoDao;
import info.palamarchuk.api.cooking.entity.RecipeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecipeInfoService {

    RecipeInfoDao dao;

    @Autowired
    public RecipeInfoService(RecipeInfoDao dao) {
        this.dao = dao;
        this.dao.setClazz(RecipeInfo.class);
    }

    public List<RecipeInfo> getAllByRecipeId(long recipeId) {
        return dao.findByRecipeId(recipeId);
    }

    public List<RecipeInfo> getAll() {
        return dao.findAll();
    }

    public RecipeInfo getById(long id) {
        return dao.findOne(id);
    }

    public RecipeInfo getByRecipeIdAndLangId(long recipeId, short languageId) {
        return dao.findByRecipeIdAndLanguageId(recipeId, languageId);
    }

    public RecipeInfo add(RecipeInfo recipeInfo) {
        dao.create(recipeInfo);
        return recipeInfo;
    }

    public void update(RecipeInfo recipeInfo) {
        dao.update(recipeInfo);
    }


    public void deleteById(long id) {
        dao.deleteById(id);
    }
}
