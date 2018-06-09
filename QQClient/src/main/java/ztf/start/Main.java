package ztf.start;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ztf.Global.ServerMessage;
import ztf.Server.LogOutServer;
import ztf.po.User;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        primaryStage.setTitle("QQ登录界面");
        primaryStage.setScene(new Scene(root, 500, 388));
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
    }


}
