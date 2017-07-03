package info.palamarchuk.api.cooking.service;

import info.palamarchuk.api.cooking.data.IngredientEntityData;
import info.palamarchuk.api.cooking.entity.Ingredient;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
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
public class IngredientServiceTest {

    @Autowired
    private IngredientService service;
    @Autowired
    private ServiceTestHelper<Ingredient> testingHelper;

    public class Verifier implements EntityDataVerifiable<Ingredient> {
        final private IngredientEntityData data;

        private Verifier(String name) {
            this.data = new IngredientEntityData().setName(name);
        }

        @Override
        public void verify(Ingredient ingredient) {
            assertThat(ingredient.getName(), is(data.getName()));
        }

        @Override
        public void fill(Ingredient ingredient) {
            ingredient.setName(data.getName());
        }
    }

    @Test
    public void shouldGetAll() throws Exception {
        Map<Integer, Verifier> expected = new HashMap<>();
        expected.put(1, new Verifier("onion"));
        expected.put(2, new Verifier("carrot"));
        expected.put(3, new Verifier("rosemary"));

        List<Ingredient> ingredients = service.getAll();
        assertThat(ingredients.size(), is(3));
        for (Ingredient ingredient : ingredients) {
            expected.get(ingredient.getId()).verify(ingredient);
        }
    }

    @Test
    @Transactional
    public void shouldFindById() throws Exception {
        Ingredient ingredient = service.getById(1L);
        new Verifier("onion").verify(ingredient);
        assertThat(ingredient.getId(), is(1));
        assertThat(ingredient.getTranslations().size(), is(2));

        ingredient = service.getById(2L);
        new Verifier("carrot").verify(ingredient);
        assertThat(ingredient.getId(), is(2));
        assertThat(ingredient.getTranslations().size(), is(2));
    }

    @Test
    public void shouldAdd() throws Exception {
        Ingredient ingredient = new Ingredient();
        Verifier verifier = new Verifier("Rosemary");
        verifier.fill(ingredient);

        testingHelper.assertAdding(service, ingredient, verifier);
        assertThat(ingredient.getId(), notNullValue()); // id is set in the the original object
        assertThat(ingredient.getTranslations(), nullValue());
    }

    @Test
    public void shouldUpdate() throws Exception {
        Ingredient ingredient = service.getById(1L);
        testingHelper.assertUpdating(service, ingredient, new Verifier("red onion"));
    }

    @Test
    public void shouldDelete() throws Exception {
        testingHelper.assertDeleting(service, 1L);
    }
}
