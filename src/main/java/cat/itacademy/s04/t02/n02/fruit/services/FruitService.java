package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.model.Fruit;

import java.util.List;

public interface FruitService {

    Fruit createFruit(String name, int weightInKg);

    Fruit updateFruit(Long id, String name, int weightInKg);

    void removeFruit(Long id);

    Fruit getFruitById(Long id);

    List<Fruit> getAllFruits();
}
