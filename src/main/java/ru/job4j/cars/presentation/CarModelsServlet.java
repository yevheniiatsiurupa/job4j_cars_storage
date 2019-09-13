package ru.job4j.cars.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
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
 * Сервлет для получения json объекта со списком моделей,
 * соответствующих марке из запроса.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class CarModelsServlet extends HttpServlet {
    /**
     * Метод отвечает на запрос post.
     * Получает из запроса номер марки,
     * по марке получаем из БД список моделей,
     * помещаем его в объект json и сериализуем
     * с помощью mapper.
     * В качестве ответа на запрос возвращаем text/json.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader br = req.getReader();
        int carMakeId = Integer.parseInt(br.readLine());
        List models = ValidateService.getInstance().findCarModelsByMake(carMakeId);
        ListJson listJson = new ListJson(models);

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
}
