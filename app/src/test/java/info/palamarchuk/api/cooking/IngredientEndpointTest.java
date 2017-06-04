package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Ingredient;
import org.junit.Ignore;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class IngredientEndpointTest {

    @MockBean
    private IngredientService ingredientService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnRecipeById() throws Exception {
        Ingredient ingredient = new Ingredient();
        int id = 4;
        ingredient.setId(id);
        String name = "lamb";
        ingredient.setName(name);
        given(ingredientService.getById(id)).willReturn(ingredient);

        MvcResult result = mockMvc.perform(get("/ingredients/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id", is(id)))
            .andExpect(jsonPath("$.data.name", is(name)))
            .andReturn();
    }

    @Test
    public void shouldAddRecipe() throws Exception {
        Ingredient ingredient = new Ingredient();
        String name = "Mozzarella";
        ingredient.setName(name);

        mockMvc.perform(post("/ingredients")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content("{\"name\": \"" + name + "\"}")
        ).andExpect(status().isCreated())
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].name", is(name)));

        ArgumentCaptor<Ingredient> argument = ArgumentCaptor.forClass(Ingredient.class);
        verify(ingredientService).add(argument.capture());
        assertThat(argument.getValue().getName(), is(name));
    }
}
