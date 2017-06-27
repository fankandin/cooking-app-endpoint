package info.palamarchuk.api.cooking.data;

import info.palamarchuk.api.cooking.entity.IngredientTranslation;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class IngredientTranslationEntityData {
    protected Integer id;
    protected Integer ingredientId;
    protected Short languageId;
    protected String name;
    protected String nameExtra;

    public IngredientTranslation makeEntity() {
        IngredientTranslation entity = new IngredientTranslation();
        entity.setId(id);
        entity.setIngredientId(ingredientId);
        entity.setLanguageId(languageId);
        entity.setName(name);
        entity.setNameExtra(nameExtra);
        return entity;
    }
}
