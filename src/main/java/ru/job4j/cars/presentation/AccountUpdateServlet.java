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
 * Сервлет для редактирования аккаунта пользователя.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class AccountUpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/account-update.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String answer = this.update(req);
        req.setAttribute("answer", answer);
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/account-updatePost.jsp").forward(req, resp);
    }

    private String update(HttpServletRequest req) {
        Account oldAcc = (Account) req.getSession().getAttribute("account");
        oldAcc.setName(req.getParameter("name"));
        oldAcc.setLogin(req.getParameter("login"));
        oldAcc.setPassword(req.getParameter("password"));
        oldAcc.setEmail(req.getParameter("email"));
        oldAcc.setRole(req.getParameter("role"));
        String response;
        try {
            ValidateService.getInstance().addOrUpdateAccount(oldAcc);
            response = "Аккаунт успешно отредактирован.";
        } catch (ErrorException e) {
            response = e.getMessage();
        }
        return response;
    }
}
