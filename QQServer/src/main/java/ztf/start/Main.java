package ztf.start;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ztf.Global.ServerMessage;
import ztf.dao.UserDao;
import ztf.po.User;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        primaryStage.setTitle("QQ服务端 by 201525010627");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        getServerIp();
        initData();
        launch();
    }

    public static void getServerIp(){
        try {
            Enumeration<NetworkInterface> interfaces=null;
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                Enumeration<InetAddress> addresss = ni.getInetAddresses();
                while(addresss.hasMoreElements())
                {
                    InetAddress nextElement = addresss.nextElement();
                    String hostAddress = nextElement.getHostAddress();
                    if(hostAddress.startsWith("172")){
                        ServerMessage.SERVER_IP=hostAddress;
                        System.out.println("服务端IP地址为：" +hostAddress);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initData(){
        UserDao userDao=UserDao.getInstance();
        userDao.insert(new User("ztf1","ztf1","腾讯"));
        userDao.insert(new User("ztf2","ztf2","今日头条"));
        userDao.insert(new User("ztf3","ztf3","阿里"));
        userDao.insert(new User("ztf4","ztf4","网易"));
        userDao.insert(new User("ztf5","ztf5","ZTF"));
    }

}
