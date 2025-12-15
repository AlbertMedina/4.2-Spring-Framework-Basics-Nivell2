package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.model.Provider;

import java.util.List;

public interface ProviderService {

    Provider createProvider(String name, String country);

    Provider updateProvider(Long id, String name, String country);

    void removeProvider(Long id);

    Provider getProviderById(Long id);

    List<Provider> getAllProviders();
}
