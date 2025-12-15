package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.repository.FruitRepository;
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

    @InjectMocks
    private FruitServiceImpl fruitService;

    @Test
    void createFruit_shouldSaveFruit() {
        String name = "Watermelon";
        int weightInKg = 2;

        when(fruitRepository.save(any(Fruit.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Fruit fruit = fruitService.createFruit(name, weightInKg);

        assertNotNull(fruit);
        assertEquals(name, fruit.getName());
        assertEquals(weightInKg, fruit.getWeightInKg());

        verify(fruitRepository).save(any(Fruit.class));
    }

    @Test
    void updateFruit_shouldUpdateExistingFruit() {
        Fruit fruit = new Fruit("Watermelon", 2);

        when(fruitRepository.findById(1L)).thenReturn(Optional.of(fruit));
        when(fruitRepository.save(any(Fruit.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Fruit updated = fruitService.updateFruit(1L, "Melon", 3);

        assertEquals("Melon", updated.getName());
        assertEquals(3, updated.getWeightInKg());

        verify(fruitRepository).findById(1L);
        verify(fruitRepository).save(any(Fruit.class));
    }

    @Test
    void removeFruit_shouldDeleteExistingFruit() {
        when(fruitRepository.existsById(1L)).thenReturn(true);
        doNothing().when(fruitRepository).deleteById(1L);

        assertDoesNotThrow(() -> fruitService.removeFruit(1L));

        verify(fruitRepository).existsById(1L);
        verify(fruitRepository).deleteById(1L);
    }

    @Test
    void getFruitById_shouldReturnFruit() {
        Fruit fruit = new Fruit("Watermelon", 2);

        when(fruitRepository.findById(1L)).thenReturn(Optional.of(fruit));

        Fruit result = fruitService.getFruitById(1L);

        assertNotNull(result);
        assertEquals("Watermelon", result.getName());
        assertEquals(2, result.getWeightInKg());

        verify(fruitRepository).findById(1L);
    }

    @Test
    void getAllFruits_shouldReturnListOfFruits() {
        Fruit f1 = new Fruit("Watermelon", 2);
        Fruit f2 = new Fruit("Melon", 3);

        when(fruitRepository.findAll()).thenReturn(Arrays.asList(f1, f2));

        List<Fruit> fruits = fruitService.getAllFruits();

        assertEquals(2, fruits.size());
        assertEquals("Watermelon", fruits.get(0).getName());
        assertEquals("Melon", fruits.get(1).getName());

        verify(fruitRepository).findAll();
    }
}