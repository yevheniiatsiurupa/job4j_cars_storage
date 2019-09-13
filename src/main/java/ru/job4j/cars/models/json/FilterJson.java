package ru.job4j.cars.models.json;

/**
 * Класс для работы с фильтрами на основной странице приложения.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class FilterJson {
    private String carMake;
    private String carModel;
    private String carBody;
    private String engine;
    private String transmission;

    /**
     * Поле содержит тип фильтра:
     * "showAll" - для "Показать все",
     * "showPhoto" - для "Показать с фото",
     * "showToday" - для "Показать за сегодня".
     */
    private String filterType;

    public FilterJson(String carMake, String carModel, String carBody, String engine, String transmission, String filterType) {
        this.carMake = carMake;
        this.carModel = carModel;
        this.carBody = carBody;
        this.engine = engine;
        this.transmission = transmission;
        this.filterType = filterType;
    }

    public FilterJson() {
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarBody() {
        return carBody;
    }

    public void setCarBody(String carBody) {
        this.carBody = carBody;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }
}
