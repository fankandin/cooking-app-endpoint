package info.palamarchuk.api.cooking.data;

import info.palamarchuk.api.cooking.entity.Recipe;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Time;

@Setter
@Getter
@Accessors(chain = true)
public class RecipeEntityData implements EntityExportable {
    protected Long id;

    String title;
    Time cookTime;
    Time precookTime;
    String annotation;
    String method;
    Short languageId;

    public RecipeEntityData setCookTime(String cookTime) {
        this.cookTime = Time.valueOf(cookTime);
        return this;
    }

    public RecipeEntityData setPrecookTime(String precookTime) {
        this.precookTime = Time.valueOf(precookTime);
        return this;
    }

    public Recipe makeEntity() {
        Recipe entity = new Recipe();
        entity.setId(id);
        entity.setTitle(title);
        entity.setCookTime(cookTime);
        entity.setPrecookTime(precookTime);
        entity.setAnnotation(annotation);
        entity.setMethod(method);
        entity.setLanguageId(languageId);
        return entity;
    }
}
