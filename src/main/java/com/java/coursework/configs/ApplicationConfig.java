package com.java.coursework.configs;

import com.java.coursework.models.Person;
import com.java.coursework.services.IndexService;
import com.java.coursework.services.PersonService;
import com.java.coursework.services.impl.IndexServiceImpl;
import com.java.coursework.services.impl.PersonServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ApplicationConfig {

    // Функція, що створює та повертає бін для PersonService
    @Bean
    public PersonService personService() {
        return new PersonServiceImpl();
    }

    // Функція, що створює та повертає бін для IndexService
    // Залежність від PersonService передається у конструктор IndexServiceImpl
    @Bean
    public IndexService indexService() {
        return new IndexServiceImpl(personService());
    }

    // Функція, що створює та повертає бін для AtomicInteger
    // Глобальний індекс читається з файлу за допомогою IndexService
    @Bean
    public AtomicInteger globalIndex() {
        return new AtomicInteger(indexService().readGlobalIndexFromFile());
    }

    // Функція, що створює та повертає бін для ObservableList<Person>
    // Список об'єктів Person читається з файлу за допомогою PersonService
    @Bean
    public ObservableList<Person> observableList() {
        return FXCollections.observableArrayList(personService().readPersonsFromFile());
    }
}
