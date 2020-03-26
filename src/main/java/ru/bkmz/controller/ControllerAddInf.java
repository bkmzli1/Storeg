package ru.bkmz.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
;

import static ru.bkmz.Main.bd;

public class ControllerAddInf {
    public ComboBox list;
    public TextField nameTxt;
    public DatePicker date;
    public TextArea description;
    public TextField timeSS;
    public TextField timeMM;
    public TextField timeHH;
    public TextField userSurname;
    public TextField userName;
    public TextField userMotherhood;


    public void initialize() {
        ObservableList<String> observableList = FXCollections.observableArrayList();

        try {
            Statement statmt = bd.getConn().createStatement();
            ResultSet resultSet = statmt.executeQuery("SELECT * FROM 'Storeg_name'");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                observableList.add(name);
            }
        } catch (SQLException e) {

        }
        list.setItems(observableList);
        list.setValue(observableList.get(0));


        textPropertyTime(timeHH, 2, 12);
        textPropertyTime(timeMM, 2, 60);
        textPropertyTime(timeSS, 2, 60);
        //  textProperty(nameTxt);
    }

    public void add(ActionEvent actionEvent) {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd '\n' hh:mm:ss");
        try {
            Statement statmt = bd.getConn().createStatement();
            statmt.execute("INSERT INTO '" + list.getValue() +
                    "' ('name', 'data_creations', 'data_arrivals','description','surname','nameU','motherhood') VALUES" +
                    " ('"
                    + nameTxt.getText()
                    + "', '" +
                    formatForDateNow.format(dateNow) + "', '" +
                    this.date.getValue() + "\n" + timeHH.getText() + ":" + timeMM.getText() + ":" + timeSS.getText()
                    + "','" +
                    description.getText() +
                    "','"+userSurname.getText()+"','"+userName.getText()+"','"+userMotherhood.getText()+"'); ");
        } catch (SQLException e) {

        }
        ControllerMain.itemsMain.update();
    }

    public static void textPropertyTime(TextField textField, int size, int maxNumber) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[\\d]")) {
                    newValue = newValue.replaceAll("[^\\d]", "");
                    if (newValue.length() > size) {
                        newValue = newValue.substring(0, size);
                    }
                    try {
                        if (Integer.parseInt(newValue) > maxNumber) {
                            newValue = newValue.substring(0, newValue.length() - 1);
                        }
                    } catch (NumberFormatException nfe) {

                    }

                    textField.setText(newValue);
                }
            }
        });
    }

    public static void textProperty(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                newValue = newValue.replaceAll("[\\d]", "");

                textField.setText(newValue);

            }
        });
    }

}
