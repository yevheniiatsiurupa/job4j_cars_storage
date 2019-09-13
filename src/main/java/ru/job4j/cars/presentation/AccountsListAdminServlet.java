package ru.job4j.cars.presentation;

import ru.job4j.cars.validation.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Сервлет для страницы со списком всех пользователей (для администратора).
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class AccountsListAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List accounts = ValidateService.getInstance().findAllAccounts();
        req.setAttribute("accounts", accounts);
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/account-list.jsp").forward(req, resp);
    }
}
