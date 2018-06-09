package ztf.handler;


import javafx.application.Platform;
import ztf.Global.ServerMessage;
import ztf.bean.Link;
import ztf.bean.Msg;
import ztf.dao.UserDao;
import ztf.po.User;
import ztf.view.MainUI;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/6 16:10
 */
public class ServerReceiveHandle implements Runnable {
    private Socket socket;

    public ServerReceiveHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Link link = null;
        try {
            //连接
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            Msg msg = (Msg) is.readObject();
            if (msg.getType() == Msg.Type.CTS_LOGIN) {
                //客户端给服务器发来了端口
                UserDao userDao = UserDao.getInstance();
                User user = userDao.selectByAccount(msg.getUser().getAccount());
                if (user == null || !user.getPassword().equals(msg.getUser().getPassword())) {
                    //当前用户不存在或者密码错误
                    os.writeObject(Msg.getSTC_LOGIN("101", null));
                    os.writeObject(null);

                } else {
                    //开始检测是否已经登录
                    ArrayList<Link> linkList = ServerMessage.linkList;
                    if (!linkList.contains(new Link(msg.getUser()))) {
                        link = new Link(user, msg.getMsg(), socket.getInetAddress().getHostAddress());
                        linkList.add(link);
                        Link finalLink = link;
                        //更新UI
                        Platform.runLater(
                                () -> {
                                    MainUI.online_userList.add(finalLink.toString());
                                    MainUI.updateOnlineNum(String.valueOf(linkList.size()));
                                }
                        );
                        os.writeObject(Msg.getSTC_LOGIN("100", user));
                        os.writeObject(null);
                        new Thread(() -> {
                            for (Link link12 : linkList) {
                                //给所有客户端发送在线用户信息
                                new Thread(new STCSendPortHandle(link12.getIp(), link12.getPort(), Msg.getSTCMsg_sendPortToC(linkList))).start();
                            }
                        }).start();
                    } else {
                        //帐号已经登录
                        os.writeObject(Msg.getSTC_LOGIN("102", null));
                        os.writeObject(null);

                    }
                }
            }else if (msg.getType() == Msg.Type.CTS_LOGOUT) {
                Link tempLink = msg.getLink();
                Platform.runLater(
                        () -> {
                            MainUI.online_userList.remove(tempLink.toString());
                        }
                );

                ArrayList<Link> linkList = ServerMessage.linkList;
                linkList.remove(tempLink);
                //删除在线列表
                Platform.runLater(
                        () -> {
                            MainUI.updateOnlineNum(String.valueOf(ServerMessage.linkList.size()));
                        }
                );
                new Thread(() -> {
                    for (Link link1 : linkList) {
                        //给所有客户端发送在线用户信息
                        new Thread(new STCSendPortHandle(link1.getIp(), link1.getPort(), Msg.getSTCMsg_sendPortToC(linkList))).start();
                    }
                }).start();
            }
        } catch (SocketException e) {
            e.printStackTrace();
            if(link!=null){
                //从视图移除
                Link finalLink = link;
                Platform.runLater(
                        () -> {
                            MainUI.online_userList.remove(finalLink.toString());
                        }
                );
                ArrayList<Link> linkList = ServerMessage.linkList;
                linkList.remove(link);
                //删除在线列表
                Platform.runLater(
                        () -> {
                            MainUI.updateOnlineNum(String.valueOf(ServerMessage.linkList.size()));
                        }
                );
                new Thread(() -> {
                    for (Link link1 : linkList) {
                        //给所有客户端发送在线用户信息
                        new Thread(new STCSendPortHandle(link1.getIp(), link1.getPort(), Msg.getSTCMsg_sendPortToC(linkList))).start();
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
