package cat.itacademy.s04.t02.n02.fruit.controllers;

import cat.itacademy.s04.t02.n02.fruit.dto.FruitDTO;
import cat.itacademy.s04.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.services.FruitServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FruitController {

    private final FruitServiceImpl fruitService;

    public FruitController(FruitServiceImpl fruitService) {
        this.fruitService = fruitService;
    }

    @PostMapping("/fruits")
    public ResponseEntity<Fruit> addFruit(@RequestBody @Valid FruitDTO fruitDTORequest) {
        Fruit fruit = fruitService.createFruit(fruitDTORequest.getName(), fruitDTORequest.getWeightInKg());
        return ResponseEntity.status(HttpStatus.CREATED).body(fruit);
    }

    @PutMapping("/fruits/{id}")
    public ResponseEntity<Fruit> updateFruit(@PathVariable Long id, @RequestBody @Valid FruitDTO fruitDTORequest) {
        Fruit fruit = fruitService.updateFruit(id, fruitDTORequest.getName(), fruitDTORequest.getWeightInKg());
        return ResponseEntity.ok(fruit);
    }

    @DeleteMapping("/fruits/{id}")
    public ResponseEntity<Void> removeFruit(@PathVariable Long id) {
        fruitService.removeFruit(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fruits/{id}")
    public ResponseEntity<Fruit> getFruitById(@PathVariable Long id) {
        Fruit fruit = fruitService.getFruitById(id);
        return ResponseEntity.ok(fruit);
    }

    @GetMapping("/fruits")
    public ResponseEntity<List<Fruit>> getAllFruits() {
        List<Fruit> fruits = fruitService.getAllFruits();
        return ResponseEntity.ok(fruits);
    }
}
