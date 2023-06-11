package com.java.coursework;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
    private Resource mainPage;

    // Функція, яка слухає подію StageReadyEvent і ініціалізує сцену (Stage)
    @SneakyThrows
    @Override
    public void onApplicationEvent(AccountantApplication.StageReadyEvent event) {
        FXMLLoader loader = new FXMLLoader(mainPage.getURL());
        loader.setControllerFactory(applicationContext::getBean);
        Stage stage = event.getStage();
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setTitle("Accounting Application");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/application.png"));
    }
}
