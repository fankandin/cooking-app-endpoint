package info.palamarchuk.api.cooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="ingredient")
public class Ingredient implements IdNumerableEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Size(max=80, message="too long name")
    private String name;

    @OneToMany(mappedBy = "ingredient", cascade=CascadeType.ALL)
    @JsonIgnore
    private Set<RecipeIngredient> recipes = new HashSet<>();

    @OneToMany(mappedBy = "ingredient", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IngredientTranslation> translations;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<IngredientTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<IngredientTranslation> translations) {
        this.translations = translations;
    }
}
