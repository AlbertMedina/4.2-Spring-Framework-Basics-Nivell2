package cat.itacademy.s04.t02.n02.fruit.controllers;

import cat.itacademy.s04.t02.n02.fruit.dto.FruitDTO;
import cat.itacademy.s04.t02.n02.fruit.dto.ProviderDTO;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.fruit.repository.ProviderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ProviderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private FruitRepository fruitRepository;

    @BeforeEach
    void cleanDB() {
        fruitRepository.deleteAll();
        providerRepository.deleteAll();
    }

    @Test
    void createProvider_shouldReturnProviderWithId() throws Exception {
        mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new ProviderDTO("Albert", "Spain"))))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(notNullValue())).andExpect(jsonPath("$.name").value("Albert")).andExpect(jsonPath("$.country").value("Spain"));
    }

    @Test
    void createProvider_shouldNotCreateMultipleProvidersWithSameName() throws Exception {
        mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new ProviderDTO("Albert", "Spain"))))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new ProviderDTO("Albert", "Spain"))))
                .andExpect(status().isConflict());
    }

    @Test
    void updateProvider_shouldUpdateExistingProvider() throws Exception {
        String response = mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new ProviderDTO("Albert", "Spain"))))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Provider provider = objectMapper.readValue(response, Provider.class);

        mockMvc.perform(put("/providers/{id}", provider.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new ProviderDTO("Joao", "Portugal"))))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(notNullValue())).andExpect(jsonPath("$.name").value("Joao")).andExpect(jsonPath("$.country").value("Portugal"));
    }

    @Test
    void updateProvider_shouldReturnNotFoundWhenProviderDoesNotExist() throws Exception {
        mockMvc.perform(put("/providers/{id}", 1L).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new ProviderDTO("Joao", "Portugal"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateProvider_shouldNotUpdateNameIfItBelongsToAnotherExistingProvider() throws Exception {
        String response = mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new ProviderDTO("Albert", "Spain"))))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new ProviderDTO("Joao", "Portugal"))))
                .andExpect(status().isCreated());

        Provider provider = objectMapper.readValue(response, Provider.class);

        mockMvc.perform(put("/providers/{id}", provider.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new ProviderDTO("Joao", "Portugal"))))
                .andExpect(status().isConflict());
    }

    @Test
    void removeProvider_shouldRemoveProviderAndReturnNoContent() throws Exception {
        String response = mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new ProviderDTO("Albert", "Spain"))))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Provider provider = objectMapper.readValue(response, Provider.class);

        mockMvc.perform(delete("/providers/{id}", provider.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/providers/{id}", provider.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void removeProvider_shouldReturnNotFoundWhenProviderDoesNotExist() throws Exception {
        mockMvc.perform(post("/fruits").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FruitDTO("Watermelon", 2, 1L))))
                .andExpect(status().isNotFound());
    }

    @Test
    void removeProvider_shouldNotRemoveProviderWithFruitsAssigned() throws Exception {
        String response = mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new ProviderDTO("Albert", "Spain"))))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Provider provider = objectMapper.readValue(response, Provider.class);

        mockMvc.perform(post("/fruits").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FruitDTO("Watermelon", 2, provider.getId()))))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/providers/{id}", provider.getId()))
                .andExpect(status().isConflict());
    }

    @Test
    void getProviderById_shouldReturnCorrectProvider() throws Exception {
        String response = mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new ProviderDTO("Albert", "Spain"))))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Provider provider = objectMapper.readValue(response, Provider.class);

        mockMvc.perform(get("/providers/{id}", provider.getId())).andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.name").value("Albert")).andExpect(jsonPath("$.country").value("Spain"));
    }

    @Test
    void getProviderById_shouldReturnNotFoundWhenProviderDoesNotExist() throws Exception {
        mockMvc.perform(get("/providers/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProviders_shouldReturnEmptyListInitially() throws Exception {
        mockMvc.perform(get("/providers"))
                .andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    @Test
    void getAllProviders_shouldReturnAllExistingProviders() throws Exception {
        mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new ProviderDTO("Albert", "Spain"))))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        mockMvc.perform(post("/providers").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new ProviderDTO("Joao", "Portugal"))))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/providers")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(notNullValue())).andExpect(jsonPath("$[0].name").value("Albert")).andExpect(jsonPath("$[0].country").value("Spain"))
                .andExpect(jsonPath("$[1].id").value(notNullValue())).andExpect(jsonPath("$[1].name").value("Joao")).andExpect(jsonPath("$[1].country").value("Portugal"));
    }
}
