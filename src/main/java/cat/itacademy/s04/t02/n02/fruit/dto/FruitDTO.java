package cat.itacademy.s04.t02.n02.fruit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class FruitDTO {

    @NotNull
    @NotBlank
    private String name;

    @Positive
    private int weightInKg;

    public FruitDTO() {
    }

    public FruitDTO(String name, int weightInKg) {
        this.name = name;
        this.weightInKg = weightInKg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeightInKg() {
        return weightInKg;
    }

    public void setWeightInKg(int weightInKg) {
        this.weightInKg = weightInKg;
    }
}
