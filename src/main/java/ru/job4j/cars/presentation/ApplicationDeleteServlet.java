package ru.job4j.cars.presentation;

import ru.job4j.cars.models.Application;
import ru.job4j.cars.models.Car;
import ru.job4j.cars.validation.ErrorException;
import ru.job4j.cars.validation.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Сервлет для удаления объявления.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class ApplicationDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int applicationId = Integer.parseInt(req.getParameter("id"));
        Application application = ValidateService.getInstance().findApplicationById(applicationId);
        req.setAttribute("application", application);
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/application-delete.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String answer = this.delete(req, resp);
        req.setAttribute("answer", answer);
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/application-createPost.jsp").forward(req, resp);
    }

    /**
     * Метод для удаления объявления.
     * Удаляет заявку по полученному номеру.
     * После удаления объявления удаляет также
     * соответствующее фото из сервера.
     */
    private String delete(HttpServletRequest req, HttpServletResponse resp) {
        int applicationId = Integer.parseInt(req.getParameter("applicationId"));

        Application application = (Application) ValidateService.getInstance().findApplicationById(applicationId);
        Car car = application.getCar();
        String oldPhoto = application.getPhoto();
        String path = (String) getServletContext().getAttribute("fileBase");

        String response;
        try {
            ValidateService.getInstance().deleteApplication(application, car);
            response = "Объявление успешно удалено.";
            if (oldPhoto != null) {
                File photo = new File(path, oldPhoto);
                photo.delete();
            }
        } catch (ErrorException e) {
            response = e.getMessage();
        }
        return response;
    }
}
