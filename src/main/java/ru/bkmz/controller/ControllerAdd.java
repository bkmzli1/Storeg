package ru.bkmz.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import static ru.bkmz.Main.bd;

public class ControllerAdd {

    public TextField addText;




    public void initialize() {

    }

    public void addButton(ActionEvent actionEvent) {
        Statement statmt = null;

        try {
            bd.setBD(
                    "create table Storeg_name ( \tid INTEGER not null \t\tconstraint Storeg_name_pk \t\t\tprimary key autoincrement, \tname TEXT not null );  create unique index Storeg_name_id_uindex \ton Storeg_name (id);");
        } catch (SQLException e) {
        }
        String nameTable = addText.getText();
        try {
            statmt = bd.getConn().createStatement();
            ResultSet resultSet = statmt.executeQuery("SELECT * FROM '" + nameTable + "'");

        } catch (SQLException e) {
            try {
                Statement statmtCreate = bd.getConn().createStatement();
                statmtCreate.execute(
                        ("create table \"table\" ( id INTEGER not null constraint table_pk primary key autoincrement, name " +
                                "TEXT not null, data_creations TEXT not null, data_arrivals TEXT not null, description TEXT ); create unique " +
                                "index table_id_uindex on \"table\" (id);")
                                .replace("\"table\"", "\"" + nameTable + "\""));
            } catch (SQLException ex) {

            }
            try {
                bd.setBD("INSERT INTO 'Storeg_name' ('name') VALUES ('" + nameTable + "'); ");
            } catch (SQLException ex) {

            }
        }

        ControllerMain.itemsMain.update();
    }
}
