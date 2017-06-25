package info.palamarchuk.api.cooking.service;

import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import info.palamarchuk.api.cooking.helper.EntityDataVerifiable;
import info.palamarchuk.api.cooking.helper.ServiceTestHelper;
import info.palamarchuk.api.cooking.service.RecipeIngredientService;
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
    @Autowired
    ServiceTestHelper<RecipeIngredient> testingHelper;

    public class RecipeIngredientData implements EntityDataVerifiable<RecipeIngredient> {
        long recipeId;
        int ingredientId;
        BigDecimal amount;
        boolean isAmountNetto = false;
        String measurement;
        String preparation;

        @Override
        public void verify(RecipeIngredient recipeIngredient) {
            assertThat(recipeIngredient.getRecipeId(), is(recipeId));
            assertThat(recipeIngredient.getIngredientId(), is(ingredientId));
            assertThat(recipeIngredient.getAmount(), is(amount));
            assertThat(recipeIngredient.isAmountNetto(), is(isAmountNetto));
            assertThat(recipeIngredient.getMeasurement(), is(measurement));
            assertThat(recipeIngredient.getPreparation(), is(preparation));
        }

        @Override
        public void fill(RecipeIngredient recipeIngredient) {
            recipeIngredient.setRecipeId(recipeId);
            recipeIngredient.setIngredientId(ingredientId);
            recipeIngredient.setAmount(amount);
            recipeIngredient.setAmountNetto(isAmountNetto);
            recipeIngredient.setMeasurement(measurement);
            recipeIngredient.setPreparation(preparation);
        }
    }

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
        assertThat(recipeIngredient.getPreparation(), is("cut into 3x3 mm sticks"));
    }

    @Test
    public void shouldAdd() throws Exception {
        RecipeIngredientData data = new RecipeIngredientData();
        data.recipeId = 1L;
        data.ingredientId = 5;
        data.amount = new BigDecimal("2.00");
        data.measurement = "tbsp";
        data.preparation = "rub in the fist";

        RecipeIngredient recipeIngredient = new RecipeIngredient();
        data.fill(recipeIngredient);

        testingHelper.assertAdding(service, recipeIngredient, data);
        assertThat(recipeIngredient.getId(), is(5L)); // id is updated in the the original object
    }

    @Test
    public void shouldUpdate() throws Exception {
        RecipeIngredientData data = new RecipeIngredientData();
        data.recipeId = 1L;
        data.ingredientId = 6;
        data.amount = new BigDecimal("6.00");
        data.isAmountNetto = false;
        data.measurement = "handful";
        data.preparation = "dried overnight";

        RecipeIngredient recipeIngredient = service.getById(2L);

        testingHelper.assertUpdating(service, recipeIngredient, data);
    }

    @Test
    public void shouldDelete() throws Exception {
        testingHelper.assertDeleting(service, 1L);
    }
}
