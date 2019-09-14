package ru.job4j.cars.persistence;

import org.junit.Test;
import ru.job4j.cars.models.*;
import ru.job4j.cars.models.json.CarInfo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DbStoreTest {
    private final DbStore dbStore = DbStore.getInstance();

    @Test
    public void whenAddAccount() {
        Account account = new Account();
        account.setLogin("login one");
        this.dbStore.addOrUpdateAccount(account);
        assertThat(account, is(this.dbStore.findAccountByLogin(account.getLogin())));
    }

    @Test
    public void whenUpdateAccount() {
        Account account = new Account();
        account.setLogin("login two");
        this.dbStore.addOrUpdateAccount(account);
        Account update = new Account(account.getId());
        update.setLogin(account.getLogin());
        update.setEmail("updated email");
        this.dbStore.addOrUpdateAccount(update);
        assertThat(this.dbStore.findAccountByLogin(account.getLogin()).getEmail(), is("updated email"));
    }

    @Test
    public void whenDeleteAccount() {
        int sizeInit = this.dbStore.findAllAccounts().size();
        Account account = new Account();
        account.setLogin("login three");
        this.dbStore.addOrUpdateAccount(account);
        this.dbStore.deleteAccount(account);
        int sizeRes = this.dbStore.findAllAccounts().size();
        assertThat(sizeRes - sizeInit, is(0));
    }

    @Test
    public void whenFindAllAccount() {
        int sizeInit = this.dbStore.findAllAccounts().size();
        Account account = new Account();
        account.setLogin("login four");
        this.dbStore.addOrUpdateAccount(account);
        int sizeNew = this.dbStore.findAllAccounts().size();
        assertThat(sizeNew - sizeInit, is(1));
    }

    @Test
    public void whenAddCar() {
        CarMake carMake = new CarMake();
        this.dbStore.addOrUpdateCarMake(carMake);
        CarModel carModel = new CarModel();
        carModel.setCarMake(carMake);
        this.dbStore.addOrUpdateCarModel(new CarModel());
        this.dbStore.addOrUpdateCarBody(new CarBody());
        this.dbStore.addOrUpdateEngine(new Engine());
        this.dbStore.addOrUpdateTransmission(new Transmission());
        Car car = this.dbStore.addOrUpdateCar(0, 1, 1, 1, 1, 1,
                new CarInfo(2000, 100000));
        assertThat(car.getId() > 0, is(true));
    }

    @Test
    public void whenAddApplication() {
        Account account = new Account();
        account.setLogin("login five");
        this.dbStore.addOrUpdateAccount(account);
        CarMake carMake = new CarMake();
        this.dbStore.addOrUpdateCarMake(carMake);
        CarModel carModel = new CarModel();
        carModel.setCarMake(carMake);
        this.dbStore.addOrUpdateCarModel(new CarModel());
        this.dbStore.addOrUpdateCarBody(new CarBody());
        this.dbStore.addOrUpdateEngine(new Engine());
        this.dbStore.addOrUpdateTransmission(new Transmission());
        Car car = this.dbStore.addOrUpdateCar(0, 1, 1, 1, 1, 1,
                new CarInfo(2000, 100000));
        Application application = this.dbStore.addOrUpdateApplication(0, car, account.getId(), "desc",
                "photo", false);
        assertThat(application.getId() > 0, is(true));
    }

    @Test
    public void whenDeleteApplication() {
        int sizeInit = this.dbStore.findAllApplications().size();
        Account account = new Account();
        account.setLogin("login six");
        this.dbStore.addOrUpdateAccount(account);
        CarMake carMake = new CarMake();
        this.dbStore.addOrUpdateCarMake(carMake);
        CarModel carModel = new CarModel();
        carModel.setCarMake(carMake);
        this.dbStore.addOrUpdateCarModel(new CarModel());
        this.dbStore.addOrUpdateCarBody(new CarBody());
        this.dbStore.addOrUpdateEngine(new Engine());
        this.dbStore.addOrUpdateTransmission(new Transmission());
        Car car = this.dbStore.addOrUpdateCar(0, 1, 1, 1, 1, 1,
                new CarInfo(2000, 100000));
        Application application = this.dbStore.addOrUpdateApplication(0, car, account.getId(), "desc",
                "photo", false);
        this.dbStore.deleteApplication(application, car);
        int sizeRes = this.dbStore.findAllApplications().size();
        assertThat(sizeRes - sizeInit, is(0));
    }
}