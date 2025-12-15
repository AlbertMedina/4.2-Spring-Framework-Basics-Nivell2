package cat.itacademy.s04.t02.n02.fruit.controllers;

import cat.itacademy.s04.t02.n02.fruit.dto.ProviderDTO;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.services.ProviderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping("/providers")
    public ResponseEntity<Provider> addProvider(@RequestBody @Valid ProviderDTO providerDTORequest) {
        Provider provider = providerService.createProvider(providerDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(provider);
    }

    @PutMapping("/providers/{id}")
    public ResponseEntity<Provider> updateProvider(@PathVariable Long id, @RequestBody @Valid ProviderDTO providerDTORequest) {
        Provider provider = providerService.updateProvider(id, providerDTORequest);
        return ResponseEntity.ok(provider);
    }

    @DeleteMapping("/providers/{id}")
    public ResponseEntity<Void> removeProvider(@PathVariable Long id) {
        providerService.removeProvider(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/providers/{id}")
    public ResponseEntity<Provider> getProviderById(@PathVariable Long id) {
        Provider provider = providerService.getProviderById(id);
        return ResponseEntity.ok(provider);
    }

    @GetMapping("/providers")
    public ResponseEntity<List<Provider>> getAllProviders() {
        List<Provider> providers = providerService.getAllProviders();
        return ResponseEntity.ok(providers);
    }
}
