package ztf.Server;

import ztf.Global.ClientMessage;
import ztf.handler.ReceiveHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/8 14:44
 */
public class ReceiveServer implements Runnable {

    @Override
    public void run() {
        try {
            int index = new Random().nextInt(30000)+10000;
            ClientMessage.Client_PORT=index;
            ServerSocket serverSocket=new ServerSocket(index);
            while(true){
                Socket accept = serverSocket.accept();
                new Thread(new ReceiveHandler(accept)).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
