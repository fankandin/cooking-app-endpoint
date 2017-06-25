package info.palamarchuk.api.cooking.service;

import info.palamarchuk.api.cooking.dao.GenericDao;
import info.palamarchuk.api.cooking.entity.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IngredientService implements ServiceDao<Ingredient> {

    GenericDao<Ingredient> dao;

    @Autowired
    public IngredientService(GenericDao<Ingredient> dao) {
        this.dao = dao;
        this.dao.setClazz(Ingredient.class);
    }

    public List<Ingredient> getAll() {
        return dao.findAll();
    }

    public Ingredient getById(long id) {
        return dao.findOne((int)id);
    }

    public Ingredient add(Ingredient ingredient) {
        dao.create(ingredient);
        return ingredient;
    }

    public void update(Ingredient ingredient) {
        dao.update(ingredient);
    }


    public void deleteById(long id) {
        dao.deleteById((int)id);
    }
}
