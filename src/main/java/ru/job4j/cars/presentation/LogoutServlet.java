package ru.job4j.cars.presentation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Сервлет для выхода из аккаунта.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class LogoutServlet extends HttpServlet {
    /**
     * Метод для log out пользователя.
     * Удаляет атрибут account и переходит на страницу для аутентификации.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("account");
        resp.sendRedirect(String.format("%s/start", req.getContextPath()));
    }
}
