package ru.job4j.cars.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Класс определяет марку машины.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
@Entity
@Table(name = "car_make")
public class CarMake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    /**
     * Поле содержит список моделей для текущей марки машины.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "carMake", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarModel> models;

    public CarMake(int id) {
        this.id = id;
    }

    public CarMake() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CarModel> getModels() {
        return models;
    }

    public void setModels(List<CarModel> models) {
        this.models = models;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarMake carMake = (CarMake) o;
        return id == carMake.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
