package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Recipe;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
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

    @Autowired
    EntityManagerFactory em;

    @Test
    public void shouldFindById() throws Exception {
        Recipe recipe = service.getById(1);
        assertThat(recipe.getId(), is(1L));
        assertThat(recipe.getName(), is("Plov Tashkent style"));
        assertThat(recipe.getIngredients().size(), is(3));

        recipe = service.getById(2);
        assertThat(recipe.getId(), is(2L));
        assertThat(recipe.getName(), is("Greek salad"));
    }

    @Test
    public void shouldAdd() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setName("Chilli con carne");
        recipe.setCookTime(Time.valueOf("03:50:00"));
        service.add(recipe);

        assertThat(recipe.getId(), is(3L)); // id is updated in the the original object

        Recipe savedRecipe = service.getById(recipe.getId());
        assertThat(savedRecipe.getName(), is("Chilli con carne"));
        assertThat(savedRecipe.getCookTime().toString(), is("03:50:00"));
    }

    @Test
    public void shouldUpdate() throws Exception {
        String newTitle = "Plov Fergana style";
        String newTime = "03:00:00";

        Recipe recipe = service.getById(1L);
        recipe.setName(newTitle);
        recipe.setCookTime(Time.valueOf(newTime)); // not set for the record by default
        service.update(recipe);

        assertThat(recipe.getName(), is(newTitle));
        assertThat(recipe.getCookTime().toString(), is(newTime));

        Recipe savedRecipe = service.getById(recipe.getId()); // ensure it is saved in DB, not only in the instance
        assertThat(savedRecipe.getName(), is(newTitle));
        assertThat(savedRecipe.getCookTime().toString(), is(newTime));
    }

    @Test
    public void shouldDelete() throws Exception {
        Recipe recipe = service.getById(1L); // ensure recipe is there
        assertThat(recipe.getId(), is(1L));
        service.deleteById(1L);

        assertThat(service.getById(1L), is(nullValue()));
    }
}