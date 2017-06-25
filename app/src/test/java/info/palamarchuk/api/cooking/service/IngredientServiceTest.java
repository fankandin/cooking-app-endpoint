package info.palamarchuk.api.cooking.service;

import info.palamarchuk.api.cooking.entity.Ingredient;
import info.palamarchuk.api.cooking.entity.Recipe;
import info.palamarchuk.api.cooking.helper.EntityDataVerifiable;
import info.palamarchuk.api.cooking.helper.ServiceTestHelper;
import info.palamarchuk.api.cooking.service.IngredientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "classpath:/db/ingredient/before.sql"),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = "classpath:/db/ingredient/after.sql")
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
    public void shouldFindById() throws Exception {
        Ingredient ingredient = service.getById(1L);
        assertThat(ingredient.getId(), is(1));
        assertThat(ingredient.getName(), is("onion"));

        ingredient = service.getById(2L);
        assertThat(ingredient.getId(), is(2));
        assertThat(ingredient.getName(), is("carrot"));
    }

    @Test
    public void shouldAdd() throws Exception {
        IngredientData data = new IngredientData();
        data.name = "Rosemary";

        Ingredient ingredient = new Ingredient();
        data.fill(ingredient);

        testingHelper.assertAdding(service, ingredient, data);
        assertThat(ingredient.getId(), is(4)); // id is updated in the the original object
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
