package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.repository.FruitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;

    public FruitServiceImpl(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    @Override
    public Fruit createFruit(String name, int weightInKg) {
        Fruit fruit = new Fruit(name, weightInKg);
        return fruitRepository.save(fruit);
    }

    @Override
    public Fruit updateFruit(Long id, String name, int weightInKg) {
        Fruit fruit = fruitRepository.findById(id).orElseThrow(() -> new RuntimeException("Fruit " + id + "not found"));
        fruit.setName(name);
        fruit.setWeightInKg(weightInKg);
        return fruitRepository.save(fruit);
    }

    @Override
    public void removeFruit(Long id) {
        if (!fruitRepository.existsById(id)) {
            throw new RuntimeException("Fruit " + id + "not found");
        }
        fruitRepository.deleteById(id);
    }

    @Override
    public Fruit getFruitById(Long id) {
        return fruitRepository.findById(id).orElseThrow(() -> new RuntimeException("Fruit " + id + "not found"));
    }

    @Override
    public List<Fruit> getAllFruits() {
        return fruitRepository.findAll();
    }

    @Override
    public List<Fruit> getFruitsByProviderId(Long id) {
        return null;
    }
}
