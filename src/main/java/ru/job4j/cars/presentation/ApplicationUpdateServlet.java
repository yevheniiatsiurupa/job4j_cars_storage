package ru.job4j.cars.presentation;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.cars.models.Account;
import ru.job4j.cars.models.Application;
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
 * Сервлет для редактирования объявления.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class ApplicationUpdateServlet extends HttpServlet {
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
        int applicationId = Integer.parseInt(req.getParameter("id"));
        Application application = ValidateService.getInstance().findApplicationById(applicationId);
        req.setAttribute("application", application);
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/application-update.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String answer = this.update(req);
        req.setAttribute("answer", answer);
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/application-updatePost.jsp").forward(req, resp);
    }

    /**
     * Метод для генерации случайного числа.
     */
    private String generateId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt());
    }

    /**
     * Метод для редактирования объявления.
     * Вызывает метод readForm, который возращает данные формы,
     * заполненной пользователем, в виде карты.
     * Из карты получаем параметры, которые используются для редактирования новой машины,
     * а затем и объявления.
     * Если при редактировании было добавлено новое фото, то старое удаляется из сервера.
     */
    private String update(HttpServletRequest req) throws UnsupportedEncodingException {
        String response;
        req.setCharacterEncoding("UTF-8");

        try {
            Map<String, String> map = this.readForm(req);
            int applicationId = Integer.parseInt(map.get("applicationId"));
            int carId = Integer.parseInt(map.get("carId"));
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
            boolean sold = Boolean.parseBoolean(map.get("status"));

            Application oldAppl = ValidateService.getInstance().findApplicationById(applicationId);
            if (photo == null) {
                photo = oldAppl.getPhoto();
            } else {
                String oldPhoto = oldAppl.getPhoto();
                if (oldPhoto != null) {
                    String path = (String) getServletContext().getAttribute("fileBase");
                    File forDelete = new File(path, oldPhoto);
                    forDelete.delete();
                }
            }

            CarInfo info = new CarInfo(year, price);
            Car car = ValidateService.getInstance().addOrUpdateCar(carId, carMakeId, carModelId, carBodyId, engineId, transmissionId, info);
            ValidateService.getInstance().addOrUpdateApplication(applicationId, car, accountId, desc, photo, sold);
            response = "Объявление успешно отредактировано.";
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
