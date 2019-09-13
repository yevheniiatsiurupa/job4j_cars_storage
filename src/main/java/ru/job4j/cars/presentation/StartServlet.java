package ru.job4j.cars.presentation;

import ru.job4j.cars.validation.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Сервлет для отображения основной страницы со всеми объявлениями.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class StartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ValidateService validator = ValidateService.getInstance();
        req.setAttribute("carMakes", validator.findAllCarMakes());
        req.setAttribute("carBodies", validator.findAllCarBodies());
        req.setAttribute("engines", validator.findAllEngines());
        req.setAttribute("transmissions", validator.findAllTransmissions());
        resp.setContentType("text/html");
        List list = ValidateService.getInstance().findAllApplications();
        req.setAttribute("applications", list);
        req.getRequestDispatcher("/WEB-INF/views/start.jsp").forward(req, resp);
    }

}
