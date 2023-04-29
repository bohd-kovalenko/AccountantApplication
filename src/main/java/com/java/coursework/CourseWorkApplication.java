package com.java.coursework;

import com.java.coursework.models.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class CourseWorkApplication {

    public static void main(String[] args) {
        AccountantApplication.launch(AccountantApplication.class, args);
    }

    @Bean
    public ObservableList<Person> hashtable() {
        return FXCollections.observableArrayList();
    }
}
