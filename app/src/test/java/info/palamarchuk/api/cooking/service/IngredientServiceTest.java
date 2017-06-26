package info.palamarchuk.api.cooking.service;

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

import static org.hamcrest.Matchers.is;
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
    ServiceTestHelper<Ingredient> testingHelper;

    public class IngredientData implements EntityDataVerifiable<Ingredient> {
        String name;

        @Override
        public void verify(Ingredient ingredient) {
            assertThat(ingredient.getName(), is(name));
        }

        @Override
        public void fill(Ingredient ingredient) {
            ingredient.setName(name);
        }
    }

    @Test
    @Transactional
    public void shouldFindById() throws Exception {
        IngredientData data = new IngredientData();
        Ingredient ingredient = service.getById(1L);
        data.name = "onion";
        data.verify(ingredient);
        assertThat(ingredient.getId(), is(1));
        assertThat(ingredient.getTranslations().size(), is(2));

        data = new IngredientData();
        ingredient = service.getById(2L);
        data.name = "carrot";
        data.verify(ingredient);
        assertThat(ingredient.getId(), is(2));
        assertThat(ingredient.getTranslations().size(), is(2));
    }

    @Test
    public void shouldAdd() throws Exception {
        IngredientData data = new IngredientData();
        data.name = "Rosemary";

        Ingredient ingredient = new Ingredient();
        data.fill(ingredient);

        testingHelper.assertAdding(service, ingredient, data);
        assertThat(ingredient.getId(), is(4)); // id is updated in the the original object
        assertThat(ingredient.getTranslations(), nullValue());
    }

    @Test
    public void shouldUpdate() throws Exception {
        IngredientData data = new IngredientData();
        data.name = "red onion";

        Ingredient ingredient = service.getById(1L);

        testingHelper.assertUpdating(service, ingredient, data);
    }

    @Test
    public void shouldDelete() throws Exception {
        testingHelper.assertDeleting(service, 1L);
    }
}
