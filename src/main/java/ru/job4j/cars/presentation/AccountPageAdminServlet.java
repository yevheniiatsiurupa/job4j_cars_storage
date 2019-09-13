package ru.job4j.cars.presentation;

import ru.job4j.cars.models.Account;
import ru.job4j.cars.validation.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет для страницы отдельного пользователя со стороны администратора.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class AccountPageAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        Account current = (Account) ValidateService.getInstance().findAccountByLogin(login);
        int applSize = current.getApplications().size();
        req.setAttribute("account", current);
        req.setAttribute("applSize", applSize);
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/account-page-admin.jsp").forward(req, resp);
    }
}
