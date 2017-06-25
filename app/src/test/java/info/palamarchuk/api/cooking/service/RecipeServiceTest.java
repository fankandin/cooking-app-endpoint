package info.palamarchuk.api.cooking.service;

import info.palamarchuk.api.cooking.entity.Recipe;
import info.palamarchuk.api.cooking.helper.EntityDataVerifiable;
import info.palamarchuk.api.cooking.helper.ServiceTestHelper;
import info.palamarchuk.api.cooking.service.RecipeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
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

    public class RecipeData implements EntityDataVerifiable<Recipe> {
        String title;
        Time cookTime;
        Time precookTime;
        String annotation;
        String method;
        short languageId;

        @Override
        public void verify(Recipe recipe) {
            assertThat(recipe.getTitle(), is(title));
            assertThat(recipe.getCookTime(), is(cookTime));
            assertThat(recipe.getPrecookTime(), is(precookTime));
            assertThat(recipe.getAnnotation(), is(annotation));
            assertThat(recipe.getMethod(), is(method));
            assertThat(recipe.getLanguageId(), is(languageId));
        }

        @Override
        public void fill(Recipe recipe) {
            recipe.setTitle(title);
            recipe.setCookTime(cookTime);
            recipe.setLanguageId(languageId);
            recipe.setAnnotation(annotation);
            recipe.setMethod(method);
        }
    }

    @Test
    @Transactional
    public void shouldFindById() throws Exception {
        Recipe recipe = service.getById(1);
        assertThat(recipe.getId(), is(1L));
        assertThat(recipe.getTitle(), is("Plov Tashkent style"));
        assertThat(recipe.getCookTime(), is(Time.valueOf("02:00:00")));
        assertThat(recipe.getPrecookTime(), is(Time.valueOf("01:30:00")));
        assertThat(recipe.getAnnotation(), is("Delicious traditional Uzbek course"));
        assertThat(recipe.getMethod(), is("Cut the carrot into 3x3 mm sticks (like duble-size julienne) and slice the onion into half-rings..."));
        assertThat(recipe.getLanguageId(), is((short)2));
        assertThat(recipe.getLanguage().getCode(), is("en"));
        assertThat(recipe.getIngredients().size(), is(3));

        recipe = service.getById(2);
        assertThat(recipe.getId(), is(2L));
        assertThat(recipe.getTitle(), is("Greek salad"));
    }

    @Test
    public void shouldAdd() throws Exception {
        RecipeData data = new RecipeData();
        data.title = "Chilli con carne";
        data.cookTime = Time.valueOf("03:50:00");
        data.annotation = "Some annotation";
        data.method = "Here I'm telling you how to cook";
        data.languageId = 2;

        Recipe recipe = new Recipe();
        data.fill(recipe);

        testingHelper.assertAdding(service, recipe, data);
        assertThat(recipe.getId(), is(3L)); // id is updated in the the original object
    }

    @Test
    public void shouldUpdate() throws Exception {
        RecipeData data = new RecipeData();
        data.title = "Plov Fergana style";
        data.cookTime = Time.valueOf("03:00:00");
        data.precookTime = Time.valueOf("01:30:00");
        data.annotation = "Some annotation";
        data.method = "Here I'm telling you how to cook";
        data.languageId = 1;

        Recipe recipe = service.getById(1L);

        testingHelper.assertUpdating(service, recipe, data);
    }

    @Test
    public void shouldDelete() throws Exception {
        testingHelper.assertDeleting(service, 1L);
    }
}