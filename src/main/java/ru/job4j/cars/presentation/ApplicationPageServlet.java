package ru.job4j.cars.presentation;

import ru.job4j.cars.models.Application;
import ru.job4j.cars.validation.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApplicationPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int applicationId = Integer.parseInt(req.getParameter("applicationId"));
        Application application = ValidateService.getInstance().findApplicationById(applicationId);
        req.setAttribute("application", application);
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/application-page.jsp").forward(req, resp);
    }
}
