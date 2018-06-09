package ztf.Global;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import ztf.bean.ChatListModel;
import ztf.bean.Link;

import java.util.ArrayList;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/6 15:42
 */
public class ServerMessage {
    public static String SERVER_IP="localhost";
    public static Integer SERVER_PORT=8081;
    public static ListView<ChatListModel> lv_chatList;
    public static ObservableList<ChatListModel> items =FXCollections.observableArrayList ();
}
