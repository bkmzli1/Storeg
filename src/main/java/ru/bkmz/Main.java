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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.bkmz.sql.BD;

import java.awt.*;
import java.io.*;
import java.util.Objects;

public class Main extends Application {
    public static Stage stage;
    final String name = "storeg";
    protected static final Logger logger = LogManager.getLogger();
    public static String FileSeve = System.getenv("APPDATA") + "\\.Storeg",
            SQLFile = FileSeve + "\\journal.sqlite";
    public static BD bd;

    static  SplashScreen splash ;
    static Graphics2D g ;
    static Rectangle rectangle ;

    public static void main(String[] args) throws IOException {
        try {
            splash = SplashScreen.getSplashScreen();
            g = splash.createGraphics();
            rectangle = splash.getBounds();
            g.fillRect(0, 0, rectangle.width * 0 / 100, 20);
            splash.update();
            g.setColor(Color.GREEN);

            logger.info("start:launch");
            launch(args);
            logger.info("stop:launch");
            System.exit(0);
        }catch (Exception e){
            FileWriter fileWriter = new FileWriter("123.txt");
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            String sStackTrace = sw.toString(); // stack trace as a string
            System.out.println(sStackTrace);
            fileWriter.write(sStackTrace);
            fileWriter.close();
            System.exit(0);
        }

    }

    @Override
    public void init() {
        splashUP(10);
        try {
            bd = new BD(SQLFile);
        } catch (ClassNotFoundException e) {
            try {
                File file = new File(FileSeve);
                if (!file.exists()) {
                    file.mkdir();
                }
                FileWriter fileWriter = new FileWriter(SQLFile);
                fileWriter.write(SQLFile);
                try {
                    bd = new BD(SQLFile);
                } catch (ClassNotFoundException ex) {
                    logger.error("ClassNotFoundException: ", ex);
                }
            } catch (IOException ex) {
                logger.error("IOException: ", ex);
            }
        }

    }

    @Override
    public void start(Stage stage) {
        splashUP(30);
        logger.info("start loader FXML");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/main.fxml")));
        try {
            loader.load();
        } catch (IOException e) {
            logger.warn("load fxml", e);
        }

        Parent root = loader.getRoot();
        logger.info("stop loader FXML");
        Scene scene = new Scene(root);
        splashUP(50);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css"); //(3)
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("css/main.css")).toExternalForm());

        stage.setScene(scene);
        stage.setTitle(name);
        InputStream inputStream = ClassLoader.class.getResourceAsStream("/img/icon.png");
        try {
            Image image = new Image(inputStream);
            stage.getIcons().add(image);
        } catch (NullPointerException e) {
            logger.warn("img null");
        }
        splashUP(60);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                Platform.exit();
            }
        });
        splashUP(80);
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        stage.setHeight((sSize.height * 50d) / 100d);
        stage.setWidth((sSize.width * 50d) / 100d);
        stage.setMinHeight((sSize.height * 20d) / 100d);
        stage.setMinWidth((sSize.width * 20d) / 100d);
        splashUP(100);
        splash.close();

        stage.show();

        this.stage = stage;
    }

    static void splashUP(int size) {
        g.fillRect(0, 0, rectangle.width * size / 100, 20);
        splash.update();
    }
}
//-splash:src\main\resources\load.gif