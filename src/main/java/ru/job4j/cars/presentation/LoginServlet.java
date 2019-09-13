package ru.job4j.cars.presentation;

import ru.job4j.cars.models.Account;
import ru.job4j.cars.validation.ErrorException;
import ru.job4j.cars.validation.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Сервлет для входа в аккаунт.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    /**
     * Метод получает параметры login, password из запроса.
     * По полученному логину находится соответствующий пользователь и проверяется
     * пароль, если все верно, то сессии устанавливается атрибут account.
     * Если пользователь не найден по логину или пароль неверный,
     * то устанавливается атрибут error для запроса и вызывается снова метод doGet.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            Account search = ValidateService.getInstance().findAccountByLogin(login);
            if (password.equals(search.getPassword())) {
                HttpSession session = req.getSession();
                session.setAttribute("account", search);
                resp.sendRedirect(String.format("%s/start", req.getContextPath()));
            } else {
                req.setAttribute("error", "Password is incorrect.");
                doGet(req, resp);
            }
        } catch (ErrorException e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
    }
}
