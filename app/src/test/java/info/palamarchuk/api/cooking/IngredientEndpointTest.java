package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Ingredient;
import info.palamarchuk.api.cooking.entity.IngredientInfo;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        Ingredient ingredient = new Ingredient();
        int id = 4;
        ingredient.setId(id);
        String name = "lamb";
        ingredient.setName(name);
        given(service.getById(id)).willReturn(ingredient);

        MvcResult result = mockMvc.perform(get("/ingredients/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id", is(id)))
            .andExpect(jsonPath("$.data.name", is(name)))
            .andReturn();
    }

    @Test
    public void shouldGetInfos() throws Exception {
        Ingredient ingredient = new Ingredient();
        int id = 5;
        ingredient.setId(id);
        ingredient.setName("Вода");

        IngredientInfo info1 = new IngredientInfo();
        info1.setLanguageId(Short.valueOf((short)3));
        info1.setId(33);
        info1.setName("Wasser");
        IngredientInfo info2 = new IngredientInfo();
        info2.setLanguageId(Short.valueOf((short)2));
        info2.setId(34);
        info2.setName("Water");
        ArrayList<IngredientInfo> infos = new ArrayList<>();
        infos.add(info1);
        infos.add(info2);
        ingredient.setInfos(infos);

        given(service.getById(id)).willReturn(ingredient);

        MvcResult result = mockMvc.perform(get("/ingredients/" + id + "/infos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data", hasSize(2)))
            .andExpect(jsonPath("$.data[0].id", is(info1.getId())))
            .andExpect(jsonPath("$.data[0].name", is(info1.getName())))
            .andExpect(jsonPath("$.data[1].id", is(info2.getId())))
            .andExpect(jsonPath("$.data[1].name", is(info2.getName())))
            .andReturn();
    }

    @Test
    public void shouldAdd() throws Exception {
        String name = "Mozzarella";
        int id = 11;

        ArgumentCaptor<Ingredient> argument = ArgumentCaptor.forClass(Ingredient.class);
        Mockito.doAnswer(new Answer() {
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Ingredient ingredient = (Ingredient) invocation.getArguments()[0];
                ingredient.setId(id);
                return null;
            }
        }).when(service).add(argument.capture());

        mockMvc.perform(post("/ingredients")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content("{\"name\": \"" + name + "\"}")
        ).andExpect(status().isCreated())
            .andExpect(header().string("location", endsWith("/ingredients/11")))
            .andExpect(jsonPath("$.data.id", is(id)))
            .andExpect(jsonPath("$.data.name", is(name)));

        verify(service).add(argument.capture());
        assertThat(argument.getValue().getName(), is(name));
    }

    @Test
    public void shouldPatch() throws Exception {
        Ingredient current = new Ingredient();
        current.setId(12);
        current.setName("Grana Padano");
        String patchName = "Parmigiano";

        when(service.getById(current.getId())).thenReturn(current);

        ArgumentCaptor<Ingredient> argument = ArgumentCaptor.forClass(Ingredient.class);
        mockMvc.perform(patch("/ingredients/" + current.getId())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content("{\"name\": \"" + patchName + "\"}")
        ).andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id", is(current.getId())))
            .andExpect(jsonPath("$.data.name", is(patchName)));

        verify(service).update(argument.capture());
        assertThat(argument.getValue().getId(), is(current.getId()));
        assertThat(argument.getValue().getName(), is(patchName));
    }

    @Test
    public void shouldDelete() throws Exception {
        Ingredient current = new Ingredient();
        current.setId(14);
        when(service.getById(current.getId())).thenReturn(current);

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        mockMvc.perform(delete("/ingredients/" + current.getId()))
            .andExpect(status().isNoContent());

        verify(service).deleteById(argument.capture());
        assertThat(argument.getValue(), is(current.getId()));
    }
}
