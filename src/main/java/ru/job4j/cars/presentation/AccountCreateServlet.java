package ru.job4j.cars.presentation;

import ru.job4j.cars.models.Account;
import ru.job4j.cars.validation.ErrorException;
import ru.job4j.cars.validation.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет для добавления нового аккаунта.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */

public class AccountCreateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/account-create.jsp").forward(req, resp);
    }

    /**
     * Метод обрабатывает запрос post.
     * Добавляет нового пользователя.
     * Возвращает ответ в качестве атрибута.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String answer = this.create(req);
        req.setAttribute("answer", answer);
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/account-createPost.jsp").forward(req, resp);
    }

    private String create(HttpServletRequest req) {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");
        String response;
        Account account = new Account(name, login, password, email, null, role);
        try {
            ValidateService.getInstance().addOrUpdateAccount(account);
            response = "Аккаунт успешно добавлен.";
        } catch (ErrorException e) {
            response = e.getMessage();
        }
        return response;
    }
}
