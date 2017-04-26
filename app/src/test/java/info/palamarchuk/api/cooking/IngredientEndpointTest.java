package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.data.IngredientEntityData;
import info.palamarchuk.api.cooking.data.IngredientTranslationEntityData;
import info.palamarchuk.api.cooking.entity.Ingredient;
import info.palamarchuk.api.cooking.entity.IngredientTranslation;
import info.palamarchuk.api.cooking.service.IngredientService;
import org.json.JSONObject;
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
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        ResultActions resultActions = mockMvc.perform(get("/ingredients/" + data.getId()));
        verifyResponseData(resultActions, data, "$.data")
            .andExpect(status().isOk())
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

        ResultActions resultActions = mockMvc.perform(get("/ingredients"));
        verifyResponseData(resultActions, data1, "$.data[0]");
        verifyResponseData(resultActions, data2, "$.data[1]")
            .andExpect(jsonPath("$.data", hasSize(2)))
            .andExpect(status().isOk())
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

        mockMvc.perform(get("/ingredients/" + data.getId() + "/translations"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data", hasSize(translations.size())))
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
            .content(makeJson(data))
        ).andExpect(status().isCreated())
            .andExpect(header().string("location", endsWith("/ingredients/" + data.getId())));

        verify(service).add(argument.capture());
        assertThat(argument.getValue().getName(), is(data.getName()));
    }

    @Test
    public void shouldPatch() throws Exception {
        IngredientEntityData data = new IngredientEntityData().setId(6).setName("Grana Padano");
        IngredientEntityData dataPatch = new IngredientEntityData().setName("Parmigiano");
        when(service.getById(data.getId())).thenReturn(data.makeEntity());

        ArgumentCaptor<Ingredient> argument = ArgumentCaptor.forClass(Ingredient.class);
        mockMvc.perform(patch("/ingredients/" + data.getId())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(makeJson(dataPatch))
        ).andExpect(status().isNoContent())
            .andExpect(header().string("location", endsWith("/ingredients/" + data.getId())));

        verify(service).update(argument.capture());
        assertThat(argument.getValue().getId(), is(data.getId()));
        assertThat(argument.getValue().getName(), is(dataPatch.getName()));
    }

    @Test
    public void shouldNotPatchWithoutName() throws Exception {
        IngredientEntityData data = new IngredientEntityData().setId(6).setName("Grana Padano");
        IngredientEntityData dataPatch = new IngredientEntityData();
        when(service.getById(data.getId())).thenReturn(data.makeEntity());

        ResultActions resultActions = mockMvc.perform(patch("/ingredients/" + data.getId())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(makeJson(dataPatch))
        );
        resultActions
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].code", is("required.ingredient")))
            .andExpect(jsonPath("$.errors[0].field", is("name")));
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

    private ResultActions verifyResponseData(ResultActions resultActions, IngredientEntityData data, String jsonPathBase) throws Exception {
        return resultActions
            .andExpect(jsonPath(jsonPathBase + ".id", is(data.getId())))
            .andExpect(jsonPath(jsonPathBase + ".name", is(data.getName())));
    }

    /**
     * Makes JSON out of IngredientEntityData.
     * @param data
     * @return
     */
    private String makeJson(IngredientEntityData data) {
        Map<String, Object> exportData = new HashMap<>();
        exportData.put("name", data.getName());
        return new JSONObject(exportData).toString();
    }
}
