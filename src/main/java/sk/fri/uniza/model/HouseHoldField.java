package sk.fri.uniza.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HouseHoldField {
    @JsonProperty("name")
    private String name;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("description")
    private String description;

    public HouseHoldField(String name, String unit, String description) {
        this.name = name;
        this.unit = unit;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", description='" + description + '\'';
    }
}
