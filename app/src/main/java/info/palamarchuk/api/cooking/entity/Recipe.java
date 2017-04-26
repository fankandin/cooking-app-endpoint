package info.palamarchuk.api.cooking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Time;
import javax.persistence.Id;

@Entity
public class Recipe implements Serializable {

    @Id
    @Column(name="id")
    private int id;

    private String title;

    private String type;

    @Column(name="cook_time")
    private Time cookTime;

    @Column(name="precook_time")
    private Time precookTime;

    private String annotation;

    private String howto;

    private String language;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getHowto() {
        return howto;
    }

    public void setHowto(String howto) {
        this.howto = howto;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (id != recipe.id) return false;
        if (title != null ? !title.equals(recipe.title) : recipe.title != null) return false;
        if (type != null ? !type.equals(recipe.type) : recipe.type != null) return false;
        if (cookTime != null ? !cookTime.equals(recipe.cookTime) : recipe.cookTime != null) return false;
        if (precookTime != null ? !precookTime.equals(recipe.precookTime) : recipe.precookTime != null) return false;
        if (annotation != null ? !annotation.equals(recipe.annotation) : recipe.annotation != null) return false;
        if (howto != null ? !howto.equals(recipe.howto) : recipe.howto != null) return false;
        if (language != null ? !language.equals(recipe.language) : recipe.language != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (cookTime != null ? cookTime.hashCode() : 0);
        result = 31 * result + (precookTime != null ? precookTime.hashCode() : 0);
        result = 31 * result + (annotation != null ? annotation.hashCode() : 0);
        result = 31 * result + (howto != null ? howto.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }
}
