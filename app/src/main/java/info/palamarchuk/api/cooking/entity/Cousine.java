package info.palamarchuk.api.cooking.entity;

public class Cousine {
    private int id;
    private String nameRu;
    private String nameEn;
    private String nameDe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameDe() {
        return nameDe;
    }

    public void setNameDe(String nameDe) {
        this.nameDe = nameDe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cousine cousine = (Cousine) o;

        if (id != cousine.id) return false;
        if (nameRu != null ? !nameRu.equals(cousine.nameRu) : cousine.nameRu != null) return false;
        if (nameEn != null ? !nameEn.equals(cousine.nameEn) : cousine.nameEn != null) return false;
        if (nameDe != null ? !nameDe.equals(cousine.nameDe) : cousine.nameDe != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nameRu != null ? nameRu.hashCode() : 0);
        result = 31 * result + (nameEn != null ? nameEn.hashCode() : 0);
        result = 31 * result + (nameDe != null ? nameDe.hashCode() : 0);
        return result;
    }
}
