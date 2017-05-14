package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.dao.IGenericDAO;
import info.palamarchuk.api.cooking.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 *
 */
@Component
@Transactional
public class RecipeService {

    IGenericDAO<Recipe> dao;

    @Autowired
    public void setDao(IGenericDAO<Recipe> dao) {
        this.dao = dao;
        this.dao.setClazz(Recipe.class);
    }

    public Recipe findById(int id) {
        Recipe recipe = dao.findOne(id);
        return recipe;
    }

    public void add(Recipe recipe) {
        dao.create(recipe);
    }

    public void update(Recipe recipe) {
        dao.update(recipe);
    }


    public void deleteById(int id) {
        dao.deleteById(id);
    }
}
