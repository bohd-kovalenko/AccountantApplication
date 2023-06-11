package com.java.coursework.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.java.coursework.models.Person;
import com.java.coursework.services.PersonService;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersonServiceImpl implements PersonService {
    @Value("${storing-file-path}")
    private String filePath;

    /*
     * Додає список осіб до файлу.
     */
    @Override
    public void appendPersonsToFile(List<Person> persons) {
        List<Person> fileContent = readPersonsFromFile();
        try (FileWriter fileWriter = new FileWriter(filePath);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            fileContent.addAll(persons);
            String personsJson = objectMapper.writeValueAsString(fileContent);
            bufferedWriter.write(personsJson);
            bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println("Помилка додавання осіб до файлу: " + e.getMessage());
        }
    }

    /*
     * Зчитує список осіб з файлу.
     */
    @Override
    public List<Person> readPersonsFromFile() {
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String personsJson = bufferedReader.readLine();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            List<Person> persons;
            if (personsJson == null) {
                persons = new ArrayList<>();
            } else {
                persons = objectMapper.readValue(personsJson, new TypeReference<>() {
                });
            }
            return persons;
        } catch (IOException e) {
            System.err.println("Помилка зчитування осіб з файлу: " + e.getMessage());
            return null;
        }
    }

    /*
     * Видаляє особу з файлу за заданим індексом.
     */
    @Override
    public void deletePersonFromFileByIndex(int index) {
        List<Person> fileContent = readPersonsFromFile();
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).getIndex() == index) {
                fileContent.remove(i);
                break;
            }
        }
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String personsJson = objectMapper.writeValueAsString(fileContent);
            bufferedWriter.write(personsJson);
        } catch (IOException e) {
            System.out.println("Помилка запису в файл: " + e.getMessage());
        }
    }

    /*
     * Очищає файл.
     */
    @Override
    public void clearFile() {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write("");
            fileWriter.flush();
        } catch (IOException e) {
            System.err.println("Помилка очищення файлу: " + e.getMessage());
        }
    }
}
