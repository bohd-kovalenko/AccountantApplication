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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class CreatingTab implements Initializable {
    private final ObservableList<Person> mainList;
    private final ApplicationContext applicationContext;
    @Value("${main-stage.fxml.path}")
    private Resource mainStageResource;
    @Value("${limit-rate}")
    private String limitRate;
    @FXML
    private TextField nameSurnameField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField leavingDayCountField;
    @FXML
    private TextField perDiemDaysField;
    @FXML
    private TextField travelSumValueField;
    @FXML
    private TextField othersSumValueField;
    @FXML
    private TextField costPerDayField;
    @FXML
    private Button creationButton;
    @FXML
    private Button returnButton;
    @FXML
    private Label costPerDayErrorLabel;
    @FXML
    private Label leavingDayCountErrorLabel;
    @FXML
    private Label othersSumValueErrorLabel;
    @FXML
    private Label travelSumValueErrorLabel;
    @FXML
    private Label perDiemDaysCountErrorLabel;
    @FXML
    private Label accomodationGeneralValue;
    @FXML
    private Label perDiemGeneralValue;
    @FXML
    private Label generalValue;
    @FXML
    private Label limitRateLabel;
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void initPerson(Person person) {
        if (person == null) {
            this.person = new Person();
            mainList.add(this.person);
        } else {
            this.person = person;
            initAllFieldValues(this.person);
            creationButton.setText("Обновити");
        }
    }

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        limitRateLabel.setText(limitRate);
        returnButton.setOnAction(this::onReturnButtonClick);
        creationButton.setOnAction(this::onCreationButtonClick);
    }

    @FXML
    private void onReturnButtonClick(ActionEvent event) {
        comeBackToMainScene(event);
    }

    @FXML
    private void onCreationButtonClick(ActionEvent actionEvent) {
        setAllErrorLabelsToDefaults();
        if (validateTravelSumValue() &
                validateOthersSumValue() &
                validateCostPerDay() &
                validateLeavingDayCount() &
                validatePerDiemDays()) ;
        else return;
        Integer leavingDayCountParsed = Integer.parseInt(leavingDayCountField.getText()),
                perDiemDaysParsed = Integer.parseInt(perDiemDaysField.getText());
        Double travelSumValueParsed = Double.parseDouble(travelSumValueField.getText()),
                othersSumValueParsed = Double.parseDouble(othersSumValueField.getText()),
                costPerDayParsed = Double.parseDouble(costPerDayField.getText()),
                limitRateParsed = Double.parseDouble(limitRate),
                value = travelSumValueParsed +
                        othersSumValueParsed +
                        costPerDayParsed * leavingDayCountParsed +
                        limitRateParsed * perDiemDaysParsed;
        person.setNameSurname(nameSurnameField.getText());
        person.setDate(dateField.getValue());
        person.setLeavingDayCount(leavingDayCountParsed);
        person.setPerDiemDays(perDiemDaysParsed);
        person.setTravelSumValue(travelSumValueParsed);
        person.setOthersSumValue(othersSumValueParsed);
        person.setCostPerDayField(costPerDayParsed);
        person.setValue(value);
        person.setPerDiemValue(Double.parseDouble(limitRate) * perDiemDaysParsed);
        comeBackToMainScene(actionEvent);
    }

    @SneakyThrows
    private void comeBackToMainScene(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(mainStageResource.getURL());
        loader.setControllerFactory(applicationContext::getBean);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private boolean validateDoubleValue(String value) {
        try {
            Double.parseDouble(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean validateIntegerValue(String value) {
        try {
            Integer.parseInt(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void setAllErrorLabelsToDefaults() {
        othersSumValueErrorLabel.setVisible(false);
        travelSumValueErrorLabel.setVisible(false);
        costPerDayErrorLabel.setVisible(false);
        perDiemDaysCountErrorLabel.setVisible(false);
        leavingDayCountErrorLabel.setVisible(false);
    }

    public boolean validateCostPerDay() {
        if (validateDoubleValue(costPerDayField.getText())) return true;
        else {
            costPerDayErrorLabel.setVisible(true);
            return false;
        }
    }

    public boolean validateTravelSumValue() {
        if (validateDoubleValue(travelSumValueField.getText())) return true;
        else {
            travelSumValueErrorLabel.setVisible(true);
            return false;
        }
    }

    public boolean validateOthersSumValue() {
        if (validateDoubleValue(othersSumValueField.getText())) return true;
        else {
            othersSumValueErrorLabel.setVisible(true);
            return false;
        }
    }

    public boolean validateLeavingDayCount() {
        if (validateIntegerValue(leavingDayCountField.getText())) return true;
        else {
            leavingDayCountErrorLabel.setVisible(true);
            return false;
        }
    }

    public boolean validatePerDiemDays() {
        if (validateIntegerValue(perDiemDaysField.getText())) return true;
        else {
            perDiemDaysCountErrorLabel.setVisible(true);
            return false;
        }
    }

    private void initAllFieldValues(Person person) {
        nameSurnameField.appendText(person.getNameSurname());
        dateField.setValue(person.getDate());
        leavingDayCountField.appendText(person.getLeavingDayCount().toString());
        perDiemDaysField.appendText(person.getPerDiemDays().toString());
        travelSumValueField.appendText(person.getTravelSumValue().toString());
        othersSumValueField.appendText(person.getOthersSumValue().toString());
        costPerDayField.appendText(person.getCostPerDayField().toString());
        initAllSums(person);
    }

    private void initAllSums(Person person) {
        perDiemGeneralValue.setText(Double.toString(person.getPerDiemValue()));
        accomodationGeneralValue.setText(Double.toString(person.getCostPerDayField() * person.getLeavingDayCount()));
        generalValue.setText(Double.toString(person.getValue()));
    }

}
