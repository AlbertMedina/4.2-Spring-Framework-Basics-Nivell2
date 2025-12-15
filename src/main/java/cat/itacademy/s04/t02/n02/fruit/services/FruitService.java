package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.dto.FruitDTO;
import cat.itacademy.s04.t02.n02.fruit.model.Fruit;

import java.util.List;

public interface FruitService {

    Fruit createFruit(FruitDTO fruitDTO);

    Fruit updateFruit(Long id, FruitDTO fruitDTO);

    void removeFruit(Long id);

    Fruit getFruitById(Long id);

    List<Fruit> getAllFruits();

    List<Fruit> getFruitsByProviderId(Long id);
}
