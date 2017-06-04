package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Ingredient;
import info.palamarchuk.api.cooking.entity.Recipe;
import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "classpath:/recipe/before.sql"),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = "classpath:/recipe/after.sql")
})
public class RecipeServiceTest {

    @Autowired
    RecipeService serviceRecipe;

    @Autowired
    IngredientService serviceIngredient;

    @Autowired
    RecipeIngredientService serviceRecipeIngredient;

    //@Autowired
    //EntityManagerFactory em;

    @Test
    public void shouldFindById() throws Exception {
        Recipe recipe = serviceRecipe.getById(1);
        assertThat(recipe.getId(), is(1L));
        assertThat(recipe.getName(), is("Plov Tashkent style"));
        assertThat(recipe.getIngredients().size(), is(3));

        recipe = serviceRecipe.getById(2);
        assertThat(recipe.getId(), is(2L));
        assertThat(recipe.getName(), is("Greek salad"));
    }

    @Test
    public void shouldAdd() throws Exception {
        Recipe recipe = new Recipe();
        String name = "Chilli con carne";
        recipe.setName(name);
        String time = "03:50:00";
        recipe.setCookTime(Time.valueOf(time));
        serviceRecipe.add(recipe);

        assertThat(recipe.getId(), is(3L)); // id is updated in the the original object

        Recipe savedRecipe = serviceRecipe.getById(recipe.getId());
        assertThat(savedRecipe.getName(), is(name));
        assertThat(savedRecipe.getCookTime().toString(), is(time));
    }

    @Test
    public void shouldUpdate() throws Exception {
        String nameUpd = "Plov Fergana style";
        String timeUpd = "03:00:00";

        Recipe recipe = serviceRecipe.getById(1L);
        recipe.setName(nameUpd);
        recipe.setCookTime(Time.valueOf(timeUpd)); // not set for the record by default
        serviceRecipe.update(recipe);
        assertThat(recipe.getName(), is(nameUpd));
        assertThat(recipe.getCookTime().toString(), is(timeUpd));

        Recipe savedRecipe = serviceRecipe.getById(recipe.getId()); // ensure it is saved in DB, not only in the instance
        assertThat(savedRecipe.getName(), is(nameUpd));
        assertThat(savedRecipe.getCookTime().toString(), is(timeUpd));
    }

    @Test
    public void shouldDelete() throws Exception {
        Recipe recipe = serviceRecipe.getById(1L); // ensure recipe is there
        assertThat(recipe.getId(), is(1L));
        serviceRecipe.deleteById(1L);

        assertThat(serviceRecipe.getById(1L), is(nullValue()));
    }

    public void shouldAddIngredient() throws Exception {
        Ingredient ingredient = serviceIngredient.getById(4);
        RecipeIngredient recipeIngredient = new RecipeIngredient();

    }
}