package main.domain.tag.model;

public class TagDTO {
    private String name;
    private double weight;

    public TagDTO(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }
}
