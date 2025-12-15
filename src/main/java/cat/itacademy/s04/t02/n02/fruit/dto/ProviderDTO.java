package cat.itacademy.s04.t02.n02.fruit.dto;

import jakarta.validation.constraints.NotBlank;

public class ProviderDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String country;

    public ProviderDTO() {
    }

    public ProviderDTO(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}