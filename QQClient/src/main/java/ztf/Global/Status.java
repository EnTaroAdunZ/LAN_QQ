package ztf.Global;

import sun.security.util.Password;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/8 14:17
 */
public enum  Status {
    Account_Password_Error(101,"帐号密码错误"),
    Connection_Error(102,"连接错误"),
    Send_Success(103,"发送成功"),
    Send_Error(104,"发送失败"),
    Success(100,"连接成功");
    private String msg;
    private Integer code;
    Status(Integer code, String msg){
        this.code=code;
        this.msg=msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
