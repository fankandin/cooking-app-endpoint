package info.palamarchuk.api.cooking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.palamarchuk.api.cooking.data.RecipeIngredientPatch;
import info.palamarchuk.api.cooking.entity.Ingredient;
import info.palamarchuk.api.cooking.entity.Recipe;
import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class RecipeIngredientEndpointTest {

    @MockBean
    private RecipeIngredientService recipeIngredientService;

    @Autowired
    private MockMvc mockMvc;

    private class Dataset extends RecipeIngredientPatch {
        public Long id;
        public Recipe recipe;
        public Ingredient ingredient;

        RecipeIngredientPatch getPatch() {
            RecipeIngredientPatch patch = new RecipeIngredientPatch();
            patch.amount = amount;
            patch.measurement = measurement;
            patch.isAmountNetto = isAmountNetto;
            return patch;
        }
    }

    private Dataset createExampleDataset(long id, long recipeId, int ingredientId) {
        Dataset data = new Dataset();
        data.id = id;
        data.amount = new BigDecimal("700");
        data.measurement = "gram";
        data.recipe = new Recipe();
        data.recipe.setId(recipeId);
        data.recipe.setName("Pelmeni Siberia style");
        data.ingredient = new Ingredient();
        data.ingredient.setId(ingredientId);
        data.ingredient.setName("wheat");
        return data;
    }

    private RecipeIngredient createRecipeIngredient(Dataset data) {
        RecipeIngredient entity = new RecipeIngredient();
        entity.setId(data.id);
        entity.setAmount(data.amount);
        entity.setMeasurement(data.measurement);
        entity.setRecipe(data.recipe);
        entity.setRecipeId(data.recipe.getId());
        entity.setIngredient(data.ingredient);
        entity.setIngredientId(data.ingredient.getId());
        return entity;
    }

    @Test
    public void shouldGetById() throws Exception {
        Dataset data = createExampleDataset(2L, 25L, 35);
        RecipeIngredient recipeIngredient = createRecipeIngredient(data);

        given(recipeIngredientService.getById(data.id)).willReturn(recipeIngredient);

        mockMvc.perform(get("/recipes-ingredients/" + data.id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id", is(data.id)))
            .andExpect(jsonPath("$.data.amount", is(data.amount.toBigInteger().intValue())))
            .andExpect(jsonPath("$.data.measurement", is(data.measurement)))
            .andExpect(jsonPath("$.data.amountNetto", is(false))) // default
            .andExpect(jsonPath("$.data.ingredient.id", is(data.ingredient.getId())))
            .andExpect(jsonPath("$.data.ingredient.name", is(data.ingredient.getName())))
            .andExpect(jsonPath("$.data.recipe.id", is(data.recipe.getId())))
            .andExpect(jsonPath("$.data.recipe.name", is(data.recipe.getName())))
            .andReturn();
    }

    @Test
    public void shouldPatch() throws Exception {
        RecipeIngredient recipeIngredient = createRecipeIngredient(createExampleDataset(2L, 25L, 35));
        given(recipeIngredientService.getById(recipeIngredient.getId()))
            .willReturn(recipeIngredient);

        RecipeIngredientPatch patch = new RecipeIngredientPatch();
        patch.ingredientId = 45;
        patch.amount = new BigDecimal("2");
        patch.measurement = "handful";
        patch.isAmountNetto = true;

        String body = new ObjectMapper().writeValueAsString(patch);
        mockMvc.perform(
            patch("/recipes-ingredients/" + recipeIngredient.getId())
            .content(body)
            .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isNoContent());

        ArgumentCaptor<RecipeIngredient> argument = ArgumentCaptor.forClass(RecipeIngredient.class);
        verify(recipeIngredientService).update(argument.capture());
        assertThat(argument.getValue().getId(), is(recipeIngredient.getId()));
        assertThat(argument.getValue().getIngredientId(), is(patch.ingredientId));
        assertThat(argument.getValue().getAmount(), is(patch.amount));
        assertThat(argument.getValue().getMeasurement(), is(patch.measurement));
        assertThat(argument.getValue().isAmountNetto(), is(patch.isAmountNetto));
    }

    @Test
    public void shouldFilterIgnoredPatchFields() throws Exception {
        String body = "{\"amount\":2,\"measurement\":\"handful\",\"ingredientId\":5,\"ingredient\":{\"id\":15,\"name\":\"Reis\"},\"recipe\":{\"id\":25,\"name\":\"Risotto\"}}\n";

        RecipeIngredient recipeIngredient = createRecipeIngredient(createExampleDataset(3L, 26L, 36));

        given(recipeIngredientService.getById(recipeIngredient.getId())).willReturn(recipeIngredient);

        mockMvc.perform(
            patch("/recipes-ingredients/" + recipeIngredient.getId())
            .content(body)
            .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
            .andExpect(status().isNoContent())
            .andReturn();

        ArgumentCaptor<RecipeIngredient> argument = ArgumentCaptor.forClass(RecipeIngredient.class);
        verify(recipeIngredientService).update(argument.capture());
        assertThat(argument.getValue().getId(), is(recipeIngredient.getId()));
        assertThat(argument.getValue().getIngredientId(), is(5));
        assertThat(argument.getValue().getAmount(), is(new BigDecimal("2")));
        assertThat(argument.getValue().getMeasurement(), is("handful"));
        assertThat(argument.getValue().isAmountNetto(), is(false));
    }

}
