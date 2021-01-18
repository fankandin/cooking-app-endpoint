package info.palamarchuk.api.cooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.palamarchuk.api.cooking.dao.RecipeRepository;
import info.palamarchuk.api.cooking.entity.Recipe;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService implements ServiceDao<Recipe> {

    private final RecipeRepository repository;

    public List<Recipe> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Recipe> getById(long id) {
        var recipe = repository.findById(id);
        if (!recipe.isPresent()) {
            recipe.get().getIngredients().size(); // initialize lazy loading of the ingredients
        }
        return recipe;
    }

    @Override
    public Recipe add(Recipe recipe) {
        repository.save(recipe);
        return recipe;
    }

    @Override
    public void update(Recipe recipe) {
        repository.save(recipe);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(final long id) {
        return repository.existsById(id);
    }
}
