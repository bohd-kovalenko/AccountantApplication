package com.java.coursework;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<AccountantApplication.StageReadyEvent> {
    @Override
    public void onApplicationEvent(AccountantApplication.StageReadyEvent event) {
        Stage stage = event.getStage();
        Group root = new Group();
        Scene scene = new Scene(root, Color.AQUA);
        stage.setTitle("Accounting application");
        stage.setScene(scene);
    }
}
