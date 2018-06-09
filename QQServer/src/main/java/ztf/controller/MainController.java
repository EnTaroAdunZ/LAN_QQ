package ztf.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ztf.Global.ServerMessage;
import ztf.Server.ServerService;
import ztf.dao.UserDao;
import ztf.po.User;
import ztf.view.MainUI;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/6 13:33
 */
public class MainController implements Initializable {
    @FXML
    public TextField serverIp;
    @FXML
    public TextField onlineNum;
    @FXML
    public ListView<String> lv_userList;
    @FXML
    public ListView<String> lv_online_userList;
    public ObservableList<String> userList;
    public ObservableList<String> online_userList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userList=FXCollections.observableArrayList();
        online_userList=FXCollections.observableArrayList();
        lv_userList.setItems(userList);
        lv_online_userList.setItems(online_userList);
        MainUI.setUI(serverIp,onlineNum,userList,online_userList);
        MainUI.updateOnlineNum("0");
        MainUI.updateServerIp(ServerMessage.SERVER_IP);
        initUserList();
        new Thread(new ServerService()).start();
    }

    private void initUserList() {
        UserDao userDao=UserDao.getInstance();
        List<User> users = userDao.selectAll();
        for(User user:users){
            userList.addAll(user.toString());
        }
    }
}
