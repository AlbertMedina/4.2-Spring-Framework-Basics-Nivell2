package cat.itacademy.s04.t02.n02.fruit.model;

import jakarta.persistence.*;

@Entity
public class Fruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int weightInKg;
    
    public Fruit() {
    }

    public Fruit(String name, int weightInKg) {
        this.name = name;
        this.weightInKg = weightInKg;
    }

    public Long getId() {
        return id;
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
