package ru.job4j.cars.models.json;

/**
 * Вспомогательный класс (для уменьшения количества параметров в методах
 * для класса Car).
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class CarInfo {
    private int year;
    private int price;

    public CarInfo(int year, int price) {
        this.year = year;
        this.price = price;
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
}
