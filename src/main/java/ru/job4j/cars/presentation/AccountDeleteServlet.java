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
 * Сервлет для удаления аккаунта.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class AccountDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/account-delete.jsp").forward(req, resp);
    }

    /**
     * Метод обрабатывает запрос post.
     * Удаляет пользователя.
     * Возвращает ответ в качестве атрибута.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String answer = this.delete(req);
        req.setAttribute("answer", answer);
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/account-deletePost.jsp").forward(req, resp);
    }

    private String delete(HttpServletRequest req) {
        Account oldAcc = (Account) req.getSession().getAttribute("account");
        String response;
        try {
            ValidateService.getInstance().deleteAccount(oldAcc);
            req.getSession().removeAttribute("account");
            response = "Аккаунт успешно удален.";
        } catch (ErrorException e) {
            response = e.getMessage();
        }
        return response;
    }
}
