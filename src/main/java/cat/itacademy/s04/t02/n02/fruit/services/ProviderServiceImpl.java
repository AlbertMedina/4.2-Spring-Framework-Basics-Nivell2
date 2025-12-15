package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.fruit.repository.ProviderRepository;

import java.util.List;

public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;
    private final FruitRepository fruitRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository, FruitRepository fruitRepository) {
        this.providerRepository = providerRepository;
        this.fruitRepository = fruitRepository;
    }

    @Override
    public Provider createProvider(String name, String country) {
        if (providerRepository.existsByName(name)) {
            throw new RuntimeException("Provider '" + name + "' already exists");
        }

        Provider provider = new Provider(name, country);
        return providerRepository.save(provider);
    }

    @Override
    public Provider updateProvider(Long id, String name, String country) {
        Provider provider = providerRepository.findById(id).orElseThrow(() -> new RuntimeException("Provider " + id + " not found"));

        if (!provider.getName().equals(name) && providerRepository.existsByName(name)) {
            throw new RuntimeException("Provider '" + name + "' already exists");
        }

        provider.setName(name);
        provider.setCountry(country);
        return providerRepository.save(provider);
    }

    @Override
    public void removeProvider(Long id) {
        if (!providerRepository.existsById(id)) {
            throw new RuntimeException("Provider " + id + " not found");
        }

        if (fruitRepository.existsByProviderId(id)) {
            throw new RuntimeException("Provider " + id + " has associated fruits, so it cannot be removed");
        }

        providerRepository.deleteById(id);
    }

    @Override
    public Provider getProviderById(Long id) {
        return providerRepository.findById(id).orElseThrow(() -> new RuntimeException("Provider " + id + " not found"));
    }

    @Override
    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }
}
