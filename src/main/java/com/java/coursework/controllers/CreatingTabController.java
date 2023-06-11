package com.java.coursework.controllers;

import com.java.coursework.models.Person;
import com.java.coursework.services.IndexService;
import com.java.coursework.services.PersonService;
import com.java.coursework.services.SceneService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class CreatingTabController implements Initializable {
    private final AtomicInteger globalIndex;
    private final ObservableList<Person> observableList;
    private final SceneService sceneService;
    private final PersonService personService;
    private final IndexService indexService;
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
    @FXML
    private Button deletionButton;
    private Person person;
    private boolean isUpdateButton;

    // Ініціалізує об'єкт Person для створення нової особи або оновлення існуючої особи.
    // Якщо person є нульовим, то створюється новий об'єкт Person.
    // В іншому випадку використовується переданий об'єкт для ініціалізації полів усіх полів у формі.
    // Кнопка створення отримує текст "Обновити", а кнопка видалення стає видимою.
    public void initPerson(Person person) {
        if (person == null) {
            this.person = new Person();
            isUpdateButton = false;
        } else {
            this.person = person;
            initAllFieldValues(this.person);
            creationButton.setText("Обновити");
            isUpdateButton = true;
            deletionButton.setVisible(true);
        }
    }

    //Ініціалізація сторінки додавання звіту
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        limitRateLabel.setText(limitRate);
        returnButton.setOnAction(this::onReturnButtonClick);
        creationButton.setOnAction(this::onCreationButtonClick);
        deletionButton.setOnAction(this::onDeletionButtonClick);
    }

    // Оброблює подію натискання кнопки "Повернутися".
    private void onReturnButtonClick(ActionEvent event) {
        comeBackToMainScene(returnButton);
    }

    // Оброблює подію натискання кнопки "Створити".
    private void onCreationButtonClick(ActionEvent actionEvent) {
        setAllErrorLabelsToDefaults();
        if (validateTravelSumValue() &
                validateOthersSumValue() &
                validateCostPerDay() &
                validateLeavingDayCount() &
                validatePerDiemDays()) ;
        else return;
        person.setNameSurname(nameSurnameField.getText());
        person.setDate(dateField.getValue());
        person.setLeavingDayCount(new BigDecimal(leavingDayCountField.getText()));
        person.setPerDiemDays(new BigDecimal(perDiemDaysField.getText()));
        person.setTravelSumValue(new BigDecimal(travelSumValueField.getText()));
        person.setOthersSumValue(new BigDecimal(othersSumValueField.getText()));
        person.setCostPerDayField(new BigDecimal(costPerDayField.getText()));
        person.setPerDiemValue(new BigDecimal(limitRate).multiply(person.getPerDiemDays()));
        person.setLeavingSumValue(person.getLeavingDayCount().multiply(person.getCostPerDayField()));
        person.setValue(person.getTravelSumValue()
                .add(person.getOthersSumValue())
                .add(person.getCostPerDayField().multiply(person.getLeavingDayCount()))
                .add(person.getPerDiemValue()));
        if (isUpdateButton) {
            for (int i = 0; i < observableList.size(); i++) {
                if (observableList.get(i).getIndex() == person.getIndex()) {
                    observableList.remove(i);
                    break;
                }
            }
            observableList.add(person);
        } else {
            this.person.setIndex(globalIndex.getAndIncrement());
            indexService.incrementIndexInFile();
            observableList.add(person);
            personService.appendPersonsToFile(List.of(person));
        }
        comeBackToMainScene(creationButton);
    }

    // Оброблює подію натискання кнопки "Видалити".
    private void onDeletionButtonClick(ActionEvent actionEvent) {
        for (int i = 0; i < observableList.size(); i++) {
            if (observableList.get(i).getIndex() == person.getIndex()) {
                personService.deletePersonFromFileByIndex(i);
                observableList.remove(i);
                break;
            }
        }
        comeBackToMainScene(deletionButton);
    }

    // Повертається до головного сценарію.
    @SneakyThrows
    private void comeBackToMainScene(Node node) {
        sceneService.switchScene(node, mainStageResource);
    }

    // Перевіряє, чи є значення типу double.
    private boolean validateDoubleValue(String value) {
        try {
            Double.parseDouble(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // Перевіряє, чи є значення типу integer.
    private boolean validateIntegerValue(String value) {
        try {
            Integer.parseInt(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // Приховує всі помилкові мітки.
    private void setAllErrorLabelsToDefaults() {
        othersSumValueErrorLabel.setVisible(false);
        travelSumValueErrorLabel.setVisible(false);
        costPerDayErrorLabel.setVisible(false);
        perDiemDaysCountErrorLabel.setVisible(false);
        leavingDayCountErrorLabel.setVisible(false);
    }

    // Перевіряє правильність значення "Ціна за день".
    public boolean validateCostPerDay() {
        if (validateDoubleValue(costPerDayField.getText())) return true;
        else {
            costPerDayErrorLabel.setVisible(true);
            return false;
        }
    }

    // Перевіряє правильність значення "Вартість подорожі".
    public boolean validateTravelSumValue() {
        if (validateDoubleValue(travelSumValueField.getText())) return true;
        else {
            travelSumValueErrorLabel.setVisible(true);
            return false;
        }
    }

    // Перевіряє правильність значення "Інші витрати".
    public boolean validateOthersSumValue() {
        if (validateDoubleValue(othersSumValueField.getText())) return true;
        else {
            othersSumValueErrorLabel.setVisible(true);
            return false;
        }
    }

    // Перевіряє правильність значення "Кількість днів виїзду".
    public boolean validateLeavingDayCount() {
        if (validateIntegerValue(leavingDayCountField.getText())) return true;
        else {
            leavingDayCountErrorLabel.setVisible(true);
            return false;
        }
    }

    // Перевіряє правильність значення "Кількість днів дієти".
    public boolean validatePerDiemDays() {
        if (validateIntegerValue(perDiemDaysField.getText())) return true;
        else {
            perDiemDaysCountErrorLabel.setVisible(true);
            return false;
        }
    }

    // Ініціалізує всі поля форми значеннями об'єкта Person.
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

    // Ініціалізує значення загальних сум.
    private void initAllSums(Person person) {
        perDiemGeneralValue.setText(person.getPerDiemValue().toString());
        accomodationGeneralValue.setText(person.getLeavingSumValue().toString());
        generalValue.setText(person.getValue().toString());
    }
}
