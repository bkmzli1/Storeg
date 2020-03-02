package ru.bkmz.util;

import javafx.scene.control.CheckBox;

public class Table {
    int id;//id таблицы
    CheckBox checkBox;// кнопачка для удаления
    String name, dataCreations, dataArrivals, description;//все строковые параметры

    public Table(int id, String name, String dataCreations, String dataArrivals, String description) {
        this.id = id;
        this.name = name;
        this.dataCreations = dataCreations;
        this.dataArrivals = dataArrivals;
        this.description = description;
        this.checkBox = new CheckBox();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataCreations() {
        return dataCreations;
    }

    public void setDataCreations(String dataCreations) {
        this.dataCreations = dataCreations;
    }

    public String getDataArrivals() {
        return dataArrivals;
    }

    public void setDataArrivals(String dataArrivals) {
        this.dataArrivals = dataArrivals;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
