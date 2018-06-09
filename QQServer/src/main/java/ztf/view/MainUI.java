package ztf.view;

import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ztf.Global.ServerMessage;

import java.net.Socket;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/6 15:40
 */
public class MainUI {
    public static TextField serverIp;
    public static TextField onlineNum;
    public static ObservableList<String> userList;
    public static ObservableList<String> online_userList;
    public static void setUI( TextField serverIp,TextField onlineNum,ObservableList<String> userList,ObservableList<String> online_userList) {
        MainUI.serverIp = serverIp;
        MainUI.onlineNum = onlineNum;
        MainUI.userList = userList;
        MainUI.online_userList = online_userList;
    }
    public static void updateServerIp(String serverIp) {
        MainUI.serverIp.setText(serverIp);
    }
    public static void updateOnlineNum(String onlineNum) {
        MainUI.onlineNum.setText(onlineNum);
    }
}
