package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.dao.GenericDao;
import info.palamarchuk.api.cooking.entity.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IngredientService {

    GenericDao<Ingredient> dao;

    @Autowired
    public IngredientService(GenericDao<Ingredient> dao) {
        this.dao = dao;
        this.dao.setClazz(Ingredient.class);
    }

    public List<Ingredient> getAll() {
        return dao.findAll();
    }

    public Ingredient getById(int id) {
        return dao.findOne(id);
    }

    public Ingredient add(Ingredient ingredient) {
        dao.create(ingredient);
        return ingredient;
    }

    public void update(Ingredient ingredient) {
        dao.update(ingredient);
    }


    public void deleteById(int id) {
        dao.deleteById(id);
    }
}
