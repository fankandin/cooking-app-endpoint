package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Ingredient;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "classpath:/ingredient/before.sql"),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = "classpath:/ingredient/after.sql")
})

public class IngredientServiceTest {

    @Autowired
    private IngredientService service;

    @Test
    public void shouldFindById() throws Exception {
        Ingredient ingredient = service.getById(1);
        assertThat(ingredient.getId(), is(1));
        assertThat(ingredient.getName(), is("onion"));

        ingredient = service.getById(2);
        assertThat(ingredient.getId(), is(2));
        assertThat(ingredient.getName(), is("carrot"));
    }

    @Test
    public void shouldAdd() throws Exception {
        Ingredient ingredient = new Ingredient();
        String name = "Rosemary";
        ingredient.setName(name);
        service.add(ingredient);

        assertThat(ingredient.getId(), is(4)); // id is updated in the the original object

        Ingredient savedIngredient = service.getById(ingredient.getId());
        assertThat(savedIngredient.getName(), is(name));
    }

    @Test
    public void shouldUpdate() throws Exception {
        String nameUpd = "red onion";

        Ingredient ingredient = service.getById(1);
        ingredient.setName(nameUpd);
        service.update(ingredient);
        assertThat(ingredient.getName(), is(nameUpd));

        Ingredient savedIngredient = service.getById(ingredient.getId()); // ensure it is saved in DB, not only in the instance
        assertThat(savedIngredient.getName(), is(nameUpd));
    }

    @Test
    public void shouldDelete() throws Exception {
        Ingredient ingredient = service.getById(1); // ensure ingredient is there
        assertThat(ingredient.getId(), is(1));
        service.deleteById(1);

        assertThat(service.getById(1), is(nullValue()));
    }

}
