package ztf.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ztf.controller.ChatWinController;

import java.io.Serializable;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/8 8:21
 */
public class ChatListModel implements Serializable {
    private final StringProperty userName;
    private final  StringProperty ip;
    private final  StringProperty port;
    //头像序号
    private final  StringProperty headPortrait;
    //是否有未读信息
    private final  StringProperty msgFlag;
    //当前是否闪烁白色状态
    private final  StringProperty flicker;
    //是否已经初始化
    private final  StringProperty init;
    //窗口是否已经打开
    private final StringProperty openFlag;
    //当前存储的消息
    private String message;
    private ChatWinController chatWinController;

    {
        this.msgFlag = new SimpleStringProperty("false");
        this.flicker = new SimpleStringProperty("false");
        this.init=new SimpleStringProperty("false");
        this.headPortrait=new SimpleStringProperty("null");
        this.openFlag=new SimpleStringProperty("false");
        message=new String();
        chatWinController=null;
    }

    public ChatListModel(String userName, String ip, String port ) {
        this.userName = new SimpleStringProperty(userName);
        this.ip = new SimpleStringProperty(ip);
        this.port = new SimpleStringProperty(port);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOpenFlag() {
        return openFlag.get();
    }

    public StringProperty openFlagProperty() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag.set(openFlag);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getIp() {
        return ip.get();
    }

    public StringProperty ipProperty() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip.set(ip);
    }

    public String getPort() {
        return port.get();
    }

    public StringProperty portProperty() {
        return port;
    }

    public void setPort(String port) {
        this.port.set(port);
    }

    public String getHeadPortrait() {
        return headPortrait.get();
    }

    public StringProperty headPortraitProperty() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait.set(headPortrait);
    }

    public String getMsgFlag() {
        return msgFlag.get();
    }

    public StringProperty msgFlagProperty() {
        return msgFlag;
    }

    public void setMsgFlag(String msgFlag) {
        this.msgFlag.set(msgFlag);
    }

    public String getFlicker() {
        return flicker.get();
    }

    public StringProperty flickerProperty() {
        return flicker;
    }

    public void setFlicker(String flicker) {
        this.flicker.set(flicker);
    }

    public String getInit() {
        return init.get();
    }

    public StringProperty initProperty() {
        return init;
    }

    public void setInit(String init) {
        this.init.set(init);
    }

    public ChatWinController getChatWinController() {
        return chatWinController;
    }

    public void setChatWinController(ChatWinController chatWinController) {
        this.chatWinController = chatWinController;
    }

    @Override
    public String toString() {
        return "ChatListModel{" +
                "userName=" + userName +
                ", ip=" + ip +
                ", port=" + port +
                ", headPortrait=" + headPortrait +
                '}';
    }
}
