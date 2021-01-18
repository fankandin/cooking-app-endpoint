package info.palamarchuk.api.cooking;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import info.palamarchuk.api.cooking.data.LanguageEntityData;
import info.palamarchuk.api.cooking.entity.Language;
import info.palamarchuk.api.cooking.service.LanguageService;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class LanguageEndpointTest {

    @MockBean
    private LanguageService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetAll() throws Exception {
        LanguageEntityData data1 = new LanguageEntityData().setId((short)2).setCode("de-ch");
        LanguageEntityData data2 = new LanguageEntityData().setId((short)4).setCode("en-us");

        List<Language> languages = new ArrayList<>();
        languages.add(data1.makeEntity());
        languages.add(data2.makeEntity());

        given(service.getAll()).willReturn(languages);

        mockMvc.perform(get("/languages"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data", hasSize(2)))
            .andExpect(jsonPath("$.data[0].id", is(data1.getId().intValue())))
            .andExpect(jsonPath("$.data[0].code", is(data1.getCode())))
            .andExpect(jsonPath("$.data[1].id", is(data2.getId().intValue())))
            .andExpect(jsonPath("$.data[1].code", is(data2.getCode())))
            .andReturn();
    }
}
