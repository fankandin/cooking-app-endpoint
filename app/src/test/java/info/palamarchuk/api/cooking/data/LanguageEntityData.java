package info.palamarchuk.api.cooking.data;

import info.palamarchuk.api.cooking.entity.Language;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class LanguageEntityData implements EntityExportable {
    private Short id;
    protected String code;

    public Language makeEntity() {
        Language entity = new Language();
        entity.setId(id);
        entity.setCode(code);
        return entity;
    }
}
