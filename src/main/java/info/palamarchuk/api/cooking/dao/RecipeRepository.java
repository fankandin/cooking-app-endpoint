package info.palamarchuk.api.cooking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import info.palamarchuk.api.cooking.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
