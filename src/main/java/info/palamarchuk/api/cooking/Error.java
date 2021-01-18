package info.palamarchuk.api.cooking;

public class Error {
    private String code;
    private String field;

    public Error(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
