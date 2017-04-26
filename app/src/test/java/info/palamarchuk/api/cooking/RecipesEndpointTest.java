package info.palamarchuk.api.cooking;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import info.palamarchuk.api.cooking.entity.Recipe;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Ignore
public class RecipesEndpointTest {

  @MockBean
  private RecipeService recipeService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturnAvailableTypes() throws Exception {
    Recipe recipe = new Recipe();
    recipe.setId(1);
    recipe.setTitle("Плов");


    given(recipeService.findById(1)).willReturn(Collections.singletonList(recipe));
    mockMvc.perform(get("/recipe/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data", hasSize(1)))
        .andExpect(jsonPath("$.data[0].id", is(1)))
        .andExpect(jsonPath("$.data[0].title", is("Плов")));
  }

}