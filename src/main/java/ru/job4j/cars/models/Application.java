package ru.job4j.cars.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * Класс объявления пользователя.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Поле содержит ссылку на аккаунт пользователя.
     * Таблица БД application использует внешний ключ account_id
     * для связи с таблицей account.
     */
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    /**
     * Поле содержит ссылку на машину, связанную с объявлением.
     * Таблица БД application использует внешний ключ car_id
     * для связи с таблицей car.
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "car_id")
    private Car car;

    /**
     * Поле содержит текст объявления.
     */
    @Column(name = "description")
    private String description;

    /**
     * Поле хранит имя файла фото, прикрепленного к объявлению.
     * Все фото хранятся в директории на сервере.
     */
    @Column(name = "photo")
    private String photo;

    /**
     * Поле содержит статус объявления (продано / не продано).
     * При создании объявления по умолчанию статус - не продано.
     */
    @Column(name = "sold")
    private boolean sold = false;

    /**
     * Поле содержит дату создания объявления.
     */
    @Column(name = "created")
    private Date created = new Date(System.currentTimeMillis());

    public Application(Account account, Car car, String description, String photo, boolean sold, Date created) {
        this.account = account;
        this.car = car;
        this.description = description;
        this.photo = photo;
        this.sold = sold;
        this.created = created;
    }

    public Application(Account account, Car car, String description, String photo) {
        this.account = account;
        this.car = car;
        this.description = description;
        this.photo = photo;
    }

    public Application() {
    }

    public Application(int id) {
        this.id = id;
    }

    public Application(int id, Account account, Car car, String description, String photo, boolean sold) {
        this.id = id;
        this.account = account;
        this.car = car;
        this.description = description;
        this.photo = photo;
        this.sold = sold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Application that = (Application) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
