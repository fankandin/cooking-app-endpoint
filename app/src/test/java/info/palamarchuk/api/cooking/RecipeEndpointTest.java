package info.palamarchuk.api.cooking;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class RecipeEndpointTest {

    @MockBean
    private RecipeService recipeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnRecipeById() throws Exception {
        Recipe recipe = new Recipe();
        long id = 3L;
        recipe.setId(id);
        String name = "Chilli con carne";
        recipe.setName(name);
        given(recipeService.getById(id)).willReturn(recipe);

        MvcResult result = mockMvc.perform(get("/recipes/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id", is((int)id)))
            .andExpect(jsonPath("$.data.name", is(name)))
            .andReturn();
    }

    @Test
    public void shouldAddRecipe() throws Exception {
        Recipe recipe = new Recipe();
        String name = "Chilli con carne";
        recipe.setName(name);

        mockMvc.perform(post("/recipes")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content("{\"name\": \""+ name +"\"}")
        ).andExpect(status().isCreated())
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].name", is(name)));

        ArgumentCaptor<Recipe> argument = ArgumentCaptor.forClass(Recipe.class);
        verify(recipeService).add(argument.capture());
        assertThat(argument.getValue().getName(), is(name));
    }

}