package com.java.coursework.controllers;

import com.java.coursework.models.Person;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
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
    private final Stage stage;
    private final ApplicationContext applicationContext;
    private final ObservableList<Person> mainList;
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

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("nameSurname"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        personTable.setItems(mainList);
        addButton.setOnAction(this::onAddButtonClick);
        personTable.setRowFactory(trf -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    onTableRowClick(event, row.getItem());
                }
            });
            return row;
        });
    }

    @SneakyThrows
    @FXML
    private void onAddButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(resource.getURL());
        loader.setControllerFactory(applicationContext::getBean);
        Parent parent = loader.load();
        ((CreatingTab) loader.getController()).initPerson(null);
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @SneakyThrows
    private void onTableRowClick(MouseEvent event, Person person) {
        FXMLLoader loader = new FXMLLoader(resource.getURL());
        loader.setControllerFactory(applicationContext::getBean);
        Parent parent = loader.load();
        ((CreatingTab) loader.getController()).initPerson(person);
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
//    private void changeScene()
//    private void pdfSummaryCreation(){
//        try(PDDocument document = Loader.loadPDF(new File("src/main/resources/readyToUseDocs/abc.pdf"))){
//            document.addSignature();
//        }catch (Exception e){
//
//        }
//    }

}
