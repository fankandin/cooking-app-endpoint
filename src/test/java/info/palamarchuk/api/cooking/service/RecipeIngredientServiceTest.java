package info.palamarchuk.api.cooking.service;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import info.palamarchuk.api.cooking.data.RecipeIngredientEntityData;
import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import info.palamarchuk.api.cooking.helper.EntityDataVerifiable;
import info.palamarchuk.api.cooking.helper.ServiceTestHelper;
import lombok.Setter;
import lombok.experimental.Accessors;

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

    @Accessors(chain = true)
    @Setter
    public class Verifier implements EntityDataVerifiable<RecipeIngredient> {
        final private RecipeIngredientEntityData data;

        public Verifier(RecipeIngredientEntityData data) {
            this.data = data;
        }

        @Override
        public void verify(RecipeIngredient recipeIngredient) {
            assertThat(recipeIngredient.getRecipeId(), is(data.getRecipeId()));
            assertThat(recipeIngredient.getIngredientId(), is(data.getIngredientId()));
            assertThat(recipeIngredient.getAmount(), is(data.getAmount()));
            assertThat(recipeIngredient.isAmountNetto(), is(data.isAmountNetto()));
            assertThat(recipeIngredient.getMeasurement(), is(data.getMeasurement()));
            assertThat(recipeIngredient.getPreparation(), is(data.getPreparation()));
        }

        @Override
        public void fill(RecipeIngredient recipeIngredient) {
            recipeIngredient.setRecipeId(data.getRecipeId());
            recipeIngredient.setIngredientId(data.getIngredientId());
            recipeIngredient.setAmount(data.getAmount());
            recipeIngredient.setAmountNetto(data.isAmountNetto());
            recipeIngredient.setMeasurement(data.getMeasurement());
            recipeIngredient.setPreparation(data.getPreparation());
        }
    }

    private RecipeIngredientEntityData[] storedRecipeIngredients = {
        new RecipeIngredientEntityData().setId(1L)
            .setRecipeId(1L)
            .setIngredientId(1)
            .setAmount("250.00")
            .setAmountNetto(true)
            .setMeasurement("gram")
            .setPreparation("halved rings"),
        new RecipeIngredientEntityData().setId(2L)
            .setRecipeId(1L)
            .setIngredientId(2)
            .setAmount("500.00")
            .setAmountNetto(true)
            .setMeasurement("gram")
            .setPreparation("cut into 3x3 mm sticks"),
        new RecipeIngredientEntityData().setId(3L)
            .setRecipeId(1L)
            .setIngredientId(3)
            .setAmount("700.00")
            .setAmountNetto(false)
            .setMeasurement("gram")
    };

    @Test
    public void shouldGetById() throws Exception {
        new Verifier(storedRecipeIngredients[1]).verify(service.getById(storedRecipeIngredients[1].getId()).get());
    }

    @Test
    public void shouldGetAllByRecipeId() throws Exception {
        Map<Long, RecipeIngredientEntityData> expected = new HashMap<>();
        for (int i=0; i<storedRecipeIngredients.length; i++) {
            expected.put(storedRecipeIngredients[i].getId(), storedRecipeIngredients[i]);
        }

        List<RecipeIngredient> recipeIngredients = service.getAllByRecipeId(1);
        assertThat(recipeIngredients.size(), is(3));
        for (RecipeIngredient recipeIngredient : recipeIngredients) {
            new Verifier(expected.get(recipeIngredient.getId())).verify(recipeIngredient);
        }
    }

    @Test
    public void shouldGetByRecipeIdAndIngredientId() throws Exception {
        RecipeIngredient recipeIngredient = service.getByRecipeIdAndIngredientId(storedRecipeIngredients[2].getRecipeId(), storedRecipeIngredients[2].getIngredientId());
        new Verifier(storedRecipeIngredients[2]).verify(recipeIngredient);
    }

    @Test
    public void shouldAdd() throws Exception {
        Verifier verifier = new Verifier(new RecipeIngredientEntityData()
            .setRecipeId(1L)
            .setIngredientId(5)
            .setAmount("2.00")
            .setMeasurement("tbsp")
            .setPreparation("rub in the fist")
        );

        RecipeIngredient recipeIngredient = new RecipeIngredient();
        verifier.fill(recipeIngredient);

        testingHelper.assertAdding(service, recipeIngredient, verifier);
        assertThat(recipeIngredient.getId(), notNullValue()); // id is updated in the the original object
    }

    @Test
    public void shouldUpdate() throws Exception {
        Verifier verifier = new Verifier(new RecipeIngredientEntityData()
            .setRecipeId(1L)
            .setIngredientId(6)
            .setAmount("6.00")
            .setAmountNetto(false)
            .setMeasurement("handful")
            .setPreparation("dried overnight")
        );

        testingHelper.assertUpdating(service, service.getById(2L).get(), verifier);
    }

    @Test
    public void shouldDelete() throws Exception {
        testingHelper.assertDeleting(service, 1L);
    }
}
