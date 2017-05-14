package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Recipe;
import org.junit.Ignore;
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
    RecipeService service;

    @Test
    public void shouldFindById() throws Exception {
        Recipe recipe = service.findById(1);
        assertThat(recipe.getId(), equalTo(1));
        assertThat(recipe.getTitle(), equalTo("Plov Tashkent style"));

        recipe = service.findById(2);
        assertThat(recipe.getId(), equalTo(2));
        assertThat(recipe.getTitle(), equalTo("Greek salad"));
    }

    @Test
    public void shouldAdd() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setTitle("Chilli con carne");
        recipe.setCookTime(Time.valueOf("03:50:00"));
        recipe.setLanguage("english");
        service.add(recipe);

        assertThat(recipe.getId(), is(3)); // id is updated in the the original object

        Recipe savedRecipe = service.findById(recipe.getId());
        assertThat(savedRecipe.getTitle(), is("Chilli con carne"));
        assertThat(savedRecipe.getCookTime().toString(), is("03:50:00"));
        assertThat(savedRecipe.getLanguage(), is("english"));
    }

    @Test
    public void shouldUpdate() throws Exception {
        String newTitle = "Plov Fergana style";
        String newTime = "03:00:00";

        Recipe recipe = service.findById(1);
        recipe.setTitle(newTitle);
        recipe.setCookTime(Time.valueOf(newTime)); // not set for the record by default
        service.update(recipe);

        assertThat(recipe.getTitle(), is(newTitle));
        assertThat(recipe.getCookTime().toString(), is(newTime));

        Recipe savedRecipe = service.findById(recipe.getId()); // ensure it is saved in DB, not only in the instance
        assertThat(savedRecipe.getTitle(), is(newTitle));
        assertThat(savedRecipe.getCookTime().toString(), is(newTime));
    }

    @Test
    public void shouldDelete() throws Exception {
        Recipe recipe = service.findById(1); // ensure recipe is there
        assertThat(recipe.getId(), is(1));
        service.deleteById(1);

        assertThat(service.findById(1), is(nullValue()));
    }
}