package ru.job4j.cars.models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CarTest {
    private static SessionFactory factory = new Configuration().configure().buildSessionFactory();
    private Session session;

    @Before
    public void beforeTest() {
        this.session = factory.openSession();
        session.beginTransaction();
    }

    @After
    public void afterTest() {
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void whenAddUpdateDelete() {
        Car car = new Car("fourth car", new CarBody(1), new Engine(1), new Transmission(1));
        session.save(car);
        Car added = session.get(Car.class, car.getId());
        assertThat(car.getId() > 0, is(true));
        assertThat(added != null, is(true));
        assertThat(added.getName(), is("fourth car"));

        car.setName("fourth car updated");
        Car updated = session.get(Car.class, car.getId());
        assertThat(updated.getName(), is("fourth car updated"));

        session.delete(car);
        Car deleted = session.get(Car.class, car.getId());
        assertThat(deleted == null, is(true));
    }
}