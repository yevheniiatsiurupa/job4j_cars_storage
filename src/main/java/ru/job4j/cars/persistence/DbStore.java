package ru.job4j.cars.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.job4j.cars.models.*;
import ru.job4j.cars.models.json.CarInfo;
import ru.job4j.cars.models.json.FilterJson;
import ru.job4j.cars.validation.ErrorException;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Класс для работы с базой данных.
 * @author Evgeniya Tsiurupa
 * @version 1.0
 * @since 13/09/2019
 */
public class DbStore {
    private static final DbStore INSTANCE = new DbStore();
    private static SessionFactory factory;
    private DbStore() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public static DbStore getInstance() {
        return INSTANCE;
    }

    private <T> T doFunction(final Function<Session, T> command) {
        final Session session = factory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            T rsl = command.apply(session);
            tr.commit();
            return rsl;
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
        return null;
    }

    private void doVoid(final Consumer<Session> command) {
        final Session session = factory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            command.accept(session);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
                throw new ErrorException("  Возникла ошибка, операция не выполнена.");
            }
        } finally {
            session.close();
        }
    }


    public void addOrUpdateAccount(Account account) {
        this.doVoid(session -> session.saveOrUpdate(account));
    }

    public void addOrUpdateCarMake(CarMake carMake) {
        this.doVoid(session -> session.saveOrUpdate(carMake));
    }

    public void addOrUpdateCarModel(CarModel carModel) {
        this.doVoid(session -> session.saveOrUpdate(carModel));
    }

    public void addOrUpdateCarBody(CarBody carBody) {
        this.doVoid(session -> session.saveOrUpdate(carBody));
    }

    public void addOrUpdateEngine(Engine engine) {
        this.doVoid(session -> session.saveOrUpdate(engine));
    }

    public void addOrUpdateTransmission(Transmission tr) {
        this.doVoid(session -> session.saveOrUpdate(tr));
    }

    public void deleteAccount(Account account) {
        this.doVoid(session -> session.delete(account));
    }

    public void deleteApplication(Application application, Car car) {
        this.doVoid(session -> {
            session.delete(application);
            session.delete(car);
        });
    }

    public List findAllAccounts() {
        return this.doFunction(session -> session.createQuery("from Account").list());
    }

    public List findAllApplications() {
        return this.doFunction(session -> session.createQuery("from Application").list());
    }

    public List findAllCars() {
        return this.doFunction(session -> session.createQuery("from Car").list());
    }

    public List findAllCarMakes() {
        return this.doFunction(session -> session.createQuery("from CarMake").list());
    }

    public List findAllCarBodies() {
        return this.doFunction(session -> session.createQuery("from CarBody").list());
    }

    public List findAllEngines() {
        return this.doFunction(session -> session.createQuery("from Engine").list());
    }

    public List findAllTransmissions() {
        return this.doFunction(session -> session.createQuery("from Transmission").list());
    }

    public Account findAccountByLogin(String login) {
        String query = String.format("from Account A where A.login = '%s'", login);
        final Session session = factory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Account rsl = (Account) session.createQuery(query).getSingleResult();
            tr.commit();
            return rsl;
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
                throw new ErrorException("Неверный логин. Пользователь не найден.");
            }
        } finally {
            session.close();
        }
        return null;
    }

    public List findCarModelsByMake(int carMakeId) {
        CarMake carMake = new CarMake(carMakeId);
        return this.doFunction(session -> {
            Query query = session.createQuery("from CarModel C where C.carMake = :param");
            query.setParameter("param", carMake);
            return query.list();
        });
    }

    public Application findApplicationById(int applicationId) {
        String query = String.format("from Application A where A.id=%d", applicationId);
        return this.doFunction(session -> (Application) session.createQuery(query).getSingleResult());
    }

    public Car addOrUpdateCar(int carId, int carMakeId, int carModelId, int carBodyId, int engineId, int transmissionId,
                              CarInfo info) {
        int year = info.getYear();
        int price = info.getPrice();
        final Session session = factory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Car newCar = new Car(carId,
                    new CarMake(carMakeId),
                    new CarModel(carModelId),
                    new CarBody(carBodyId),
                    new Engine(engineId),
                    new Transmission(transmissionId));
            newCar.setYear(year);
            newCar.setPrice(price);
            session.saveOrUpdate(newCar);
            tr.commit();
            return newCar;
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
                throw new ErrorException("Ошибка при добавлении нового автомобиля.");
            }
        } finally {
            session.close();
        }
        return null;
    }

    public Application addOrUpdateApplication(int applicationId, Car car, int accountId, String desc, String photo, boolean sold) {
        final Session session = factory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Account account = new Account(accountId);
            Application application = new Application(applicationId, account, car, desc, photo, sold);
            session.saveOrUpdate(application);
            tr.commit();
            return application;
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
                throw new ErrorException("Ошибка при добавлении заявки.");
            }
        } finally {
            session.close();
        }
        return null;
    }

    /**
     * Метод для получения списка объявлений, которые
     * соответствуют фильтру.
     * Получает конечную часть строки для запроса (метод getFilterQuery).
     * Если фильтр содержит фильтр "Показать за сегодня" ("showToday"),
     * то для запроса устанавливается параметр.
     */
    public List findAllApplicationsFiltered(FilterJson filter) {
        String ending = this.getFilterQuery(filter);
        if (ending.isEmpty()) {
            return this.findAllApplications();
        } else {
            return this.doFunction(session -> {
                String q = String.format("from Application A where %s", ending);
                Query query = session.createQuery(q);
                if ("showToday".equals(filter.getFilterType())) {
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    Date today = new Date(cal.getTimeInMillis());
                    query.setParameter("param", today);
                }
                return query.list();
            });
        }
    }

    /**
     * Метод для получения конечной части запроса для
     * получения объявлений по фильтру.
     * Принимает объект FilterJson.
     * В зависимости от значений полей объекта составляет часть запроса,
     * отдельные части соединяются словом and.
     */
    private String getFilterQuery(FilterJson filter) {
        StringJoiner joiner = new StringJoiner(" and ");
        if (!(filter.getCarMake().isEmpty())) {
            String s1 = String.format("A.car.carMake.id = %s", filter.getCarMake());
            joiner.add(s1);
        }
        if (!(filter.getCarModel().isEmpty())) {
            String s1 = String.format("A.car.carModel.id = %s", filter.getCarModel());
            joiner.add(s1);
        }
        if (!(filter.getCarBody().isEmpty())) {
            String s1 = String.format("A.car.carBody.id = %s", filter.getCarBody());
            joiner.add(s1);
        }
        if (!(filter.getEngine().isEmpty())) {
            String s1 = String.format("A.car.engine.id = %s", filter.getEngine());
            joiner.add(s1);
        }
        if (!(filter.getTransmission().isEmpty())) {
            String s1 = String.format("A.car.transmission.id = %s", filter.getTransmission());
            joiner.add(s1);
        }
        if ("showToday".equals(filter.getFilterType())) {
            joiner.add("A.created >= :param");
        } else if ("showPhoto".equals(filter.getFilterType())) {
            joiner.add("A.photo is not null");
        }
        return joiner.toString();
    }
}
