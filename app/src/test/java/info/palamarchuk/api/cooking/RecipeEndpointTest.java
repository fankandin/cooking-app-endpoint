package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.data.IngredientEntityData;
import info.palamarchuk.api.cooking.data.RecipeEntityData;
import info.palamarchuk.api.cooking.data.RecipeIngredientEntityData;
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
        RecipeEntityData data = new RecipeEntityData().setId(1L).setTitle("Chilli con carne");
        given(service.getById(data.getId())).willReturn(data.makeEntity());

        MvcResult result = mockMvc.perform(get("/recipes/" + data.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id", is(data.getId().intValue())))
            .andExpect(jsonPath("$.data.title", is(data.getTitle())))
            .andReturn();
    }

    @Test
    public void shouldGetAll() throws Exception {
        RecipeEntityData data1 = new RecipeEntityData().setId(3L).setTitle("Cheese cake");
        RecipeEntityData data2 = new RecipeEntityData().setId(4L).setTitle("Tuna salad");

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(data1.makeEntity());
        recipes.add(data2.makeEntity());

        given(service.getAll()).willReturn(recipes);

        MvcResult result = mockMvc.perform(get("/recipes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data", hasSize(2)))
            .andExpect(jsonPath("$.data[0].id", is(data1.getId().intValue())))
            .andExpect(jsonPath("$.data[0].title", is(data1.getTitle())))
            .andExpect(jsonPath("$.data[1].id", is(data2.getId().intValue())))
            .andExpect(jsonPath("$.data[1].title", is(data2.getTitle())))
            .andReturn();
    }

    @Test
    public void shouldGetIngredients() throws Exception {
        RecipeEntityData data = new RecipeEntityData().setId(3L).setTitle("Lagman soup");
        List<RecipeIngredient> recipeIngredients = new ArrayList<>();

        IngredientEntityData ingredientData1 = new IngredientEntityData().setId(1).setName("Lamb");
        IngredientEntityData ingredientData2 = new IngredientEntityData().setId(2).setName("Noodles");

        RecipeIngredientEntityData recipeIngredientData1 = new RecipeIngredientEntityData().setId(21L)
            .setRecipeId(data.getId())
            .setIngredientId(ingredientData1.getId())
            .setAmount("500").setMeasurement("gram");
        RecipeIngredient recipeIngredient1 = recipeIngredientData1.makeEntity();
        recipeIngredient1.setIngredient(ingredientData1.makeEntity());

        RecipeIngredientEntityData recipeIngredientData2 = new RecipeIngredientEntityData().setId(22L)
            .setRecipeId(data.getId())
            .setIngredientId(ingredientData2.getId())
            .setAmount("1").setMeasurement("unit");
        RecipeIngredient recipeIngredient2 = recipeIngredientData2.makeEntity();
        recipeIngredient2.setIngredient(ingredientData2.makeEntity());

        recipeIngredients.add(recipeIngredient1);
        recipeIngredients.add(recipeIngredient2);

        Recipe recipe = data.makeEntity();
        recipe.setIngredients(recipeIngredients);

        given(service.getById(data.getId())).willReturn(recipe);
        MvcResult result = mockMvc.perform(get("/recipes/" + data.getId() + "/ingredients"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data", hasSize(2)))
            .andExpect(jsonPath("$.data[0].id", is((recipeIngredientData1.getId().intValue()))))
            .andExpect(jsonPath("$.data[0].amount", is(recipeIngredientData1.getAmount().toBigInteger().intValue())))
            .andExpect(jsonPath("$.data[0].measurement", is(recipeIngredientData1.getMeasurement())))
            .andExpect(jsonPath("$.data[0].ingredient.id", is(ingredientData1.getId())))
            .andExpect(jsonPath("$.data[0].ingredient.name", is(ingredientData1.getName())))
            .andExpect(jsonPath("$.data[1].id", is(recipeIngredientData2.getId().intValue())))
            .andExpect(jsonPath("$.data[1].amount", is(recipeIngredientData2.getAmount().toBigInteger().intValue())))
            .andExpect(jsonPath("$.data[1].measurement", is(recipeIngredientData2.getMeasurement())))
            .andExpect(jsonPath("$.data[1].ingredient.id", is(ingredientData2.getId())))
            .andExpect(jsonPath("$.data[1].ingredient.name", is(ingredientData2.getName())))
            .andReturn();
    }

    @Test
    public void shouldAdd() throws Exception {
        RecipeEntityData data = new RecipeEntityData().setId(3L).setTitle("Chilli con carne");

        ArgumentCaptor<Recipe> argument = ArgumentCaptor.forClass(Recipe.class);
        Mockito.doAnswer(new Answer() {
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Recipe recipe = (Recipe) invocation.getArguments()[0];
                recipe.setId(data.getId());
                return null;
            }
        }).when(service).add(argument.capture());

        mockMvc.perform(post("/recipes")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content("{\"title\": \"" + data.getTitle() + "\"}")
        ).andExpect(status().isCreated())
            .andExpect(header().string("location", endsWith("/recipes/" + data.getId())));

        verify(service).add(argument.capture());
        assertThat(argument.getValue().getTitle(), is(data.getTitle()));

    }

    @Test
    public void shouldPatch() throws Exception {
        RecipeEntityData data = new RecipeEntityData().setId(4L).setTitle("Ragu Bolognese");
        String patchName = "Ragù alla bolognese";

        when(service.getById(data.getId())).thenReturn(data.makeEntity());

        ArgumentCaptor<Recipe> argument = ArgumentCaptor.forClass(Recipe.class);
        mockMvc.perform(patch("/recipes/" + data.getId())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content("{\"title\": \"" + patchName + "\"}")
        ).andExpect(status().isNoContent())
            .andExpect(header().string("location", endsWith("/recipes/" + data.getId())));

        verify(service).update(argument.capture());
        assertThat(argument.getValue().getId(), is(data.getId()));
        assertThat(argument.getValue().getTitle(), is(patchName));
    }

    @Test
    public void shouldDelete() throws Exception {
        RecipeEntityData data = new RecipeEntityData().setId(5L).setTitle("Tofu burger");

        when(service.getById(data.getId())).thenReturn(data.makeEntity());
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        mockMvc.perform(delete("/recipes/" + data.getId()))
            .andExpect(status().isNoContent());

        verify(service).deleteById(argument.capture());
        assertThat(argument.getValue(), is(data.getId()));
    }

    @Test
    public void shouldReturnNotFoundOnDeleteNonexistent() throws Exception {
        long id = 6L;
        when(service.getById(id)).thenReturn(null);

        mockMvc.perform(delete("/recipes/" + id))
            .andExpect(status().isNotFound());

        verify(service, never()).deleteById(id);
    }
}