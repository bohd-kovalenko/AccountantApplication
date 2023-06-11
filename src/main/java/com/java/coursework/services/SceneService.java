package com.java.coursework.services;

import com.java.coursework.models.Person;
import javafx.scene.Node;
import org.springframework.core.io.Resource;

public interface SceneService {
    void switchScene(Node node, Resource resource, Person person);

    void switchScene(Node node, Resource resource);
}
