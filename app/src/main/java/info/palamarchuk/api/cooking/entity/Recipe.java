package info.palamarchuk.api.cooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name="recipe")
public class Recipe implements IdNumerableEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="cook_time")
    private Time cookTime;

    @Column(name="precook_time")
    private Time precookTime;

    @Size(max=160, message="too long title")
    private String title;

    @Lob
    @Size(max=21844, message="too long name")
    private String annotation;

    @Lob
    @Size(max=21844, message="too long method")
    private String method;

    @Column(name="language_id", nullable = false)
    private Short languageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Language language;

    @OneToMany(mappedBy = "recipe", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecipeIngredient> ingredients;

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    @JsonIgnore
    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Time getCookTime() {
        return cookTime;
    }

    public void setCookTime(Time cookTime) {
        this.cookTime = cookTime;
    }

    public Time getPrecookTime() {
        return precookTime;
    }

    public void setPrecookTime(Time precookTime) {
        this.precookTime = precookTime;
    }

    public Short getLanguageId() {
        return languageId;
    }

    public void setLanguageId(short languageId) {
        this.languageId = languageId;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
