package ru.job4j.cars.presentation;

import ru.job4j.cars.validation.ValidateService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Слушатель контекста.
 * При инициализации контекста добавляет набор атрибутов.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class CarsServletContextListener implements ServletContextListener {
    /**
     * Метод добавляет атрибуты контекста после его инициализации.
     * Атрибуты используются для выпадающих списков на страницах приложения,
     * атрибут fileBase хранит путь к директории, в которой хранятся
     * фото объявлений.
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext sc = servletContextEvent.getServletContext();
        String fileBase = "C:\\projects\\job4j_cars_storage\\src\\main\\resources\\images";
        ValidateService validator = ValidateService.getInstance();
        sc.setAttribute("carMakes", validator.findAllCarMakes());
        sc.setAttribute("carBodies", validator.findAllCarBodies());
        sc.setAttribute("engines", validator.findAllEngines());
        sc.setAttribute("transmissions", validator.findAllTransmissions());
        sc.setAttribute("fileBase", fileBase);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
