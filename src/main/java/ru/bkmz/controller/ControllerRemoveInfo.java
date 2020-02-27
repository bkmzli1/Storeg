package ru.bkmz.controller;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static ru.bkmz.Main.bd;

public class ControllerRemoveInfo {
    public ComboBox list;
    public ListView listRemove;
    protected static final Logger logger = LogManager.getLogger();
    List<CheckBox> checkBoxes;
    List<Integer> ids;
    ObservableList<String> observableList = FXCollections.observableArrayList();

    public void initialize() {


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
        list.setItems(observableList);
        ChangeListener<String> changeListener = (observable, oldValue, newValue) -> {
            listUpdate();
        };
        list.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }

    public void remove(ActionEvent actionEvent) {
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isSelected()) {
                int id = ids.get(i);
                logger.info("remove: "+id);
                try {
                    Statement statmt = bd.getConn().createStatement();
                    statmt.execute("DELETE FROM '" + list.getValue() + "' WHERE id = '" + id + "'");
                } catch (SQLException e) {
                    logger.error("update: ", e);
                }
            }
        }
        list.setItems(observableList);
        listUpdate();
        ControllerMain.itemsMain.update();
    }

    void listUpdate() {
        try {
            Statement statmt = bd.getConn().createStatement();
            ResultSet resultSet = statmt.executeQuery("SELECT * FROM '" + list.getValue()+"'");
            ObservableList<HBox> hBoxes = FXCollections.observableArrayList();
            checkBoxes = new ArrayList<>();
            ids = new ArrayList<>();
            while (resultSet.next()) {
                HBox hBox = new HBox(5);
                hBox.setAlignment(Pos.CENTER_LEFT);
                CheckBox checkBox = new CheckBox();
                VBox vBox = new VBox(5);
                vBox.getChildren().addAll(new Text());
                int id = resultSet.getInt("id");
                vBox.getChildren().addAll(new Text("товар: " + id));
                vBox.getChildren().addAll(new Text("имя: " + resultSet.getString("name")));
                vBox.getChildren().addAll(new Text("дата создания записи: " + resultSet.getString("data_Creations")));
                vBox.getChildren().addAll(new Text("дата поступления: " + resultSet.getString("data_Arrivals")));
                vBox.getChildren().addAll(new Text("описание: " + resultSet.getString("description")));
                hBox.getChildren().addAll(checkBox, vBox);
                hBoxes.add(hBox);
                ids.add(id);
                checkBoxes.add(checkBox);
            }
            listRemove.setItems(hBoxes);
        } catch (SQLException e) {
            logger.error("update: ", e);
        }

    }
}

