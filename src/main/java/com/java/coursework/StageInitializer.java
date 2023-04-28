package com.java.coursework;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StageInitializer implements ApplicationListener<AccountantApplication.StageReadyEvent> {
    private final ApplicationContext applicationContext;
    @Value("${main-stage.fxml.path}")
    private Resource resource;

    @SneakyThrows
    @Override
    public void onApplicationEvent(AccountantApplication.StageReadyEvent event) {
        FXMLLoader loader = new FXMLLoader(resource.getURL());
        loader.setControllerFactory(applicationContext::getBean);
        Stage stage = event.getStage();
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setTitle("Accountingapplication");
        stage.setScene(scene);
    }
}
