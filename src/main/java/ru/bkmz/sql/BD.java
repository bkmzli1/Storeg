package ru.bkmz.sql;


import java.sql.*;

//Этот класс подключает БД
public class BD {
    private Connection conn;//Эта переменная является по суте указателем где находится БД
    //это конструктор класса он нужен что бы  при вызове класса в Main подключалась БД
    // также он принемает строку (пут к БД)
    // throws  это иключение только в отличии от try{}catch() он выводит исключение из функции это нужно чтобы при первом запуске нету папки где будет БД
    // ClassNotFoundException - это если    Class.forName("org.sqlite.JDBC"); нет
    // SQLException - это если БД говорит об ошибке
    public BD(String fileConn) throws ClassNotFoundException, SQLException {

            Class.forName("org.sqlite.JDBC");//подключаем API для БД
            conn = DriverManager.getConnection("jdbc:sqlite:" + fileConn);// подключение к БД "jdbc:sqlite:" (тип БД) + fileConn (адрес БД)


    }
    //функция для быстрого создания запросса и его закрытия (запрос на создания чего либо)
    public void setBD(String inquiry) throws SQLException {

        Statement statmt = conn.createStatement();//Statement - это интерфейс для запросов к БД
        //Интерфе́йс (от англ. interface) — общая граница между двумя функциональными объектами, требования к которой определяются стандартом[1]; совокупность средств, методов и правил
        // взаимодействия (управления, контроля и т. д.) между элементами системы[2]. из вики я сам хз что это
        statmt.execute(inquiry);//сам запрос

        statmt.close();//закрываем запрос так как он нам большне не нужен

    }

// получем Connection conn; для работы сним в не класса БД
    public Connection getConn() {
        return conn;
    }
}
