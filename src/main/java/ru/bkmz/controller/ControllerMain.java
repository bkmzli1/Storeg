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

   //получаем элементы окна из fxml они имею в файле вот такой вид  fx:id="tabStored"
    public TabPane tabStored;
    public Button add;
    public Button edit;
    public Button addInfo;
    public Button remove;
    public Button removeInfo;
    public AnchorPane root;
    //создаём все дополнительные окна
    public static StageDialog addDialog ;
    public static StageDialog addInfoDialog ;
    public static StageDialog removeDialog ;
    public static StageDialog removeInfoDialog ;
    public static StageDialog editDialog;
    //это класс который нужен для получения элементов окна из вне этого класса
    public static ItemsMain itemsMain;

    public void initialize() {
        //определяем этот класс
        itemsMain = new ItemsMain(tabStored, add, edit, addInfo, remove, removeInfo);
        // обновляем таблицу
        itemsMain.update();
    }
// new StageDialog(имя файла fxml);
// создаём окно addDialog.show();//выводим окно
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
}
