package ztf.Server;

import com.sun.security.ntlm.Server;
import ztf.Global.ClientMessage;
import ztf.Global.ServerMessage;
import ztf.bean.Link;
import ztf.bean.Msg;

import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/8 16:39
 */
public class LogOutServer implements Runnable{
    @Override
    public void run() {
        try{
            Socket socket=new Socket(ServerMessage.SERVER_IP,ServerMessage.SERVER_PORT);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(Msg.getCTCMsg_CTS_LOGOUT(new Link(ClientMessage.user,String.valueOf(ClientMessage.Client_PORT),ClientMessage.Client_IP)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
