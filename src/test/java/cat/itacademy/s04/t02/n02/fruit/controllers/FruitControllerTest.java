package cat.itacademy.s04.t02.n02.fruit.controllers;

import cat.itacademy.s04.t02.n02.fruit.dto.FruitDTO;
import cat.itacademy.s04.t02.n02.fruit.model.Fruit;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class FruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private FruitRepository fruitRepository;

    private Long providerId;

    @BeforeEach
    void cleanDB() {
        fruitRepository.deleteAll();
        providerRepository.deleteAll();
        Provider provider = providerRepository.save(new Provider("Albert", "Spain"));
        providerId = provider.getId();
    }

    @Test
    void getFruits_shouldReturnEmptyListInitially() throws Exception {
        mockMvc.perform(get("/fruits")).andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    @Test
    void createFruit_shouldReturnFruitWithId() throws Exception {
        mockMvc.perform(post("/fruits").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FruitDTO("Watermelon", 2, providerId))))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(notNullValue())).andExpect(jsonPath("$.name").value("Watermelon")).andExpect(jsonPath("$.weightInKg").value(2));
    }

    @Test
    void getFruitById_shouldReturnCorrectFruit() throws Exception {
        String response = mockMvc.perform(post("/fruits").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FruitDTO("Watermelon", 2, providerId))))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Fruit fruit = objectMapper.readValue(response, Fruit.class);

        mockMvc.perform(get("/fruits/{id}", fruit.getId())).andExpect(jsonPath("$.id").value(notNullValue())).andExpect(jsonPath("$.name").value("Watermelon")).andExpect(jsonPath("$.weightInKg").value(2));
    }

    @Test
    void updateFruit_shouldUpdateExistingFruit() throws Exception {
        String response = mockMvc.perform(post("/fruits").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FruitDTO("Watermelon", 2, providerId))))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Fruit fruit = objectMapper.readValue(response, Fruit.class);

        mockMvc.perform(put("/fruits/{id}", fruit.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FruitDTO("Melon", 3, providerId))))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(notNullValue())).andExpect(jsonPath("$.name").value("Melon")).andExpect(jsonPath("$.weightInKg").value(3));
    }

    @Test
    void removeFruit_shouldRemoveFruitAndReturnNoContent() throws Exception {
        String response = mockMvc.perform(post("/fruits").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FruitDTO("Watermelon", 2, providerId))))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Fruit fruit = objectMapper.readValue(response, Fruit.class);

        mockMvc.perform(delete("/fruits/{id}", fruit.getId())).andExpect(status().isNoContent());

        mockMvc.perform(get("/fruits/{id}", fruit.getId())).andExpect(status().isNotFound());
    }

    @Test
    void getAllFruits_shouldReturnAllExistingFruits() throws Exception {
        mockMvc.perform(post("/fruits").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FruitDTO("Watermelon", 2, providerId))))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        mockMvc.perform(post("/fruits").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FruitDTO("Melon", 3, providerId))))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/fruits")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(notNullValue())).andExpect(jsonPath("$[0].name").value("Watermelon")).andExpect(jsonPath("$[0].weightInKg").value(2))
                .andExpect(jsonPath("$[1].id").value(notNullValue())).andExpect(jsonPath("$[1].name").value("Melon")).andExpect(jsonPath("$[1].weightInKg").value(3));

    }
}
