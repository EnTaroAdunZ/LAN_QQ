package ztf.bean;

import ztf.po.User;

import java.io.Serializable;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/6 16:17
 */
public class Link implements Serializable {
    private User user;
    private String port;
    private String ip;

    @Override
    public boolean equals(Object obj) {
        Link other = (Link) obj;
        return this.user.equals(other.getUser());
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    public Link(User user){
        this.user=user;
    }
    public Link(){

    }

    public Link(User user, String port, String ip) {
        this.user = user;
        this.port = port;
        this.ip = ip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "用户名='" + user.getAccount() + '\'' +
                "IP='" + ip + '\'' +
                "端口='" + port + '\'' +
                ", 昵称='" + user.getUserName()+'\'';
    }
}
