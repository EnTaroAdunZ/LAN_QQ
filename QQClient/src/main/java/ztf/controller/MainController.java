package ztf.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import ztf.Global.ClientMessage;
import ztf.Global.Status;
import ztf.Global.Util;
import ztf.Server.LoadOnServer;
import ztf.Server.LogOutServer;
import ztf.Server.ReceiveServer;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/6 13:33
 */
public class MainController implements Initializable {
    @FXML
    public TextField tf_serverIP;
    @FXML
    public PasswordField pf_password;
    @FXML
    public TextField tf_account;
    ExecutorService es = null;
    @FXML
    public void loadOn(ActionEvent event){
        try {
            String account=tf_account.getText();
            String password=pf_password.getText();
            String serverIP=tf_serverIP.getText();
            Future<Status> future =es.submit(new LoadOnServer(account,password,serverIP));
            Status status = future.get();
            if(status==Status.Account_Password_Error){
                Util.alertInformationDialog("错误",status.getMsg());
            }else if(status==Status.Connection_Error){
                Util.alertInformationDialog("错误",status.getMsg());
            }else if(status==Status.Success){
                Window window = ((Node) (event.getSource())).getScene().getWindow();
                window.hide();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/list.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stg=new Stage();
                stg.setTitle(ClientMessage.USER_NAME +"的QQ");
                stg.setOnCloseRequest(event1 -> {
                    new Thread(new LogOutServer()).start();
                    try {
                        Thread.sleep(500);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    System.exit(0);
                });
                stg.setScene(scene);
                stg.show();
                ChatListController chatListController= (ChatListController) loader.getController();
                chatListController.setValue(stg);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        es = Executors.newSingleThreadExecutor();
        new Thread(new ReceiveServer()).start();
    }
}
