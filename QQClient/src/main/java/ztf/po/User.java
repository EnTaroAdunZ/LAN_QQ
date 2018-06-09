package ztf.po;

import java.io.Serializable;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/6 13:45
 */
public class User implements Serializable {
    private String account;
    private String password;
    private String userName;

    @Override
    public int hashCode() {
        return account.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        User other=(User)obj;
        return this.account.equals(other.getAccount());
    }

    public User(String account, String password, String userName) {
        this.account = account;
        this.password = password;
        this.userName = userName;
    }

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "用户名='" + account + '\'' +
                ", 密码='" + password + '\'' +
                ", 昵称='" + userName;
    }
}
