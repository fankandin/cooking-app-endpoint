package info.palamarchuk.api.cooking;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import info.palamarchuk.api.cooking.data.IngredientTranslationEntityData;
import info.palamarchuk.api.cooking.entity.IngredientTranslation;
import info.palamarchuk.api.cooking.service.IngredientTranslationService;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class IngredientTranslationEndpointTest {

    @MockBean
    private IngredientTranslationService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetById() throws Exception {
        IngredientTranslationEntityData data = new IngredientTranslationEntityData().setId(2)
            .setLanguageId((short)3).setName("Petersilie").setNameExtra("Glatte Petersilie");
        given(service.getById(data.getId())).willReturn(Optional.of(data.makeEntity()));

        ResultActions resultActions = mockMvc.perform(get("/ingredients/translations/" + data.getId()));
        verifyResponseData(resultActions, data, "$.data")
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    public void shouldAdd() throws Exception {
        IngredientTranslationEntityData data = new IngredientTranslationEntityData().setId(5)
            .setLanguageId((short)3).setIngredientId(2)
            .setName("Dill").setNameExtra("Gurkenkraut").setNote("Bitte, kein Trocken");

        ArgumentCaptor<IngredientTranslation> argument = ArgumentCaptor.forClass(IngredientTranslation.class);
        Mockito.doAnswer(invocation -> {
            IngredientTranslation translation = (IngredientTranslation) invocation.getArguments()[0];
            translation.setId(data.getId());
            return null;
        }).when(service).add(argument.capture());

        mockMvc.perform(post("/ingredients/translations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(makeJson(data))
        ).andExpect(status().isCreated())
            .andExpect(header().string("location", endsWith("/ingredients/translations/" + data.getId())));

        verify(service).add(argument.capture());
        assertThat(argument.getValue().getLanguageId(), is(data.getLanguageId()));
        assertThat(argument.getValue().getName(), is(data.getName()));
        assertThat(argument.getValue().getNameExtra(), is(data.getNameExtra()));
        assertThat(argument.getValue().getNote(), is(data.getNote()));
    }

    @Test
    public void shouldNotAddWithoutIngredientId() throws Exception {
        IngredientTranslationEntityData data = new IngredientTranslationEntityData().setId(5)
            .setLanguageId((short)3).setName("Dill");
        ResultActions resultActions = mockMvc.perform(post("/ingredients/translations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(makeJson(data))
        );
        resultActions
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].code", is("required.ingredient.translation")))
            .andExpect(jsonPath("$.errors[0].field", is("ingredientId")));
    }

    @Test
    public void shouldNotAddWithoutLanguageId() throws Exception {
        IngredientTranslationEntityData data = new IngredientTranslationEntityData().setId(5)
            .setIngredientId(1).setName("Dill");

        ResultActions resultActions = mockMvc.perform(post("/ingredients/translations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(makeJson(data))
        );
        resultActions
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].code", is("required.ingredient.translation")))
            .andExpect(jsonPath("$.errors[0].field", is("languageId")));
    }

    @Test
    public void shouldPatch() throws Exception {
        IngredientTranslationEntityData data = new IngredientTranslationEntityData().setId(7)
            .setIngredientId(2).setLanguageId((short)2).setName("Grana Padano").setNameExtra("Parmesan");
        IngredientTranslationEntityData dataPatch = new IngredientTranslationEntityData()
            .setLanguageId((short)5).setName("Parmigiano").setNameExtra("Parmesan").setNote("Of course, not factory-grated");
        when(service.getById(data.getId())).thenReturn(Optional.of(data.makeEntity()));
        when(service.getByIngredientIdAndLangId(data.getIngredientId(), dataPatch.getLanguageId())).thenReturn(null); // validation checks for possible conflicts for the new unique key

        ArgumentCaptor<IngredientTranslation> argument = ArgumentCaptor.forClass(IngredientTranslation.class);
        mockMvc.perform(patch("/ingredients/translations/" + data.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(makeJson(dataPatch))
        ).andExpect(status().isNoContent())
            .andExpect(header().string("location", endsWith("/ingredients/translations/" + data.getId())));

        verify(service).update(argument.capture());
        assertThat(argument.getValue().getId(), is(data.getId()));
        assertThat(argument.getValue().getLanguageId(), is(dataPatch.getLanguageId()));
        assertThat(argument.getValue().getName(), is(dataPatch.getName()));
        assertThat(argument.getValue().getNameExtra(), is(dataPatch.getNameExtra()));
        assertThat(argument.getValue().getNote(), is(dataPatch.getNote()));
    }

    @Test
    public void shouldNotPatchWithConflictingLanguageId() throws Exception {
        IngredientTranslationEntityData data = new IngredientTranslationEntityData().setId(7)
            .setIngredientId(2).setLanguageId((short)2).setName("Grana Padano");
        IngredientTranslationEntityData dataConflicting = new IngredientTranslationEntityData().setId(8)
            .setIngredientId(2).setLanguageId((short)5).setName("Parmigiano");
        IngredientTranslationEntityData dataPatch = new IngredientTranslationEntityData()
            .setLanguageId((short)5).setName("Parmesan");
        when(service.getById(data.getId())).thenReturn(Optional.of(data.makeEntity()));
        when(service.getByIngredientIdAndLangId(data.getIngredientId(), dataPatch.getLanguageId())).thenReturn(dataConflicting.makeEntity()); // validation checks for possible conflicts for the new unique key

        ResultActions resultActions = mockMvc.perform(patch("/ingredients/translations/" + data.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(makeJson(dataPatch))
        );
        resultActions
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].code", is("duplicated.ingredient.translation")))
            .andExpect(jsonPath("$.errors[0].field", is("languageId")));
    }

    @Test
    public void shouldDelete() throws Exception {
        IngredientTranslationEntityData data = new IngredientTranslationEntityData().setId(7);
        when(service.existsById(data.getId())).thenReturn(true);

        var argument = ArgumentCaptor.forClass(Integer.class);
        mockMvc.perform(delete("/ingredients/translations/" + data.getId()))
            .andExpect(status().isNoContent());

        verify(service).deleteById(argument.capture());
        assertThat(argument.getValue(), is((long)data.getId()));
    }

    @Test
    public void shouldReturnNotFoundOnDeleteNonexistent() throws Exception {
        int id = 8;
        when(service.getById(id)).thenReturn(null);

        mockMvc.perform(delete("/ingredients/translations/" + id))
            .andExpect(status().isNotFound());

        verify(service, never()).deleteById(id);
    }

    private ResultActions verifyResponseData(ResultActions resultActions, IngredientTranslationEntityData data, String jsonPathBase) throws Exception {
        return resultActions
            .andExpect(jsonPath(jsonPathBase + ".id", is(data.getId())))
            .andExpect(jsonPath(jsonPathBase + ".ingredientId", is(data.getIngredientId())))
            .andExpect(jsonPath(jsonPathBase + ".languageId", is(data.getLanguageId().intValue())))
            .andExpect(jsonPath(jsonPathBase + ".name", is(data.getName())))
            .andExpect(jsonPath(jsonPathBase + ".nameExtra", is(data.getNameExtra())));
    }

    /**
     * Makes JSON out of IngredientTranslationEntityData.
     */
    private String makeJson(IngredientTranslationEntityData data) {
        Map<String, Object> exportData = new HashMap<>();
        exportData.put("ingredientId", data.getIngredientId());
        exportData.put("languageId", data.getLanguageId());
        exportData.put("name", data.getName());
        exportData.put("nameExtra", data.getNameExtra());
        exportData.put("note", data.getNote());
        return new JSONObject(exportData).toString();
    }
}
