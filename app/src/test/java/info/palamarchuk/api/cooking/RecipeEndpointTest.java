package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Ingredient;
import info.palamarchuk.api.cooking.entity.Recipe;
import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import info.palamarchuk.api.cooking.service.RecipeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class RecipeEndpointTest {

    @MockBean
    private RecipeService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetById() throws Exception {
        long id = 1L;
        String name = "Chilli con carne";
        given(service.getById(id)).willReturn(makeRecipe(id, name));

        MvcResult result = mockMvc.perform(get("/recipes/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id", is((int)id)))
            .andExpect(jsonPath("$.data.title", is(name)))
            .andReturn();
    }

    @Test
    public void shouldGetAll() throws Exception {
        Recipe recipe1 = makeRecipe(3, "rosemary");
        Recipe recipe2 = makeRecipe(4, "dill");
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        given(service.getAll()).willReturn(recipes);

        MvcResult result = mockMvc.perform(get("/recipes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data", hasSize(2)))
            .andExpect(jsonPath("$.data[0].id", is(recipe1.getId().intValue())))
            .andExpect(jsonPath("$.data[0].title", is(recipe1.getTitle())))
            .andExpect(jsonPath("$.data[1].id", is(recipe2.getId().intValue())))
            .andExpect(jsonPath("$.data[1].title", is(recipe2.getTitle())))
            .andReturn();
    }

    @Test
    public void shouldGetIngredients() throws Exception {
        long id = 3;
        Recipe recipe = makeRecipe(id, "Lagman soup");
        List<RecipeIngredient> ingredients = new ArrayList<>();

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1);
        ingredient1.setName("Lamb");
        RecipeIngredient recipeIngredient1 = new RecipeIngredient(id, 1, BigDecimal.valueOf(500), "gram");
        recipeIngredient1.setId(21);
        recipeIngredient1.setIngredient(ingredient1);
        ingredients.add(recipeIngredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2);
        ingredient2.setName("Noodles");
        RecipeIngredient recipeIngredient2 = new RecipeIngredient(id, 2, BigDecimal.valueOf(1), "unit");
        recipeIngredient2.setId(22);
        recipeIngredient2.setIngredient(ingredient2);
        ingredients.add(recipeIngredient2);

        recipe.setIngredients(ingredients);

        given(service.getById(id)).willReturn(recipe);
        MvcResult result = mockMvc.perform(get("/recipes/" + id + "/ingredients"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data", hasSize(2)))
            .andExpect(jsonPath("$.data[0].id", is(recipeIngredient1.getId().intValue())))
            .andExpect(jsonPath("$.data[0].amount", is(recipeIngredient1.getAmount().toBigInteger().intValue())))
            .andExpect(jsonPath("$.data[0].measurement", is(recipeIngredient1.getMeasurement())))
            .andExpect(jsonPath("$.data[0].ingredient.id", is(recipeIngredient1.getIngredient().getId())))
            .andExpect(jsonPath("$.data[0].ingredient.name", is(recipeIngredient1.getIngredient().getName())))
            .andExpect(jsonPath("$.data[1].id", is(recipeIngredient2.getId().intValue())))
            .andExpect(jsonPath("$.data[1].amount", is(recipeIngredient2.getAmount().toBigInteger().intValue())))
            .andExpect(jsonPath("$.data[1].measurement", is(recipeIngredient2.getMeasurement())))
            .andExpect(jsonPath("$.data[1].ingredient.id", is(recipeIngredient2.getIngredient().getId())))
            .andExpect(jsonPath("$.data[1].ingredient.name", is(recipeIngredient2.getIngredient().getName())))
            .andReturn();
    }

    @Test
    public void shouldAdd() throws Exception {
        String name = "Chilli con carne";
        long id = 3;

        ArgumentCaptor<Recipe> argument = ArgumentCaptor.forClass(Recipe.class);
        Mockito.doAnswer(new Answer() {
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Recipe recipe = (Recipe) invocation.getArguments()[0];
                recipe.setId(id);
                return null;
            }
        }).when(service).add(argument.capture());

        mockMvc.perform(post("/recipes")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content("{\"name\": \"" + name + "\"}")
        ).andExpect(status().isCreated())
            .andExpect(header().string("location", endsWith("/recipes/" + id)))
            .andExpect(jsonPath("$.data.id", is((int)id)))
            .andExpect(jsonPath("$.data.title", is(name)));

        verify(service).add(argument.capture());
        assertThat(argument.getValue().getTitle(), is(name));

    }

    @Test
    public void shouldPatch() throws Exception {
        Recipe current = makeRecipe(4, "Ragu Bolognese");
        String patchName = "Ragù alla bolognese";

        when(service.getById(current.getId())).thenReturn(current);

        ArgumentCaptor<Recipe> argument = ArgumentCaptor.forClass(Recipe.class);
        mockMvc.perform(patch("/recipes/" + current.getId())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content("{\"name\": \"" + patchName + "\"}")
        ).andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id", is(current.getId().intValue())))
            .andExpect(jsonPath("$.data.title", is(patchName)));

        verify(service).update(argument.capture());
        assertThat(argument.getValue().getId(), is(current.getId()));
        assertThat(argument.getValue().getTitle(), is(patchName));
    }

    @Test
    public void shouldDelete() throws Exception {
        Recipe current = makeRecipe(5, "Tofu burger");
        when(service.getById(current.getId())).thenReturn(current);

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        mockMvc.perform(delete("/recipes/" + current.getId()))
            .andExpect(status().isNoContent());

        verify(service).deleteById(argument.capture());
        assertThat(argument.getValue(), is(current.getId()));
    }

    @Test
    public void shouldReturnNotFoundOnDeleteNonexistent() throws Exception {
        long id = 6L;
        when(service.getById(id)).thenReturn(null);

        mockMvc.perform(delete("/recipes/" + id))
            .andExpect(status().isNotFound());

        verify(service, never()).deleteById(id);
    }

    /**
     * Makes an instance of an Recipe with given field values.
     * @param id
     * @param title
     * @return
     */
    public Recipe makeRecipe(long id, String title) {
        Recipe recipe = new Recipe();
        recipe.setId(id);
        recipe.setTitle(title);
        return recipe;
    }
}