package ru.bkmz.util.items;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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

    //получаем элементы
    public ItemsMain(TabPane tabStored, Button add, Button edit, Button addInfo, Button remove, Button removeInfo) {
        this.tabStored = tabStored;
        this.add = add;
        this.edit = edit;
        this.addInfo = addInfo;
        this.remove = remove;
        this.removeInfo = removeInfo;
    }

    //обновляем таблицу
    public void update() {
        try {

            tabStored.getTabs().clear();//очищаем таблицу чтобы при повторном вызове она не дублировала окна
            Statement statmt = bd.getConn().createStatement();//подключаем запрос в БД
            ResultSet resultSet = statmt.executeQuery("SELECT * FROM 'Storeg_name'");//получаем список таблиц из БД
            while (resultSet.next()) {//говорит прошли мы весь список или нет
                String name = resultSet.getString("name");//получаем имя таблицы
                Tab tab = new Tab(name);//создаем панель в котрой будет таблица  таблицы
                TableView<Table> table = new TableView();//создаем таблицу
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);//чтобы колонки таблицы были на всё а не на её часть
                //говорим чтобы ано растянулась на всё поле внутри  Tab
                HBox.setHgrow(table, Priority.ALWAYS);
                VBox.setVgrow(table, Priority.ALWAYS);
                //создаём колонки и даём им имена,а также говорим что они работают с классом Table
                TableColumn<Table, String> nameCol = new TableColumn<Table, String>("товар");
                TableColumn<Table, String> nameUserCol = new TableColumn<Table, String>("ФИО");

                TableColumn<Table, String> dataCol = new TableColumn<Table, String>("дата");
                TableColumn<Table, String> dataCreationsCol = new TableColumn<Table, String>("дата создания записи");
                TableColumn<Table, String> dataArrivalsCol = new TableColumn<Table, String>("дата поступления");
                TableColumn<Table, String> descriptionCol = new TableColumn<Table, String>("описание");

                //говорим что в классе Table какая колонка с какой работает переменной
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                nameUserCol.setCellValueFactory(new PropertyValueFactory<>("FIO"));
                dataCreationsCol.setCellValueFactory(new PropertyValueFactory<>("dataCreations"));
                dataArrivalsCol.setCellValueFactory(new PropertyValueFactory<>("dataArrivals"));
                descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

                dataCol.getColumns().addAll(dataCreationsCol, dataArrivalsCol);
                //добовляем в таблицу колонки
                table.getColumns().addAll(nameCol, nameUserCol,dataCol, descriptionCol);


                Statement statmtCol = bd.getConn().createStatement();
                try {
                    ResultSet resultSetCol = statmtCol.executeQuery("SELECT * FROM '" + name + "'");//запрос на таблицу из БД

                    ObservableList<Table> col = FXCollections.observableArrayList(); // осздайт список строк таблицы
                    while (resultSetCol.next()) {//проходим по таблице
                        col.add(//добовляем в таблицу сласс Table который является по суте строкой таблицы
                                new Table(
                                        //запоняем её
                                        resultSetCol.getInt("id"),
                                        resultSetCol.getString("name"),
                                        resultSetCol.getString("data_Creations"),
                                        resultSetCol.getString("data_Arrivals"),
                                        resultSetCol.getString("description"),
                                        resultSetCol.getString("surname") +" "+resultSetCol.getString("nameU")+" "+resultSetCol.getString("motherhood")
                                ));
                    }

                    table.setItems(col);//добовляем в таблицу список строк
                    //закрываем подключения к БД
                    statmtCol.close();
                    resultSetCol.close();
                    //тобовляем саму таблицу в панель
                    tab.setContent(table);
                    //добовляем панель в список панелей
                    tabStored.getTabs().add(tab);
                } catch (SQLException sqlE) {
                    //нужно если списка таблиц нет он их создаёт
                    statmt.execute("DELETE FROM 'Storeg_name' WHERE name = '" + name + "'");

                }
            }
            //закрываем подключения к БД
            statmt.close();
            resultSet.close();
//вообще хз какие тут могут быть исключения
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
