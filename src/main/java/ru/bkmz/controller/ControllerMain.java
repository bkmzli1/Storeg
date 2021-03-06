package ru.bkmz.controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import ru.bkmz.util.StageDialog;
import ru.bkmz.util.items.ItemsMain;

import java.io.IOException;

public class ControllerMain {

    public static StageDialog addDialog = new StageDialog("add");
    public static StageDialog addInfoDialog = new StageDialog("addInfo");
    public static StageDialog removeDialog = new StageDialog("remove");
    public static StageDialog removeInfoDialog = new StageDialog("removeInfo");
    public static StageDialog editDialog = new StageDialog("edit");
    public static ItemsMain itemsMain;
    public TabPane tabStored;
    public Button add;
    public Button edit;
    public Button addInfo;
    public Button remove;
    public Button removeInfo;
    public AnchorPane root;

    public void initialize() {
        itemsMain = new ItemsMain(tabStored, add, edit, addInfo, remove, removeInfo);
        itemsMain.update();
    }

    public void addStored(ActionEvent actionEvent) {
        addDialog = new StageDialog("add");
        addDialog.show();
    }

    public void addStoredInfo(ActionEvent actionEvent) {
        addInfoDialog = new StageDialog("addInfo");
        addInfoDialog.show();
    }

    public void remove(ActionEvent actionEvent) {
        removeDialog = new StageDialog("remove");
        removeDialog.show();
    }

    public void removeInfo(ActionEvent actionEvent) {
        removeInfoDialog = new StageDialog("removeInfo");
        removeInfoDialog.show();
    }


    public void edit(ActionEvent actionEvent) {
        editDialog = new StageDialog("edit");
        editDialog.show();
    }

    public void update(ActionEvent actionEvent) {
        itemsMain.update();
    }


}
