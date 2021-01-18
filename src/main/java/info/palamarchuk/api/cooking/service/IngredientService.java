package info.palamarchuk.api.cooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.palamarchuk.api.cooking.dao.IngredientRepository;
import info.palamarchuk.api.cooking.entity.Ingredient;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class IngredientService implements ServiceDao<Ingredient> {

    private final IngredientRepository repository;

    public List<Ingredient> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Ingredient> getById(final long id) {
        return repository.findById(Math.toIntExact(id));
    }

    @Override
    public Ingredient add(final Ingredient ingredient) {
        repository.save(ingredient);
        return ingredient;
    }

    @Override
    public void update(final Ingredient ingredient) {
        repository.save(ingredient);
    }

    @Override
    public boolean existsById(final long id) {
        return repository.existsById(Math.toIntExact(id));
    }

    @Override
    public void deleteById(final long id) {
        repository.deleteById(Math.toIntExact(id));
    }
}
