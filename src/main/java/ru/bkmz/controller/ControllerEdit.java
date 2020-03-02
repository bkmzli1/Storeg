package ru.bkmz.controller;

import javafx.beans.value.ChangeListener;
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
import java.time.LocalDate;


import static ru.bkmz.Main.bd;
import static ru.bkmz.controller.ControllerAddInf.textPropertyTime;

public class ControllerEdit {


    public ComboBox listTable;
    public ComboBox<String> listName;
    public TextField nameTxt;
    public DatePicker date;
    public TextField timeHH;
    public TextField timeMM;
    public TextField timeSS;
    public TextArea description;
    String id;

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
        listTable.setItems(observableList);
        listTable.setValue(observableList.get(0));
        ChangeListener<String> changeListener = (observable, oldValue, newValue) -> {
            listUpdate();
        };
        listTable.getSelectionModel().selectedItemProperty().addListener(changeListener);
        textPropertyTime(timeHH, 2, 12);
        textPropertyTime(timeMM, 2, 60);
        textPropertyTime(timeSS, 2, 60);

        ChangeListener<String> changeListener2 = (observable, oldValue, newValue) -> {

            try {
                if (listName.getItems().size() != 0) {
                    String[] idM = newValue.split("");
                    String id = "";
                    for (int i = 0; i < idM.length; i++) {
                        if (idM[i].equals(" ")) {
                            break;
                        } else {
                            id += idM[i];
                        }
                    }
                    id = id.replace("id:", "");
                    this.id = id;

                    Statement statmtTable = bd.getConn().createStatement();

                    ResultSet resultSetTable = statmtTable.executeQuery("SELECT * FROM '" + listTable.getValue() + "' WHERE id = " + id);


                    while (resultSetTable.next()) {
                        nameTxt.setText(resultSetTable.getString("name"));
                        description.setText(resultSetTable.getString("description"));
                        String times = resultSetTable.getString("data_arrivals");
                        String[] DataAndTime = times.split("\n");
                        date.setValue(LocalDate.parse(DataAndTime[0]));
                        String[] time = DataAndTime[1].split(":");
                        timeHH.setText(time[0]);
                        timeMM.setText(time[1]);
                        timeSS.setText(time[2]);
                    }
                } else {

                }

            } catch (SQLException e) {

            }
        };
        listName.getSelectionModel().selectedItemProperty().addListener(changeListener2);


        listUpdate();
    }

    public void edit(ActionEvent actionEvent) {
        try {
            bd.setBD(" UPDATE '" + listTable.getValue() + "' SET 'name' = ' " + nameTxt.getText() + " ',  'data_arrivals' = '" + this.date.getValue() + "\n " + timeHH.getText() + ":" +
                    timeMM.getText() +
                    ":" + timeSS.getText() + "', " + "'description' = '" + description.getText() + "'  WHERE id = '" + this.id + "'");
            ControllerMain.itemsMain.update();
        } catch (SQLException e) {


        }
    }

    void listUpdate() {
        try {
            Statement statmt = bd.getConn().createStatement();
            try {
                ResultSet resultSet = statmt.executeQuery("SELECT * FROM '" + listTable.getValue() + "'");

                ObservableList<String> observableList = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    observableList.add("id:" + id + " имя:" + name);
                }
                listName.setItems(observableList);
                if (listName.getItems().size() != 0) {
                    listName.setValue(observableList.get(0));
                }
            } catch (SQLException sqlE) {
                statmt.execute("DELETE FROM 'Storeg_name' WHERE name = '" + listTable.getValue() + "'");

            }
        } catch (SQLException e) {

        }

    }

}
