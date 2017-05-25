package info.palamarchuk.api.cooking;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import info.palamarchuk.api.cooking.entity.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class RecipesEndpointTest {

    @MockBean
    private RecipeService recipeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnRecipeById() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(3);
        recipe.setName("Chilli con carne");
        given(recipeService.getById(3)).willReturn(recipe);

        MvcResult result = mockMvc.perform(get("/recipes/3"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id", is(3)))
            .andExpect(jsonPath("$.data.name", is("Chilli con carne")))
            .andReturn();
    }

    @Test
    public void shouldAddRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setName("Chilli con carne");

        mockMvc.perform(post("/recipes")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content("{\"name\": \"Chilli con carne\"}")
        ).andExpect(status().isCreated())
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].name", is("Chilli con carne")));

        ArgumentCaptor<Recipe> argument = ArgumentCaptor.forClass(Recipe.class);
        verify(recipeService).add(argument.capture());
        assertThat(argument.getValue().getName(), is("Chilli con carne"));
    }

}