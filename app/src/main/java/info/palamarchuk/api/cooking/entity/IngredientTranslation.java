package info.palamarchuk.api.cooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name="ingredient_translation")
public class IngredientTranslation implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="ingredient_id", nullable = false)
    private Integer ingredientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Ingredient ingredient;

    @Column(name="language_id", nullable = false)
    private Short languageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Language language;

    @Size(max=80, message="too long name")
    private String name;

    @Column(name = "name_extra")
    @Size(max=80, message="too long nameExtra")
    private String nameExtra;

    @Size(max=255, message="too long note")
    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameExtra() {
        return nameExtra;
    }

    public void setNameExtra(String nameExtra) {
        this.nameExtra = nameExtra;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
