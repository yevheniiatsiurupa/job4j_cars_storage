package ru.job4j.cars.presentation;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.cars.models.Account;
import ru.job4j.cars.models.Car;
import ru.job4j.cars.models.json.CarInfo;
import ru.job4j.cars.validation.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Сервлет для создания нового объявления.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class ApplicationCreateServlet extends HttpServlet {
    /**
     * Поле хранит максимальный размер прикрепляемого фото (10 кБ).
     */
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 10;

    /**
     * Поле хранит объект Random для получения случайного числа.
     */
    private static final Random RN = new Random();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/application-create.jsp").forward(req, resp);
    }

    /**
     * Метод обрабатывает запрос post.
     * Добавляет нового пользователя.
     * Прикрепляет answer в качестве атрибута запроса.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String answer = this.addToDb(req);
        req.setAttribute("answer", answer);
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/application-createPost.jsp").forward(req, resp);
    }

    /**
     * Метод для генерации случайного числа.
     */
    private String generateId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt());
    }

    /**
     * Метод для добавления объявления.
     * Вызывает метод readForm, который возращает данные формы,
     * заполненной пользователем, в виде карты.
     * Из карты получаем параметры, которые используются для создания новой машины,
     * а затем и объявления.
     */
    private String addToDb(HttpServletRequest req) throws UnsupportedEncodingException {
        String response;
        try {
            Map<String, String> map = this.readForm(req);

            int carMakeId = Integer.parseInt(map.get("carMake"));
            int carModelId = Integer.parseInt(map.get("carModel"));
            int carBodyId = Integer.parseInt(map.get("carBody"));
            int engineId = Integer.parseInt(map.get("engine"));
            int transmissionId = Integer.parseInt(map.get("transmission"));
            int year = Integer.parseInt(map.get("year"));
            int price = Integer.parseInt(map.get("price"));
            Account account = (Account) req.getSession().getAttribute("account");
            int accountId = account.getId();
            String desc = map.get("desc");
            String photo = map.get("photo");

            CarInfo info = new CarInfo(year, price);
            Car car = ValidateService.getInstance().addOrUpdateCar(0, carMakeId, carModelId, carBodyId, engineId, transmissionId, info);
            ValidateService.getInstance().addOrUpdateApplication(0, car, accountId, desc, photo, false);
            response = "Объявление успешно добавлено.";
        } catch (Exception e) {
            response = e.getMessage();
        }
        return response;
    }

    /**
     * Метод считывает данные из формы, записывает их в карту
     * в виде Имя поля формы - Значение пользователя,
     * загружает файл из формы на сервер.
     * Имя загружаемого файла имеет вид: photo + случаное число.расширение.
     */
    private Map<String, String> readForm(HttpServletRequest req) throws Exception {
        Map<String, String> map = new HashMap<>();
        String path = (String) getServletContext().getAttribute("fileBase");

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);

        File uploadDir = new File(path);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        List<FileItem> formItems = upload.parseRequest(req);
        if (formItems != null && formItems.size() > 0) {
            for (FileItem item : formItems) {
                if (!item.isFormField()) {
                    String prevName = item.getName();
                    if (!prevName.isEmpty()) {
                        String[] arr = prevName.split("\\.");
                        String ext = arr[arr.length - 1];
                        String newName = "photo-" + this.generateId() + "." + ext;
                        File storeFile = new File(path, newName);

                        item.write(storeFile);
                        map.put("photo", newName);
                    }
                } else {
                    map.put(item.getFieldName(), item.getString("UTF-8"));
                }
            }
        }
        return map;
    }
}
