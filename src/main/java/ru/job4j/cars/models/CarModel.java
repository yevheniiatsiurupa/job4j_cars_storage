package ru.job4j.cars.models;

import javax.persistence.*;

/**
 * Класс определяет модель машины.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
@Entity
@Table(name = "car_model")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    /**
     * Модель машины.
     * Связь с таблицей car_make по внешнему ключу car_make_id.
     */
    @ManyToOne
    @JoinColumn(name = "car_make_id")
    private CarMake carMake;

    public CarModel(int id) {
        this.id = id;
    }

    public CarModel() {
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

    public CarMake getCarMake() {
        return carMake;
    }

    public void setCarMake(CarMake carMake) {
        this.carMake = carMake;
    }
}
