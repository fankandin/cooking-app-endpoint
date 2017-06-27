package info.palamarchuk.api.cooking.service;

import info.palamarchuk.api.cooking.data.RecipeEntityData;
import info.palamarchuk.api.cooking.entity.Recipe;
import info.palamarchuk.api.cooking.helper.EntityDataVerifiable;
import info.palamarchuk.api.cooking.helper.ServiceTestHelper;
import lombok.Setter;
import lombok.experimental.Accessors;
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
public class RecipeServiceTest {

    @Autowired
    RecipeService service;
    @Autowired
    ServiceTestHelper<Recipe> testingHelper;

    @Accessors(chain = true)
    @Setter
    public class Verifier implements EntityDataVerifiable<Recipe> {
        final private RecipeEntityData data;

        public Verifier(RecipeEntityData data) {
            this.data = data;
        }

        @Override
        public void verify(Recipe recipe) {
            assertThat(recipe.getTitle(), is(data.getTitle()));
            assertThat(recipe.getCookTime(), is(data.getCookTime()));
            assertThat(recipe.getPrecookTime(), is(data.getPrecookTime()));
            assertThat(recipe.getAnnotation(), is(data.getAnnotation()));
            assertThat(recipe.getMethod(), is(data.getMethod()));
            assertThat(recipe.getLanguageId(), is(data.getLanguageId()));
        }

        @Override
        public void fill(Recipe recipe) {
            recipe.setTitle(data.getTitle());
            recipe.setCookTime(data.getCookTime());
            recipe.setLanguageId(data.getLanguageId());
            recipe.setAnnotation(data.getAnnotation());
            recipe.setMethod(data.getMethod());
        }
    }

    private RecipeEntityData[] storedRecipes = {
        new RecipeEntityData().setId(1L)
            .setTitle("Plov Tashkent style")
            .setCookTime("02:00:00")
            .setPrecookTime("01:30:00")
            .setAnnotation("Delicious traditional Uzbek course")
            .setMethod("Cut the carrot into 3x3 mm sticks (like duble-size julienne) and slice the onion into half-rings...")
            .setLanguageId((short)2),
        new RecipeEntityData().setId(2L)
            .setTitle("Greek salad")
            .setCookTime("00:30:00")
            .setAnnotation("Tasty refreshing famous salad")
            .setMethod("Add finely graded Parmigiano cheese to the sourcream, finely grade the garlic...")
            .setLanguageId((short)2)
    };

    @Test
    @Transactional
    public void shouldFindById() throws Exception {
        Recipe recipe = service.getById(storedRecipes[0].getId());
        new Verifier(storedRecipes[0]).verify(recipe);
        assertThat(recipe.getId(), is(storedRecipes[0].getId()));
        assertThat(recipe.getLanguage().getCode(), is("en"));
        assertThat(recipe.getIngredients().size(), is(3));

        // test another recipe is selected correctly
        recipe = service.getById(storedRecipes[1].getId());
        new Verifier(storedRecipes[1]).verify(recipe);
    }

    @Test
    public void shouldAdd() throws Exception {
        Verifier verifier = new Verifier(new RecipeEntityData()
            .setTitle("Chilli con carne")
            .setCookTime("03:50:00")
            .setAnnotation("Some annotation")
            .setMethod("Here I'm telling you how to cook")
            .setLanguageId((short)2)
        );

        Recipe recipe = new Recipe();
        verifier.fill(recipe);

        testingHelper.assertAdding(service, recipe, verifier);
        assertThat(recipe.getId(), is(3L)); // id is updated in the the original object
    }

    @Test
    public void shouldUpdate() throws Exception {
        Verifier verifier = new Verifier(new RecipeEntityData()
            .setTitle("Plov Fergana style")
            .setCookTime("03:00:00")
            .setPrecookTime("01:30:00")
            .setAnnotation("Some annotation")
            .setMethod("Here I'm telling you how to cook")
            .setLanguageId((short)1)
        );

        Recipe recipe = service.getById(1L);
        testingHelper.assertUpdating(service, recipe, verifier);
    }

    @Test
    public void shouldDelete() throws Exception {
        testingHelper.assertDeleting(service, 1L);
    }
}