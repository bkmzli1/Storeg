package ru.bkmz.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.bkmz.util.Notification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static ru.bkmz.Main.bd;

public class ControllerRemove {
    protected static final Logger logger = LogManager.getLogger();
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
                logger.error("remove: ", e);
            }
        } else {
            logger.info("list size = null");
            ControllerMain.removeDialog.getNewWindow().close();
            new Notification("Таблица", "талица пуста");
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

            logger.error("update: ", e);
        }
        if (list.getItems().size() == 0) {
            ControllerMain.removeDialog.getNewWindow().close();
            new Notification("Информация", "Таблиц больше нет");
        }
        list.setItems(observableList);
        try {
            list.setValue(observableList.get(0));
        } catch (IndexOutOfBoundsException ioobe) {

        }

    }
}
