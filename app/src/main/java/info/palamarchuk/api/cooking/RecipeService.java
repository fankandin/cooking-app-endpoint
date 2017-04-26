package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.dao.IGenericDAO;
import info.palamarchuk.api.cooking.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 *
 */
@Component
public class RecipeService {

    IGenericDAO<Recipe> dao;

    @Autowired
    public void setDao(IGenericDAO<Recipe> dao) {
        this.dao = dao;
        this.dao.setClazz(Recipe.class);
    }

    /**
     *
     * @param id
     * @return
     */
    public List<Recipe> findById(int id) {
        Recipe recipe = dao.findOne(id);
        return Collections.singletonList(recipe);
    }
}
