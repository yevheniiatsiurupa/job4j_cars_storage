package ru.job4j.cars.models;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс машины, связанной с объявлением.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Марка машины.
     * Связь с таблицей car_make по внешнему ключу car_make_id.
     */
    @ManyToOne
    @JoinColumn(name = "car_make_id")
    private CarMake carMake;

    /**
     * Модель машины.
     * Для марок есть соответствующие модели машин.
     * Связь с таблицей car_model по внешнему ключу car_model_id.
     */
    @ManyToOne
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;

    /**
     * Тип кузова машины.
     * Связь с таблицей car_body по внешнему ключу car_body_id.
     */
    @ManyToOne
    @JoinColumn(name = "car_body_id")
    private CarBody carBody;

    /**
     * Тип двигателя.
     * Связь с таблицей engine по внешнему ключу engine_id.
     */
    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;

    /**
     * Тип коробки передач.
     * Связь с таблицей transmission по внешнему ключу transmission_id.
     */
    @ManyToOne
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;

    /**
     * Год выпуска машины.
     */
    @Column(name = "year")
    private int year;

    /**
     * Цена машины.
     */
    @Column(name = "price")
    private int price;



    public Car(int id) {
        this.id = id;
    }

    public Car() {
    }

    public Car(CarMake carMake, CarModel carModel, CarBody carBody, Engine engine, Transmission transmission, int year, int price) {
        this.carMake = carMake;
        this.carModel = carModel;
        this.carBody = carBody;
        this.engine = engine;
        this.transmission = transmission;
        this.year = year;
        this.price = price;
    }

    public Car(int id, CarMake carMake, CarModel carModel, CarBody carBody, Engine engine, Transmission transmission) {
        this.id = id;
        this.carMake = carMake;
        this.carModel = carModel;
        this.carBody = carBody;
        this.engine = engine;
        this.transmission = transmission;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CarMake getCarMake() {
        return carMake;
    }

    public void setCarMake(CarMake carMake) {
        this.carMake = carMake;
    }

    public CarBody getCarBody() {
        return carBody;
    }

    public void setCarBody(CarBody carBody) {
        this.carBody = carBody;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return id == car.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
