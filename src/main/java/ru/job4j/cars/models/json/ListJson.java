package ru.job4j.cars.models.json;

import java.util.List;

/**
 * Класс для передачи списков через ajax-запросы.
 * @version 1.0.
 * @since 13/09/2019.
 * @author Evgeniya Tsiurupa
 */
public class ListJson {
    List list;

    public ListJson(List list) {
        this.list = list;
    }

    public ListJson() {
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
