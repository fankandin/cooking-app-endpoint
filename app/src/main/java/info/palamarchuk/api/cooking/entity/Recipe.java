package info.palamarchuk.api.cooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="recipe")
public class Recipe implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(name="cook_time")
    private Time cookTime;

    @Column(name="precook_time")
    private Time precookTime;

    @OneToMany(mappedBy = "recipe", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecipeIngredient> ingredients;

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    @JsonIgnore
    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    @OneToMany(mappedBy = "recipe", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecipeInfo> infos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @JsonIgnore
    public List<RecipeInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<RecipeInfo> infos) {
        this.infos = infos;
    }
}
