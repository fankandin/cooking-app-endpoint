package info.palamarchuk.api.cooking.service;

import info.palamarchuk.api.cooking.entity.Language;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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

    @Test
    public void shouldGetAll() throws Exception {
        Map<Short, String> expected = new HashMap<>();
        expected.put((short)1, "ru-ru");
        expected.put((short)2, "en-uk");
        expected.put((short)3, "de-de");
        expected.put((short)4, "fr-fr");

        List<Language> languages = service.getAll();
        assertThat(languages.size(), is(4));
        for (Language language : languages) {
            assertThat(language.getCode(), is(expected.get(language.getId())));
        }
    }

    @Test
    public void shouldGetById() throws Exception {
        Language language = service.getById(2L);
        assertThat(language.getId(), is((short)2));
        assertThat(language.getCode(), is("en-uk"));

        language = service.getById(4L);
        assertThat(language.getId(), is((short)4));
        assertThat(language.getCode(), is("fr-fr"));
    }
}
