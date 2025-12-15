package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.dto.ProviderDTO;
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
    public Provider createProvider(ProviderDTO providerDTO) {
        if (providerRepository.existsByName(providerDTO.getName())) {
            throw new RuntimeException("Provider '" + providerDTO.getName() + "' already exists");
        }

        Provider provider = new Provider(providerDTO.getName(), providerDTO.getCountry());
        return providerRepository.save(provider);
    }

    @Override
    public Provider updateProvider(Long id, ProviderDTO providerDTO) {
        Provider provider = providerRepository.findById(id).orElseThrow(() -> new RuntimeException("Provider " + id + " not found"));

        if (!provider.getName().equals(providerDTO.getName()) && providerRepository.existsByName(providerDTO.getName())) {
            throw new RuntimeException("Provider '" + providerDTO.getName() + "' already exists");
        }

        provider.setName(providerDTO.getName());
        provider.setCountry(providerDTO.getCountry());
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
