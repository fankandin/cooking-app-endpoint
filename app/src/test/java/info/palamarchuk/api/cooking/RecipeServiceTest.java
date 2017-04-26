package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Recipe;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "classpath:/recipe/before.sql"),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = "classpath:/recipe/after.sql")
})
@Ignore
public class RecipeServiceTest {

    @Autowired
    RecipeService service;

    @Test
    public void shouldFindById() throws Exception {
        List<Recipe> recipe = service.findById(1);

        assertThat(recipe, hasSize(1));
        assertThat(recipe.get(0).getId(), equalTo(1));
        assertThat(recipe.get(0).getTitle(), equalTo("Плов"));
    }

}