package info.palamarchuk.api.cooking.service;

import info.palamarchuk.api.cooking.entity.IngredientTranslation;
import info.palamarchuk.api.cooking.helper.EntityDataVerifiable;
import info.palamarchuk.api.cooking.helper.ServiceTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "classpath:/db/language/before.sql"),
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "classpath:/db/ingredient/before.sql"),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = "classpath:/db/ingredient/after.sql"),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = "classpath:/db/language/after.sql")
})
public class IngredientTranslationTest {

    @Autowired
    private IngredientTranslationService service;
    @Autowired
    ServiceTestHelper<IngredientTranslation> testingHelper;

    public class IngredientTranslationData implements EntityDataVerifiable<IngredientTranslation> {
        int ingredientId;
        short languageId;
        String name;
        String nameExtra;

        @Override
        public void verify(IngredientTranslation translation) {
            assertThat(translation.getIngredientId(), is(ingredientId));
            assertThat(translation.getLanguageId(), is(languageId));
            assertThat(translation.getName(), is(name));
            assertThat(translation.getNameExtra(), is(nameExtra));
        }

        @Override
        public void fill(IngredientTranslation translation) {
            translation.setIngredientId(ingredientId);
            translation.setLanguageId(languageId);
            translation.setName(name);
            translation.setNameExtra(nameExtra);
        }
    }

    @Test
    public void shouldFindById() throws Exception {
        IngredientTranslationData data = new IngredientTranslationData();
        data.name = "onion";
        data.nameExtra = "organic range";
        data.ingredientId = 1;
        data.languageId = 2;

        IngredientTranslation translation = service.getById(1);
        data.verify(translation);
        assertThat(translation.getId(), is(1));

        data = new IngredientTranslationData();
        data.name = "die Zwiebel";
        data.nameExtra = "bio";
        data.ingredientId = 1;
        data.languageId = 3;

        translation = service.getById(2);
        data.verify(translation);
        assertThat(translation.getId(), is(2));
    }

    @Test
    public void shouldAdd() throws Exception {
        IngredientTranslationData data = new IngredientTranslationData();
        data.languageId = 3;
        data.ingredientId = 3;
        data.name = "der Rosmarin";

        IngredientTranslation translation = new IngredientTranslation();
        data.fill(translation);

        testingHelper.assertAdding(service, translation, data);
        assertThat(translation.getId(), is(5)); // id is updated in the the original object
    }

    @Test
    public void shouldUpdate() throws Exception {
        IngredientTranslationData data = new IngredientTranslationData();
        data.ingredientId = 2; // ingredientId is old, it cannot be updated in this entity;
        data.languageId = 4; // language is being changed
        data.name = "Romarin";

        IngredientTranslation translation = service.getById(3L);

        testingHelper.assertUpdating(service, translation, data);
    }

    @Test
    public void shouldDelete() throws Exception {
        testingHelper.assertDeleting(service, 1L);
    }
}
