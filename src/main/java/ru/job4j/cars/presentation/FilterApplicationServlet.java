package ru.job4j.cars.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.cars.models.json.FilterJson;
import ru.job4j.cars.models.json.ListJson;
import ru.job4j.cars.validation.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Сервлет для фильтрации объявлений на странице.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class FilterApplicationServlet extends HttpServlet {
    /**
     * Метод отвечает на запрос post.
     * Получает из запроса объект FilterJson,
     * по нему получает выборку объявлений из БД,
     * которые соответствуют фильтру,
     * добавляет список объявлений в объект ListJson,
     * сериализует его с помощью mapper и отправляет в ответе.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterJson filterJson = this.getFilter(req, resp);
        List list = ValidateService.getInstance().findAllApplicationsFiltered(filterJson);
        ListJson listJson = new ListJson(list);

        ObjectMapper mapper = new ObjectMapper();
        StringWriter sw = new StringWriter();
        mapper.writeValue(sw, listJson);
        String json = sw.toString();

        resp.setContentType("text/json");
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                resp.getOutputStream(), StandardCharsets.UTF_8));
        pw.append(json);
        pw.flush();
    }

    /**
     * Метод считывает из запроса данные формы и возвращает объект
     * FilterJson с данными.
     */
    private FilterJson getFilter(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        while (reader.ready()) {
            sb.append(reader.readLine());
        }
        String content = sb.toString();
        StringReader stringReader = new StringReader(content);

        ObjectMapper mapper = new ObjectMapper();
        FilterJson filterJson = mapper.readValue(stringReader, FilterJson.class);
        return filterJson;
    }
}
