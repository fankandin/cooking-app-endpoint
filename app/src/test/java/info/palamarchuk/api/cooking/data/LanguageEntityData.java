package info.palamarchuk.api.cooking.data;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class LanguageEntityData {
    private Short id;
    protected String code;
}
