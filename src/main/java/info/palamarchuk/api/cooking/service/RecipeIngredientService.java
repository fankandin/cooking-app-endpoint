package info.palamarchuk.api.cooking.service;

import info.palamarchuk.api.cooking.dao.RecipeIngredientRepository;
import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeIngredientService implements ServiceDao<RecipeIngredient> {

    private final RecipeIngredientRepository repository;

    public List<RecipeIngredient> getAllByRecipeId(long recipeId) {
        List<RecipeIngredient> recipeIngredients = repository.findByRecipeId(recipeId);
        return recipeIngredients;
    }

    @Override
    public Optional<RecipeIngredient> getById(long id) {
        return repository.findById(id);
    }

    public RecipeIngredient getByRecipeIdAndIngredientId (long recipeId, long ingredientId) {
        return repository.findByRecipeIdAndIngredientId(recipeId, Math.toIntExact(ingredientId));
    }

    @Override
    public RecipeIngredient add(RecipeIngredient recipeIngredient) {
        repository.save(recipeIngredient);
        return recipeIngredient;
    }

    @Override
    public void update(RecipeIngredient recipeIngredient) {
        repository.save(recipeIngredient);
    }

    @Override
    public boolean existsById(final long id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

}
