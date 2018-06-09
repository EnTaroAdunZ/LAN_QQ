package ztf.bean;

import ztf.po.User;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/6 16:06
 */
public class Msg implements Serializable {
    public enum Type {
        CTC,CTS_SENDPORT,STC_SENDPORT,CTS_LOGIN,CTS_LOGOUT,CTC_SENDFILE
    }
    private Type type;
    private String msg;
    private User user;
    private Link link;
    private File file;
    private ArrayList<Link> portList;
    public static Msg getCTC_SENDFILE(Link link,File file) {
        return new Msg(Type.CTC_SENDFILE,link,file);
    }
    public static Msg getSTCMsg_sendPortToC(ArrayList<Link> linkList) {
        return new Msg(Type.STC_SENDPORT,linkList);
    }
    public static Msg getCTCMsg(String p_msg,String userName) {
        return new Msg(Type.CTC,p_msg,userName);
    }
    public static Msg getCTCMsg_CTS_LOGOUT(Link link) {
        return new Msg(Type.CTS_LOGOUT,link);
    }
    public static Msg getCTSMsg_sendPortToS(String port) {
        return new Msg(Type.CTS_SENDPORT,port);
    }
    public static Msg getSTC_LOGIN(String status,User user) {
        return new Msg(Type.CTS_LOGIN,status,user);
    }
    public static Msg getCTSMsg_LOGIN(User user,Integer port) {
        return new Msg(Type.CTS_LOGIN,String.valueOf(port),user);
    }
    public Msg(Type type,Link link, File file) {
        super();
        this.type = type;
        this.link=link;
        this.file = file;
    }
    public Msg(Type type, Link link) {
        super();
        this.type = type;
        this.link = link;
    }
    public Msg(Type type, String msg,User user) {
        super();
        this.type = type;
        this.msg = msg;
        this.user=user;
    }
    public Msg(Type type, String msg) {
        super();
        this.type = type;
        this.msg = msg;
    }
    public Msg(Type type, String msg, String userName) {
        super();
        this.type = type;
        this.msg = msg;
        this.user=new User(null,null,userName);
    }
    public Msg(Type type, ArrayList<Link> linkList) {
        super();
        this.type = type;
        this.portList = linkList;
    }
    public Type getType() {
        return type;
    }
    public String getMsg() {
        return msg;
    }
    public User getUser() {
        return user;
    }
    public ArrayList<Link> getPortList() {
        return portList;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public void setPortList(ArrayList<Link> portList) {
        this.portList = portList;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

