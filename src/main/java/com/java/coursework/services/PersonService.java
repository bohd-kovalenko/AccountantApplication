package com.java.coursework.services;

import com.java.coursework.models.Person;

import java.util.List;

public interface PersonService {
    void appendPersonsToFile(List<Person> persons);

    List<Person> readPersonsFromFile();

    void deletePersonFromFileByIndex(int index);

    void clearFile();

}
