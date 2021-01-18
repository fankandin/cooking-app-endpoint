package info.palamarchuk.api.cooking.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import info.palamarchuk.api.cooking.data.LanguageEntityData;
import info.palamarchuk.api.cooking.entity.Language;
import info.palamarchuk.api.cooking.helper.EntityDataVerifiable;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "classpath:/db/language/before.sql"),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = "classpath:/db/language/after.sql")
})
public class LanguageServiceTest {

    @Autowired
    private LanguageService service;

    private class Verifier implements EntityDataVerifiable<Language> {
        final private LanguageEntityData data;

        private Verifier(LanguageEntityData data) {
            this.data = data;
        }

        @Override
        public void verify(Language language) {
            assertThat(language.getCode(), is(this.data.getCode()));
        }

        @Override
        public void fill(Language language) {
            language.setCode(data.getCode());
        }
    }

    LanguageEntityData[] storedLanguages = {
        new LanguageEntityData().setId((short)1).setCode("ru-RU"), // id=1
        new LanguageEntityData().setId((short)2).setCode("en-GB"), // id=2
        new LanguageEntityData().setId((short)3).setCode("de-DE"), // id=3
        new LanguageEntityData().setId((short)4).setCode("fr-FR"), // id=4
    };

    @Test
    public void shouldGetAll() throws Exception {
        Map<Short, LanguageEntityData> expected = new HashMap<>();
        for (int i = 0; i<storedLanguages.length; i++) {
            expected.put(storedLanguages[i].getId(), storedLanguages[i]);
        }

        List<Language> languages = service.getAll();
        assertThat(languages.size(), is(4));
        for (Language language : languages) {
            new Verifier(expected.get(language.getId())).verify(language);
        }
    }

    @Test
    public void shouldGetById() throws Exception {
        new Verifier(storedLanguages[1]).verify(service.getById(storedLanguages[1].getId()).get());
        new Verifier(storedLanguages[3]).verify(service.getById(storedLanguages[3].getId()).get());
    }
}
