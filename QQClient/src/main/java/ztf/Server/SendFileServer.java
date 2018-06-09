package ztf.Server;

import javafx.stage.FileChooser;
import ztf.Global.Status;
import ztf.bean.Link;
import ztf.bean.Msg;
import ztf.po.User;

import java.io.File;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/8 23:35
 */
public class SendFileServer  implements Callable<Status> {
    private String ip;
    private Integer port;
    private String userName;
    private File file;

    public SendFileServer(String ip, Integer port,String userName,File file) {
        this.ip = ip;
        this.port = port;
        this.userName=userName;
        this.file=file;
    }

    @Override
    public Status call() throws Exception {
        try {
            Socket socket = new Socket(ip,port);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(Msg.getCTC_SENDFILE(new Link(new User(null,null,userName),String.valueOf(port),ip),file));
            os.writeObject(null);
            return Status.Send_Success;
        }catch (Exception e){
            e.printStackTrace();
            return Status.Send_Error;
        }
    }
}
