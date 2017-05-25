package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.dao.IGenericDAO;
import info.palamarchuk.api.cooking.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Service
@Transactional
public class RecipeService {

    IGenericDAO<Recipe> dao;

    @Autowired
    public void setDao(IGenericDAO<Recipe> dao) {
        this.dao = dao;
        this.dao.setClazz(Recipe.class);
    }

    public List<Recipe> getAll() {
        return dao.findAll();
    }

    public Recipe getById(long id) {
        Recipe recipe = dao.findOne(id);
        if (recipe != null) {
            recipe.getIngredients().size(); // initialize lazy loading of the ingredients
        }
        return recipe;
    }

    public Recipe add(Recipe recipe) {
        dao.create(recipe);
        return recipe;
    }

    public void update(Recipe recipe) {
        dao.update(recipe);
    }


    public void deleteById(long id) {
        dao.deleteById(id);
    }
}
