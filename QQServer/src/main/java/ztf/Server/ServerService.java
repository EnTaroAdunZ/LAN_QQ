package ztf.Server;

import ztf.Global.ServerMessage;
import ztf.handler.ServerReceiveHandle;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/6 16:08
 */
public class ServerService implements Runnable{

    @Override
    public void run() {
        try (ServerSocket serverSocket=new ServerSocket(ServerMessage.SERVER_PORT)){
            while(true) {
                Socket socket=serverSocket.accept();
                //交由SocketHandle处理
                ServerReceiveHandle handle=new ServerReceiveHandle(socket);
                Thread thread=new Thread(handle);
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("服务器运行过程中出现了错误！");
        }
    }
}
