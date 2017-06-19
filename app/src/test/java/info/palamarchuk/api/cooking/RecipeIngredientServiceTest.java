package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "classpath:/db/recipe/before.sql"),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = "classpath:/db/recipe/after.sql")
})
public class RecipeIngredientServiceTest {

    @Autowired
    RecipeIngredientService service;

    @Test
    public void shouldFindById() throws Exception {
        RecipeIngredient recipeIngredient = service.getById(2L);
        assertThat(recipeIngredient.getId(), is(2L));
        assertThat(recipeIngredient.getRecipeId(), is(1L));
        assertThat(recipeIngredient.getRecipe().getId(), is(1L));
        assertThat(recipeIngredient.getIngredientId(), is(2));
        assertThat(recipeIngredient.getIngredient().getId(), is(2));
        assertThat(recipeIngredient.getAmount(), is(new BigDecimal("500.00")));
        assertThat(recipeIngredient.isAmountNetto(), is(true));
        assertThat(recipeIngredient.getMeasurement(), is("gram"));
    }


}
