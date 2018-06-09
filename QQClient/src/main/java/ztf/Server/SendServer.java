package ztf.Server;

import ztf.Global.Status;
import ztf.bean.Msg;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/8 21:22
 */
public class SendServer implements Callable<Status> {

    private String msg;
    private String ip;
    private Integer port;
    private String userName;

    public SendServer(String msg, String ip, Integer port,String userName) {
        this.msg = msg;
        this.ip = ip;
        this.port = port;
        this.userName=userName;
    }

    @Override
    public Status call() throws Exception {
        try {
            Socket socket = new Socket(ip,port);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(Msg.getCTCMsg(msg,userName));
            os.writeObject(null);
            return Status.Send_Success;
        }catch (Exception e){
            e.printStackTrace();
            return Status.Send_Error;
        }
    }
}
