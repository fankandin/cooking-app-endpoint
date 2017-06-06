package info.palamarchuk.api.cooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="ingredient")
public class Ingredient implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "ingredient", cascade=CascadeType.ALL)
    @JsonIgnore
    private Set<RecipeIngredient> recipes = new HashSet<>();

    @OneToMany(mappedBy = "ingredient", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IngredientInfo> infos;

    public int getId() {
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
    public List<IngredientInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<IngredientInfo> infos) {
        this.infos = infos;
    }
}
