package ru.job4j.cars.validation;

public class ErrorException extends RuntimeException {
    public ErrorException(String msg) {
        super(msg);
    }
}
