package ztf.handler;

import ztf.Global.ClientMessage;
import ztf.Global.ServerMessage;
import ztf.bean.ChatListModel;
import ztf.bean.Link;
import ztf.bean.Msg;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/8 14:52
 */
public class ReceiveHandler implements Runnable{

    private Socket socket;

    public ReceiveHandler(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream is= new ObjectInputStream(socket.getInputStream());
            Msg msg=(Msg) is.readObject();
            if(msg.getType()==Msg.Type.STC_SENDPORT){
                ServerMessage.items.clear();
                for(Link link:msg.getPortList()){
                    if(!link.getUser().getUserName().equals(ClientMessage.USER_NAME)){
                        ServerMessage.items.add(new ChatListModel(link.getUser().getUserName(),link.getIp(),link.getPort()));
                    }
                }
            }else if(msg.getType()==Msg.Type.CTC){
                for( ChatListModel cm:ServerMessage.items){
                    if(cm.getUserName().equals(msg.getUser().getUserName())){
                        if(cm.getOpenFlag().equals("true")){
                            cm.getChatWinController().updataMsg(msg.getMsg());
                        }else{
                            cm.setMessage(cm.getMessage()+msg.getMsg());
                            cm.setMsgFlag("true");
                            new Thread(() -> {
                                try{
                                    cm.setFlicker("false");
                                    ServerMessage.lv_chatList.refresh();
                                    while(cm.getMsgFlag().equals("true")){
                                        cm.setFlicker("true");
                                        ServerMessage.lv_chatList.refresh();
                                        Thread.sleep(200);
                                        cm.setFlicker("false");
                                        ServerMessage.lv_chatList.refresh();
                                        Thread.sleep(200);
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }).start();
                        }
                        break;
                    }
                }
            }else if(msg.getType()==Msg.Type.CTC_SENDFILE){
                ClientMessage.chatListController.saveFile(msg.getLink(),msg.getFile());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
