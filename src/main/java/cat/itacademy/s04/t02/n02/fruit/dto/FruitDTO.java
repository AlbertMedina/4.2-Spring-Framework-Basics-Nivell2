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

    @NotNull
    private Long providerId;

    public FruitDTO() {
    }

    public FruitDTO(String name, int weightInKg, Long providerId) {
        this.name = name;
        this.weightInKg = weightInKg;
        this.providerId = providerId;
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

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }
}
