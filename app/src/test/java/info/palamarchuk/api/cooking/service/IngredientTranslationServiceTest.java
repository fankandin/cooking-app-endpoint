package info.palamarchuk.api.cooking.service;

import info.palamarchuk.api.cooking.data.IngredientTranslationEntityData;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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
public class IngredientTranslationServiceTest {

    @Autowired
    private IngredientTranslationService service;
    @Autowired
    private ServiceTestHelper<IngredientTranslation> testingHelper;

    public class Verifier implements EntityDataVerifiable<IngredientTranslation> {
        final private IngredientTranslationEntityData data;

        private Verifier(IngredientTranslationEntityData data) {
            this.data = data;
        }

        @Override
        public void verify(IngredientTranslation translation) {
            assertThat(translation.getIngredientId(), is(data.getIngredientId()));
            assertThat(translation.getLanguageId(), is(data.getLanguageId()));
            assertThat(translation.getName(), is(data.getName()));
            assertThat(translation.getNameExtra(), is(data.getNameExtra()));
        }

        @Override
        public void fill(IngredientTranslation translation) {
            translation.setIngredientId(data.getIngredientId());
            translation.setLanguageId(data.getLanguageId());
            translation.setName(data.getName());
            translation.setNameExtra(data.getNameExtra());
        }
    }

    private IngredientTranslationEntityData[] storedTranslations = {
        new IngredientTranslationEntityData().setId(3)
            .setIngredientId(2)
            .setLanguageId((short)4)
            .setName("carotte")
            .setNameExtra("carotte jaune")
            .setNote(""),
        new IngredientTranslationEntityData().setId(4)
            .setIngredientId(2)
            .setLanguageId((short)2)
            .setName("carrot")
            .setNameExtra("yellow carrot")
            .setNote("Yellow carrot is common in Uzbekistan, but hardly can be found in Europe. Although it brings in special notes you may replace it with red carrot.")
    };

    @Test
    public void shouldFindById() throws Exception {
        IngredientTranslation translation = service.getById(storedTranslations[0].getId());
        new Verifier(storedTranslations[0]).verify(translation);
        assertThat(translation.getId(), is(storedTranslations[0].getId()));

        translation = service.getById(storedTranslations[1].getId());
        new Verifier(storedTranslations[1]).verify(translation);
        assertThat(translation.getId(), is(storedTranslations[1].getId()));
    }

    @Test
    public void shouldGetAllByIngredientId() {
        Map<Integer, Verifier> expected = new HashMap<>();
        expected.put(storedTranslations[0].getId(), new Verifier(storedTranslations[0]));
        expected.put(storedTranslations[1].getId(), new Verifier(storedTranslations[1]));

        List<IngredientTranslation> translations = service.getAllByIngredientId(2);
        assertThat(translations.size(), is(2));
        for (IngredientTranslation translation : translations) {
            expected.get(translation.getId()).verify(translation);
        }
    }

    @Test
    public void shouldGetByIngredientIdAndLangId() {
        IngredientTranslation translation = service.getByIngredientIdAndLangId(
            storedTranslations[1].getIngredientId(), storedTranslations[1].getLanguageId()
        );
        new Verifier(storedTranslations[1]).verify(translation);
    }

    @Test
    public void shouldAdd() throws Exception {
        Verifier verifier = new Verifier(new IngredientTranslationEntityData()
            .setLanguageId((short)3)
            .setIngredientId(3)
            .setName("der Rosmarin")
        );

        IngredientTranslation translation = new IngredientTranslation();
        verifier.fill(translation);

        testingHelper.assertAdding(service, translation, verifier);
        assertThat(translation.getId(), notNullValue()); // id is set in the the original object
    }

    @Test
    public void shouldUpdate() throws Exception {
        Verifier verifier = new Verifier(new IngredientTranslationEntityData()
            .setLanguageId((short)4) // language is being changed
            .setIngredientId(2) // ingredientId is old, it cannot be updated in this entity;
            .setName("Romarin")
        );

        IngredientTranslation translation = service.getById(3L);

        testingHelper.assertUpdating(service, translation, verifier);
    }

    @Test
    public void shouldDelete() throws Exception {
        testingHelper.assertDeleting(service, 1L);
    }
}
