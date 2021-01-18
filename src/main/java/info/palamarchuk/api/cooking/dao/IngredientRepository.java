package info.palamarchuk.api.cooking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import info.palamarchuk.api.cooking.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
