package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.dto.ProviderDTO;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProviderServiceImplTest {

    @Mock
    private FruitRepository fruitRepository;

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderServiceImpl providerService;

    @Test
    void createProvider_shouldSaveProvider() {
        ProviderDTO providerDTO = new ProviderDTO("Albert", "Spain");

        when(providerRepository.save(any(Provider.class))).thenAnswer(i -> i.getArgument(0));

        Provider provider = providerService.createProvider(providerDTO);

        assertNotNull(provider);
        assertEquals("Albert", provider.getName());
        assertEquals("Spain", provider.getCountry());

        verify(providerRepository).save(any(Provider.class));
    }

    @Test
    void updateProvider_shouldUpdateExistingProvider() {
        Provider provider = new Provider("Albert", "Spain");

        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));
        when(providerRepository.save(any(Provider.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProviderDTO providerDTO = new ProviderDTO("Albert", "Spain");

        Provider updated = providerService.updateProvider(1L, providerDTO);

        assertEquals("Albert", updated.getName());
        assertEquals("Spain", updated.getCountry());

        verify(providerRepository).findById(1L);
        verify(providerRepository).save(any(Provider.class));
    }

    @Test
    void removeProvider_shouldDeleteExistingProvider() {
        when(providerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(providerRepository).deleteById(1L);

        assertDoesNotThrow(() -> providerService.removeProvider(1L));

        verify(providerRepository).existsById(1L);
        verify(providerRepository).deleteById(1L);
    }

    @Test
    void getProviderById_shouldReturnProvider() {
        Provider provider = new Provider("Albert", "Spain");

        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));

        Provider result = providerService.getProviderById(1L);

        assertNotNull(result);
        assertEquals("Albert", result.getName());
        assertEquals("Spain", result.getCountry());

        verify(providerRepository).findById(1L);
    }

    @Test
    void getAllProvider_shouldReturnListOfProvider() {
        Provider provider1 = new Provider("Albert", "Spain");
        Provider provider2 = new Provider("Joao", "Portugal");

        when(providerRepository.findAll()).thenReturn(Arrays.asList(provider1, provider2));

        List<Provider> providers = providerService.getAllProviders();

        assertEquals(2, providers.size());
        assertEquals("Albert", providers.get(0).getName());
        assertEquals("Joao", providers.get(1).getName());

        verify(providerRepository).findAll();
    }
}
