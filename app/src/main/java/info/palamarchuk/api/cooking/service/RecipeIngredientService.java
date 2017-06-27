package info.palamarchuk.api.cooking.service;

import info.palamarchuk.api.cooking.dao.RecipeIngredientDao;
import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecipeIngredientService implements ServiceDao<RecipeIngredient> {

    RecipeIngredientDao dao;

    @Autowired
    public void RecipeService(RecipeIngredientDao dao) {
        this.dao = dao;
        this.dao.setClazz(RecipeIngredient.class);
    }

    public List<RecipeIngredient> getAllByRecipeId(long recipeId) {
        List<RecipeIngredient> recipeIngredients = dao.findByRecipeId(recipeId);
        return recipeIngredients;
    }

    public RecipeIngredient getById(long id) {
        return dao.findOne(id);
    }

    public RecipeIngredient getByRecipeIdAndIngredientId (long recipeId, long ingredientId) {
        return dao.findByRecipeIdAndIngredientId(recipeId, Math.toIntExact(ingredientId));
    }

    public RecipeIngredient add(RecipeIngredient recipeIngredient) {
        dao.create(recipeIngredient);
        return recipeIngredient;
    }

    public void update(RecipeIngredient recipeIngredient) {
        dao.update(recipeIngredient);
    }


    public void deleteById(long id) {
        dao.deleteById(id);
    }

}
