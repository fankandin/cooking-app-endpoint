package info.palamarchuk.api.cooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "recipe_ingredient_info")
public class RecipeIngredientInfo implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="recipe_ingredient_id", nullable = false)
    private Long recipeIngredientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_ingredient_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private RecipeIngredient recipeIngredient;

    @Column(name="language_id", nullable = false)
    private Short languageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Language language;

    private String preparation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecipeIngredientId() {
        return recipeIngredientId;
    }

    public void setRecipeIngredientId(Long recipeIngredientId) {
        this.recipeIngredientId = recipeIngredientId;
    }

    public RecipeIngredient getRecipeIngredient() {
        return recipeIngredient;
    }

    public void setRecipeIngredient(RecipeIngredient recipeIngredient) {
        this.recipeIngredient = recipeIngredient;
    }

    public Short getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Short languageId) {
        this.languageId = languageId;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }
}
