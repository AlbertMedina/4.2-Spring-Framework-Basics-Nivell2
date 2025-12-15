package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.dto.FruitDTO;
import cat.itacademy.s04.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.fruit.repository.ProviderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;
    private final ProviderRepository providerRepository;

    public FruitServiceImpl(FruitRepository fruitRepository, ProviderRepository providerRepository) {
        this.fruitRepository = fruitRepository;
        this.providerRepository = providerRepository;
    }

    @Override
    public Fruit createFruit(FruitDTO fruitDTO) {
        Provider provider = providerRepository.findById(fruitDTO.getProviderId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider " + fruitDTO.getProviderId() + " not found"));


        Fruit fruit = new Fruit(fruitDTO.getName(), fruitDTO.getWeightInKg(), provider);
        return fruitRepository.save(fruit);
    }

    @Override
    public Fruit updateFruit(Long id, FruitDTO fruitDTO) {
        Fruit fruit = fruitRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fruit " + id + " not found"));
        fruit.setName(fruitDTO.getName());
        fruit.setWeightInKg(fruitDTO.getWeightInKg());

        if (fruitDTO.getProviderId() != null && !fruitDTO.getProviderId().equals(fruit.getProvider().getId())) {
            Provider newProvider = providerRepository.findById(fruitDTO.getProviderId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider " + fruitDTO.getProviderId() + " not found"));
            fruit.setProvider(newProvider);
        }

        return fruitRepository.save(fruit);
    }

    @Override
    public void removeFruit(Long id) {
        if (!fruitRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fruit " + id + " not found");
        }
        fruitRepository.deleteById(id);
    }

    @Override
    public Fruit getFruitById(Long id) {
        return fruitRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fruit " + id + " not found"));
    }

    @Override
    public List<Fruit> getAllFruits() {
        return fruitRepository.findAll();
    }

    @Override
    public List<Fruit> getFruitsByProviderId(Long id) {
        if (!providerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider " + id + " not found");
        }

        return fruitRepository.findByProviderId(id);
    }
}
