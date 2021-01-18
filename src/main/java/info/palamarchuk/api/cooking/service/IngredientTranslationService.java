package info.palamarchuk.api.cooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.palamarchuk.api.cooking.dao.IngredientTranslationRepository;
import info.palamarchuk.api.cooking.entity.IngredientTranslation;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class IngredientTranslationService implements ServiceDao<IngredientTranslation> {

    private final IngredientTranslationRepository repository;

    public List<IngredientTranslation> getAll() {
        return repository.findAll();
    }

    public List<IngredientTranslation> getAllByIngredientId(long ingredientId) {
        return repository.findByIngredientId(Math.toIntExact(ingredientId));
    }

    @Override
    public Optional<IngredientTranslation> getById(final long id) {
        return repository.findById(id);
    }

    public IngredientTranslation getByIngredientIdAndLangId(final long ingredientId, final long languageId) {
        return repository.findByIngredientIdAndLanguageId(Math.toIntExact(ingredientId), (short)languageId);
    }

    @Override
    public IngredientTranslation add(final IngredientTranslation recipeInfo) {
        repository.save(recipeInfo);
        return recipeInfo;
    }

    @Override
    public void update(final IngredientTranslation recipeInfo) {
        repository.save(recipeInfo);
    }

    @Override
    public boolean existsById(final long id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(final long id) {
        repository.deleteById(id);
    }

}
