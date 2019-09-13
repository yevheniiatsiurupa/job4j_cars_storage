package ru.job4j.cars.presentation;

import ru.job4j.cars.models.Application;
import ru.job4j.cars.validation.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
 * Сервлет для отображения фото по номеру объявления.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class PhotoServlet extends HttpServlet {
    /**
     * Метод отправляет фото с сервера в ответ на запрос.
     * Получает из запроса номер объявления, находит его в БД,
     * получает путь к фото.
     * Если в объявлении нет фото, то в ответе вернем специальное изображение (no-photo.JPG).
     * Если в объявлении есть фото, то возвращаем его.
     * Фото передаем как массив байтов.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("applId"));
        Application application = ValidateService.getInstance().findApplicationById(id);
        String photoName = application.getPhoto();
        String path = (String) getServletContext().getAttribute("fileBase");
        File file;
        if (photoName == null) {
            file = new File(path, "no-photo.JPG");
        } else {
            file = new File(path, photoName);
        }

        String contentType = getServletContext().getMimeType(file.getName());
        resp.setContentType(contentType);
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
             BufferedOutputStream out = new BufferedOutputStream(resp.getOutputStream())) {
            byte[] buffer = new byte[10240];
            int length = in.read(buffer);
            while (length > 0) {
                out.write(buffer, 0, length);
                length = in.read(buffer);
            }
        }
    }
}
