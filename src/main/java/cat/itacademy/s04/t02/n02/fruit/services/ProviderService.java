package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.dto.ProviderDTO;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;

import java.util.List;

public interface ProviderService {

    Provider createProvider(ProviderDTO providerDTO);

    Provider updateProvider(Long id, ProviderDTO providerDTO);

    void removeProvider(Long id);

    Provider getProviderById(Long id);

    List<Provider> getAllProviders();
}
