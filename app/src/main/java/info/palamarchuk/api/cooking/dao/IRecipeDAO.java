package info.palamarchuk.api.cooking.dao;

import info.palamarchuk.api.cooking.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecipeDAO extends JpaRepository<Recipe, Integer> {
    Recipe findById(int id);
}
