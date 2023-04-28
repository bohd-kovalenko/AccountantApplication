package com.java.coursework;

import com.java.coursework.models.Person;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Hashtable;

@SpringBootApplication
public class CourseWorkApplication {

    public static void main(String[] args) {
        AccountantApplication.launch(AccountantApplication.class, args);
    }

    @Bean
    public ArrayList<Person> hashtable(){
        return new ArrayList<>();
    }
}
