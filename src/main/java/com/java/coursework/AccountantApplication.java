package com.java.coursework;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@RequiredArgsConstructor
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AccountantApplication extends Application {
    private ConfigurableApplicationContext applicationContext;
    private Stage stage;

    // Ініціалізація додатку
    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(CourseWorkApplication.class).run();
    }

    // Зупинка додатку
    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    // Запуск додатку
    @Override
    public void start(Stage stage) {
        applicationContext.publishEvent(new StageReadyEvent(stage));
        this.stage = stage;
        stage.show();
    }

    // Бін для об'єкту Stage
    @Bean
    public Stage stage() {
        return this.stage;
    }

    // Внутрішній клас, який представляє подію готовності Stage
    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return ((Stage) getSource());
        }
    }
}
