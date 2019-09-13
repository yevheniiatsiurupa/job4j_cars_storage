package ru.job4j.cars.presentation;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Фильтр для аутентификации пользователя.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class AuthentFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Фильтр проверяет URI запроса, запрос на определенные страницы
     * фильтр пропускает и происходит переход на указанные страницы.
     * Если в запросе идет переход на другие страницы, то проверяется залогинился ли
     * пользователь с помощью проверки атрибутов сессии. Если установлен атрибут
     * account, то пользователь уже ранее залогинился и можно производить переход на
     * нужную страницу.
     * Если у сессии нет атрибута account, то пользователя направляют на страницу /login.
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getRequestURI().contains("/login") || request.getRequestURI().contains("/account-create") || request.getRequestURI().contains("/start")
                || request.getRequestURI().contains("account-deletePost.jsp") || request.getRequestURI().contains("application-page")
                || request.getRequestURI().contains("/filter") || request.getRequestURI().contains("/car-models") || request.getRequestURI().contains("/photo")) {
            filterChain.doFilter(req, resp);
        } else {
            HttpSession session = request.getSession();
            if (session.getAttribute("account") == null) {
                ((HttpServletResponse) resp).sendRedirect(String.format("%s/login", request.getContextPath()));
                return;
            }
            filterChain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}
