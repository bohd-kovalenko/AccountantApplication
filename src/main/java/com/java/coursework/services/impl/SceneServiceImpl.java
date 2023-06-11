package com.java.coursework.services.impl;

import com.java.coursework.controllers.CreatingTabController;
import com.java.coursework.models.Person;
import com.java.coursework.services.SceneService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SceneServiceImpl implements SceneService {
    private final ApplicationContext applicationContext;

    /*
     * Переключає сцену на інший вигляд з переданим вузлом, ресурсом та особою.
     */
    @Override
    @SneakyThrows
    public void switchScene(Node node, Resource resource, Person person) {
        FXMLLoader loader = new FXMLLoader(resource.getURL());
        loader.setControllerFactory(applicationContext::getBean);
        Parent parent = loader.load();
        ((CreatingTabController) loader.getController()).initPerson(person);
        Scene scene = node.getScene();
        Stage stage = (Stage) node.getScene().getWindow();
        scene.setRoot(loader.getRoot());
        stage.setScene(scene);
        stage.show();
    }

    /*
     * Переключає сцену на інший вигляд з переданим вузлом та ресурсом.
     */
    @Override
    @SneakyThrows
    public void switchScene(Node node, Resource resource) {
        FXMLLoader loader = new FXMLLoader(resource.getURL());
        loader.setControllerFactory(applicationContext::getBean);
        Parent parent = loader.load();
        Scene scene = node.getScene();
        Stage stage = (Stage) node.getScene().getWindow();
        scene.setRoot(loader.getRoot());
        stage.setScene(scene);
        stage.show();
    }
}
