package ru.job4j.cars.validation;

import ru.job4j.cars.models.Account;
import ru.job4j.cars.models.Application;
import ru.job4j.cars.models.Car;
import ru.job4j.cars.models.json.CarInfo;
import ru.job4j.cars.models.json.FilterJson;
import ru.job4j.cars.persistence.DbStore;

import java.util.List;

/**
 * Класс для валидации данных.
 * @author Evgeniya Tsiurupa
 * @version 1.0
 * @since 28/08/2019
 */
public class ValidateService {
    /**
     * Ссылка на объект MemoryStore, в котором находится хранилище пользователей.
     */
    private final DbStore logic = DbStore.getInstance();
    private static final ValidateService INSTANCE = new ValidateService();
    private ValidateService() {
    }

    public static ValidateService getInstance() {
        return INSTANCE;
    }

    public void addOrUpdateAccount(Account account)throws ErrorException {
        this.logic.addOrUpdateAccount(account);
    }

    public void deleteAccount(Account account)throws ErrorException {
        this.logic.deleteAccount(account);
    }

    public void deleteApplication(Application application, Car car) throws ErrorException {
        this.logic.deleteApplication(application, car);
    }

    public List findAllAccounts() {
        return this.logic.findAllAccounts();
    }

    public List findAllApplications() {
        return this.logic.findAllApplications();
    }

    public List findAllApplicationsFiltered(FilterJson filter) {
        return this.logic.findAllApplicationsFiltered(filter);
    }

    public List findAllCars() {
        return this.logic.findAllCars();
    }

    public List findAllCarMakes() {
        return this.logic.findAllCarMakes();
    }

    public List findAllCarBodies() {
        return this.logic.findAllCarBodies();
    }

    public List findAllEngines() {
        return this.logic.findAllEngines();
    }

    public List findAllTransmissions() {
        return this.logic.findAllTransmissions();
    }

    public List findCarModelsByMake(int carMakeId) {
        return this.logic.findCarModelsByMake(carMakeId);
    }

    public Application findApplicationById(int applicationId) {
        return this.logic.findApplicationById(applicationId);
    }

    public Account findAccountByLogin(String login) throws ErrorException {
        return this.logic.findAccountByLogin(login);
    }

    public Car addOrUpdateCar(int carId, int carMakeId, int carModelId, int carBodyId, int engineId, int transmissionId,
                              CarInfo info) throws ErrorException {
        return this.logic.addOrUpdateCar(carId, carMakeId, carModelId, carBodyId, engineId, transmissionId, info);
    }

    public void addOrUpdateApplication(int applicationId, Car car, int accountId, String desc, String photo, boolean sold) throws ErrorException {
        this.logic.addOrUpdateApplication(applicationId, car, accountId, desc, photo, sold);
    }
 }
