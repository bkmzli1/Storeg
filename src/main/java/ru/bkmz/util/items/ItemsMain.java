package ru.bkmz.util.items;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.bkmz.util.Notification;
import ru.bkmz.util.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static ru.bkmz.Main.bd;

public class ItemsMain {
    public TabPane tabStored;
    public Button add;
    public Button edit;
    public Button addInfo;
    public Button remove;
    public Button removeInfo;

    protected static final Logger logger = LogManager.getLogger();

    public ItemsMain(TabPane tabStored, Button add, Button edit, Button addInfo, Button remove, Button removeInfo) {
        this.tabStored = tabStored;
        this.add = add;
        this.edit = edit;
        this.addInfo = addInfo;
        this.remove = remove;
        this.removeInfo = removeInfo;
    }

    public TabPane getTabStored() {
        return tabStored;
    }

    public void setTabStored(TabPane tabStored) {
        this.tabStored = tabStored;
    }

    public void update() {
        try {
            logger.trace("update: start");
            tabStored.getTabs().clear();
            Statement statmt = bd.getConn().createStatement();
            ResultSet resultSet = statmt.executeQuery("SELECT * FROM 'Storeg_name'");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Tab tab = new Tab(name);
                TableView<Table> table = new TableView();
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                table.setTableMenuButtonVisible(true);
                HBox.setHgrow(table, Priority.ALWAYS);
                VBox.setVgrow(table, Priority.ALWAYS);

                TableColumn<Table, String> nameCol = new TableColumn<Table, String>("товар");
                TableColumn<Table, String> dataCol = new TableColumn<Table, String>("дата");
                TableColumn<Table, String> dataCreationsCol = new TableColumn<Table, String>("дата создания записи");

                TableColumn<Table, String> dataArrivalsCol = new TableColumn<Table, String>("дата поступления");
                TableColumn<Table, String> descriptionCol = new TableColumn<Table, String>("описание");
                dataCol.getColumns().addAll(dataCreationsCol, dataArrivalsCol);


                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));
                dataCreationsCol.setCellValueFactory(new PropertyValueFactory<>("dataCreations"));
                dataArrivalsCol.setCellValueFactory(new PropertyValueFactory<>("dataArrivals"));
                descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
                table.getColumns().addAll(nameCol, dataCol, descriptionCol);


                Statement statmtCol = bd.getConn().createStatement();
                try {
                    ResultSet resultSetCol = statmtCol.executeQuery("SELECT * FROM '" + name + "'");

                    ObservableList<Table> col = FXCollections.observableArrayList();
                    while (resultSetCol.next()) {
                        col.add(
                                new Table(
                                        resultSetCol.getInt("id"),
                                        resultSetCol.getString("name"),
                                        resultSetCol.getString("data_Creations"),
                                        resultSetCol.getString("data_Arrivals"),
                                        resultSetCol.getString("description")
                                ));
                    }
                    table.setItems(col);
                    statmtCol.close();
                    resultSetCol.close();
                    tab.setContent(table);
                    tabStored.getTabs().add(tab);
                } catch (SQLException sqlE) {
                    statmt.execute("DELETE FROM 'Storeg_name' WHERE name = '" + name + "'");
                    new Notification("Error", "Таблица \""+ name +"\" несуществует");
                }
            }
            statmt.close();
            resultSet.close();
            logger.trace("update: stop");
        } catch (SQLException e) {
            logger.warn("update: ", e);
        }
    }
}
