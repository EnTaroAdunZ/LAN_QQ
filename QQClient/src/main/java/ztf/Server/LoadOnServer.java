package ztf.Server;

import ztf.Global.ClientMessage;
import ztf.Global.ServerMessage;
import ztf.Global.Status;
import ztf.bean.Msg;
import ztf.po.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/8 14:15
 */
public class LoadOnServer implements Callable<Status> {
    private String account;
    private String password;
    private String serverIP;

    public LoadOnServer(String account, String password, String serverIP) {
        this.account = account;
        this.password = password;
        this.serverIP = serverIP;
    }

    @Override
    public Status call()  {
        try {
            Socket socket=new Socket(serverIP,ServerMessage.SERVER_PORT);
            ClientMessage.Client_IP=socket.getLocalAddress().toString().substring(1);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            os.writeObject(Msg.getCTSMsg_LOGIN(new User(account, password),ClientMessage.Client_PORT));
            os.writeObject(null);
            Msg msg=(Msg)ois.readObject();
            if(msg.getMsg().equals("100")){
                ClientMessage.USER_NAME=msg.getUser().getUserName();
                ClientMessage.user=msg.getUser();
                ServerMessage.SERVER_IP=serverIP;
                return Status.Success;
            }else if(msg.getMsg().equals("101")){
                return Status.Account_Password_Error;
            }else if(msg.getMsg().equals("102")){
                return Status.Connection_Error;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Status.Connection_Error;
    }
}
