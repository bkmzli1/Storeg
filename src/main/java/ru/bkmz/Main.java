package ru.bkmz;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.bkmz.sql.BD;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Objects;

//Стартовый класс отсюда всё начинается
public class Main extends Application {
    public static Stage stage;// Stage это окно. Внём хранится всё окно
    final String name = "storeg";//имя окна

    public static String FileSeve = System.getenv("APPDATA") + "\\Storeg",// фаил где будет БД
            SQLFile = FileSeve + "\\journal.sqlite";// Файл БД
    public static BD bd;//это класс контроля БД


    public static void main(String[] args) {

        launch();//Запуск окна
        System.exit(0);//полное завиршение программы
    }

    //тут происходит подготовка к старту
    // этот метод запускается перед функцией start() он нужен для подготовки всех компонентов которые нужны перед запуском окна
    @Override
    public void init() {
        try {//исключение
            bd = new BD(SQLFile);//говорим где находится БД при  этом загружая в переменую класс БД
        } catch (Exception e) {//ловим все исключения поскольку тут может выпость только одно это нет папки Storeg в Roaming

            File file = new File(FileSeve);//говорим полный путь до файла
            if (!file.exists()) {//file.exists()говорит если есть файл то true если нет то false
                file.mkdir();//создайт файл
            }
            //файл можно не создавать так как само API для БД может его создать
            try {
                bd = new BD(SQLFile);// сонва пытаемся подключиться к БД
                //исключения тут нафиг не нужны но они есть так как BD выдает исключения и мы их в любом случаии должны обработать
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }


        }

    }

    //Запуск самого окна
    @Override
    public void start(Stage stage) {


        FXMLLoader loader = new FXMLLoader();//загруска файлов fxml в которых содержится набросок окна (в src/main/resu../fxml/тут все файлы окон)
        loader.setLocation(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/main.fxml")));/*говорим от куда загружаем нужную заготовку Objects.requireNonNull рекомендуется но
        можно без него
         getClass().getClassLoader().getResource - получаем класс затем загрузчик из класса а затем получаем папку ресурчы из jar а потом получаем саму заготовку
         */

        try {
            loader.load();//загружаем заготовку
        } catch (IOException e) {//тоже просто требует исключение
            e.printStackTrace();
        }


        Parent root = loader.getRoot();//получаем окно

        Scene scene = new Scene(root);//создаём внутренее окно но невыводим его

        stage.setScene(scene);//загружаем окно в внешнее окно
        stage.setTitle(name);// присавеваем окну имя
       //что делать при закрыттии окна
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                Platform.exit();//закрываем javaFX грубая формулировка но соёдйт
            }
        });

        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();//получаем размеры окна
        //какое окно должно быть изночально я сделал 50% от размеров окна
        stage.setHeight((sSize.height * 50d) / 100d);
        stage.setWidth((sSize.width * 50d) / 100d);
        //какое окно должно быть минимальное я сделал 20% от размеров окна
        stage.setMinHeight((sSize.height * 20d) / 100d);
        stage.setMinWidth((sSize.width * 20d) / 100d);


        stage.show();//вывод окна на экран при этом в контролере вызываем initialize() он говорит что нужно произвести перед открытием окна

        this.stage = stage;// выводим основное окно на внешний уровень чтобы мы могли с ним производить манипулации по типу блокировки закрития изменения размеров и тд тп
    }


}
