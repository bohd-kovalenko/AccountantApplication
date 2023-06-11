package com.java.coursework.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.java.coursework.models.Index;
import com.java.coursework.services.IndexService;
import com.java.coursework.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;

@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {
    private final PersonService personService;
    @Value("${index-file-path}")
    private String filePath;

    /*
     Зчитує глобальний індекс з файлу.
     */
    @Override
    public int readGlobalIndexFromFile() {
        int indexInt;
        String indexJson;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            indexJson = bufferedReader.readLine();
            try {
                Index index = objectMapper.readValue(indexJson, Index.class);
                indexInt = index.getIndex();
            } catch (Exception e) {
                indexInt = 0;
                writeIndexToFile(new Index(0));
                personService.clearFile();
            }
        } catch (Exception e) {
            System.err.println("Error reading Persons from file: " + e.getMessage());
            throw new RuntimeException("Error extracting global index from file!");
        }
        return indexInt;
    }

    /*
        Збільшує глобальний індекс у файлі на одиницю.
     */
    @Override
    public void incrementIndexInFile() {
        int index = readGlobalIndexFromFile();
        writeIndexToFile(new Index(++index));
    }

    /*
    Запису індекс до файлу
     */
    private void writeIndexToFile(Index index) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            ObjectMapper objectMapper = new ObjectMapper();
            String indexJson;
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            indexJson = objectMapper.writeValueAsString(index);
            bufferedWriter.write(indexJson);
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }
}
