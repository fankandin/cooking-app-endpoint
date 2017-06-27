package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.data.IngredientEntityData;
import info.palamarchuk.api.cooking.data.IngredientTranslationEntityData;
import info.palamarchuk.api.cooking.entity.Ingredient;
import info.palamarchuk.api.cooking.entity.IngredientTranslation;
import info.palamarchuk.api.cooking.service.IngredientService;
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
public class IngredientEndpointTest {

    @MockBean
    private IngredientService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetById() throws Exception {
        IngredientEntityData data = new IngredientEntityData().setId(1).setName("lamb");
        given(service.getById(data.getId())).willReturn(data.makeEntity());

        MvcResult result = mockMvc.perform(get("/ingredients/" + data.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id", is(data.getId())))
            .andExpect(jsonPath("$.data.name", is(data.getName())))
            .andReturn();
    }

    @Test
    public void shouldGetAll() throws Exception {
        IngredientEntityData data1 = new IngredientEntityData().setId(3).setName("rosemary");
        IngredientEntityData data2 = new IngredientEntityData().setId(4).setName("dill");

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(data1.makeEntity());
        ingredients.add(data2.makeEntity());

        given(service.getAll()).willReturn(ingredients);

        MvcResult result = mockMvc.perform(get("/ingredients"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data", hasSize(2)))
            .andExpect(jsonPath("$.data[0].id", is(data1.getId())))
            .andExpect(jsonPath("$.data[0].name", is(data1.getName())))
            .andExpect(jsonPath("$.data[1].id", is(data2.getId())))
            .andExpect(jsonPath("$.data[1].name", is(data2.getName())))
            .andReturn();
    }

    @Test
    public void shouldGetTranslations() throws Exception {
        IngredientEntityData data = new IngredientEntityData().setId(2).setName("Вода");
        Ingredient ingredient = data.makeEntity();

        IngredientTranslationEntityData translationData1 = new IngredientTranslationEntityData().setId(1)
            .setLanguageId((short)3).setName("Wasser");
        IngredientTranslationEntityData translationData2 = new IngredientTranslationEntityData().setId(2)
            .setLanguageId((short)2).setName("Water");

        ArrayList<IngredientTranslation> translations = new ArrayList<>();
        translations.add(translationData1.makeEntity());
        translations.add(translationData2.makeEntity());
        ingredient.setTranslations(translations);

        given(service.getById(data.getId())).willReturn(ingredient);

        MvcResult result = mockMvc.perform(get("/ingredients/" + data.getId() + "/translations"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data", hasSize(2)))
            .andExpect(jsonPath("$.data[0].id", is(translationData1.getId())))
            .andExpect(jsonPath("$.data[0].name", is(translationData1.getName())))
            .andExpect(jsonPath("$.data[1].id", is(translationData2.getId())))
            .andExpect(jsonPath("$.data[1].name", is(translationData2.getName())))
            .andReturn();
    }

    @Test
    public void shouldAdd() throws Exception {
        IngredientEntityData data = new IngredientEntityData().setId(5).setName("Mozzarella");

        ArgumentCaptor<Ingredient> argument = ArgumentCaptor.forClass(Ingredient.class);
        Mockito.doAnswer(new Answer() {
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Ingredient ingredient = (Ingredient) invocation.getArguments()[0];
                ingredient.setId(data.getId());
                return null;
            }
        }).when(service).add(argument.capture());

        mockMvc.perform(post("/ingredients")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content("{\"name\": \"" + data.getName() + "\"}")
        ).andExpect(status().isCreated())
            .andExpect(header().string("location", endsWith("/ingredients/" + data.getId())));

        verify(service).add(argument.capture());
        assertThat(argument.getValue().getName(), is(data.getName()));
    }

    @Test
    public void shouldPatch() throws Exception {
        IngredientEntityData data = new IngredientEntityData().setId(6).setName("Grana Padano");
        String patchName = "Parmigiano";

        when(service.getById(data.getId())).thenReturn(data.makeEntity());

        ArgumentCaptor<Ingredient> argument = ArgumentCaptor.forClass(Ingredient.class);
        mockMvc.perform(patch("/ingredients/" + data.getId())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content("{\"name\": \"" + patchName + "\"}")
        ).andExpect(status().isNoContent())
            .andExpect(header().string("location", endsWith("/ingredients/" + data.getId())));

        verify(service).update(argument.capture());
        assertThat(argument.getValue().getId(), is(data.getId()));
        assertThat(argument.getValue().getName(), is(patchName));
    }

    @Test
    public void shouldDelete() throws Exception {
        IngredientEntityData data = new IngredientEntityData().setId(7).setName("tofu");
        when(service.getById(data.getId())).thenReturn(data.makeEntity());

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        mockMvc.perform(delete("/ingredients/" + data.getId()))
            .andExpect(status().isNoContent());

        verify(service).deleteById(argument.capture());
        assertThat(argument.getValue(), is((long)data.getId()));
    }

    @Test
    public void shouldReturnNotFoundOnDeleteNonexistent() throws Exception {
        int id = 8;
        when(service.getById(id)).thenReturn(null);

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        mockMvc.perform(delete("/ingredients/" + id))
            .andExpect(status().isNotFound());

        verify(service, never()).deleteById(id);
    }
}
