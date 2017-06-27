package info.palamarchuk.api.cooking.data;

import info.palamarchuk.api.cooking.entity.Ingredient;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class IngredientEntityData {
    protected Integer id;
    protected String name;

    public Ingredient makeEntity() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        ingredient.setName(name);
        return ingredient;
    }
}
