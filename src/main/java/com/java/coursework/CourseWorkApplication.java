package com.java.coursework;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseWorkApplication {

    // Головний метод, який запускає додаток
    public static void main(String[] args) {
        // Виклик методу launch() з класу AccountantApplication для запуску додатку
        AccountantApplication.launch(AccountantApplication.class, args);
    }
}
