package ru.bkmz.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static ru.bkmz.Main.bd;

public class ControllerRemove {

    public ComboBox list;
    ObservableList<String> observableList = FXCollections.observableArrayList();

    public void initialize() {
        update();
    }

    public void remove(ActionEvent actionEvent) {
        if (list.getItems().size() != 0) {
//drop table bkmz;
            Statement statmtCreate = null;
            try {
                statmtCreate = bd.getConn().createStatement();
                statmtCreate.execute("drop table '" + list.getValue() + "';");
                Statement statmt = bd.getConn().createStatement();
                statmt.execute("DELETE FROM 'Storeg_name' WHERE name = '" + list.getValue() + "'");
            } catch (SQLException e) {

            }
        } else {

            ControllerMain.removeDialog.getNewWindow().close();

        }
        update();
        ControllerMain.itemsMain.update();
    }

    void update() {
        observableList.clear();
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
        try {
            list.setValue(observableList.get(0));
        } catch (IndexOutOfBoundsException ioobe) {
        }


    }
}
