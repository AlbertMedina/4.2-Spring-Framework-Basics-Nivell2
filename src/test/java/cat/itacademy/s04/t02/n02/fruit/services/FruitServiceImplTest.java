package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.dto.FruitDTO;
import cat.itacademy.s04.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.fruit.repository.ProviderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FruitServiceImplTest {

    @Mock
    private FruitRepository fruitRepository;

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private FruitServiceImpl fruitService;

    @Test
    void createFruit_shouldSaveFruit() {
        FruitDTO fruitDTO = new FruitDTO("Watermelon", 2, (Long) 1L);

        when(providerRepository.findById((Long) 1L)).thenReturn(Optional.of(new Provider("Albert", "Spain")));

        when(fruitRepository.save(any(Fruit.class))).thenAnswer(i -> i.getArgument(0));

        Fruit fruit = fruitService.createFruit(fruitDTO);

        assertNotNull(fruit);
        assertEquals("Watermelon", fruit.getName());
        assertEquals(2, fruit.getWeightInKg());

        verify(fruitRepository).save(any(Fruit.class));
    }

    @Test
    void updateFruit_shouldUpdateExistingFruit() {
        Provider provider = new Provider("Albert", "Spain");

        Fruit fruit = new Fruit("Watermelon", 2, provider);

        when(fruitRepository.findById((Long) 1L)).thenReturn(Optional.of(fruit));
        when(fruitRepository.save(any(Fruit.class))).thenAnswer(invocation -> invocation.getArgument(0));

        FruitDTO fruitDTO = new FruitDTO("Melon", 3, (Long) 1L);

        Fruit updated = fruitService.updateFruit((Long) 1L, fruitDTO);

        assertEquals("Melon", updated.getName());
        assertEquals(3, updated.getWeightInKg());

        verify(fruitRepository).findById((Long) 1L);
        verify(fruitRepository).save(any(Fruit.class));
    }

    @Test
    void removeFruit_shouldDeleteExistingFruit() {
        Long fruitId = (Long) 1L;

        when(fruitRepository.existsById(fruitId)).thenReturn((Boolean) true);
        doNothing().when(fruitRepository).deleteById(fruitId);

        assertDoesNotThrow(() -> fruitService.removeFruit(fruitId));

        verify(fruitRepository).existsById(fruitId);
        verify(fruitRepository).deleteById(fruitId);
    }

    @Test
    void getFruitById_shouldReturnFruit() {
        Provider provider = new Provider("Albert", "Spain");
        Fruit fruit = new Fruit("Watermelon", 2, provider);

        when(fruitRepository.findById((Long) 1L)).thenReturn(Optional.of(fruit));

        Fruit result = fruitService.getFruitById((Long) 1L);

        assertNotNull(result);
        assertEquals("Watermelon", result.getName());
        assertEquals(2, result.getWeightInKg());
        assertEquals(provider, result.getProvider());

        verify(fruitRepository).findById((Long) 1L);
    }

    @Test
    void getAllFruits_shouldReturnListOfFruits() {
        Provider provider1 = new Provider("Albert", "Spain");
        Provider provider2 = new Provider("Joao", "Portugal");

        Fruit f1 = new Fruit("Watermelon", 2, provider1);
        Fruit f2 = new Fruit("Melon", 3, provider2);

        when(fruitRepository.findAll()).thenReturn(Arrays.asList(f1, f2));

        List<Fruit> fruits = fruitService.getAllFruits();

        assertEquals(2, fruits.size());
        assertEquals("Watermelon", fruits.get(0).getName());
        assertEquals("Melon", fruits.get(1).getName());

        verify(fruitRepository).findAll();
    }
}