package com.java.coursework.controllers;

import com.java.coursework.models.Person;
import com.java.coursework.services.ExcelCreatorService;
import com.java.coursework.services.SceneService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Controller
@Lazy
@RequiredArgsConstructor
public class MainTabController implements Initializable {
    private final ObservableList<Person> observableList;
    private final SceneService sceneService;
    private final ExcelCreatorService excelCreatorService;
    @Value("${creating-tab.fxml.path}")
    private Resource resource;
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> nameSurnameColumn;
    @FXML
    private TableColumn<Person, Double> valueColumn;
    @FXML
    private TableColumn<Person, LocalDate> dateColumn;
    @FXML
    private Button addButton;
    @FXML
    private Button calculatingButton;

    //Ініціалізація головної сторінки
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("nameSurname"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        personTable.setItems(observableList);
        addButton.setOnAction(actionEvent -> onAddButtonClick(addButton));
        calculatingButton.setOnAction(this::onCalculationButtonClick);
        personTable.setRowFactory(trf -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    onTableRowClick(row.getItem(), row);
                }
            });
            return row;
        });
    }

    // Оброблює подію натискання кнопки "Додати".
    @SneakyThrows
    private void onAddButtonClick(Node node) {
        sceneService.switchScene(node, resource, null);
    }

    // Оброблює подію натискання на рядок таблиці.
    @SneakyThrows
    private void onTableRowClick(Person person, Node node) {
        sceneService.switchScene(node, resource, person);
    }

    // Оброблює подію натискання кнопки "Розрахувати".
    @SneakyThrows
    private void onCalculationButtonClick(ActionEvent actionEvent) {
        excelCreatorService.createExcelDocument();
    }
}
