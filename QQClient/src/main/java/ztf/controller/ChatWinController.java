package ztf.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import ztf.Global.ClientMessage;
import ztf.Global.ServerMessage;
import ztf.Global.Status;
import ztf.Global.Util;
import ztf.Server.SendFileServer;
import ztf.Server.SendServer;
import ztf.bean.ChatListModel;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/8 17:39
 */
public class ChatWinController implements Initializable {
    private final Image IMAGE1 = new Image("http://othgjp7hs.bkt.clouddn.com/18-6-8/85814151.jpg");
    private final Image IMAGE2 = new Image("http://othgjp7hs.bkt.clouddn.com/18-6-8/86481160.jpg");
    private final Image IMAGE3 = new Image("http://othgjp7hs.bkt.clouddn.com/18-6-8/5502061.jpg");
    private final Image IMAGE4 = new Image("http://othgjp7hs.bkt.clouddn.com/18-6-8/92563047.jpg");
    private final Image IMAGE5 = new Image("http://othgjp7hs.bkt.clouddn.com/18-6-8/81909198.jpg");
    @FXML
    public ImageView iv_file;
    @FXML
    public Label lb_name;
    @FXML
    public ImageView iv_sound;
    @FXML
    public ImageView iv_video;
    @FXML
    public ImageView iv_show1;
    @FXML
    public ImageView iv_show2;
    @FXML
    public TextArea ta_wait_sendMessage;
    @FXML
    public TextArea ta_message;
    private Window window;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    private String name;
    private String port;
    private String ip;
    ExecutorService es =null;

    public void setVavlue(String name,String ip,String port,Window window){
        this.name=name;
        this.port=port;
        this.ip=ip;
        this.window=window;
        lb_name.setText(name);
        loadMsg();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        iv_file.setImage(IMAGE1);
        iv_video.setImage(IMAGE2);
        iv_sound.setImage(IMAGE3);
        iv_show1.setImage(IMAGE4);
        iv_show2.setImage(IMAGE5);
        es = Executors.newSingleThreadExecutor();

    }
    @FXML
    public void sendFile(MouseEvent mouseEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("打开需要传送的文件");
            File file = fileChooser.showOpenDialog(window);
            Future<Status> submit = es.submit(new SendFileServer(ClientMessage.Client_IP, Integer.valueOf(port), ClientMessage.USER_NAME, file));
            submit.get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void sendMessage(MouseEvent mouseEvent) {
        try {
            String text = ta_wait_sendMessage.getText();
            StringBuilder sb=new StringBuilder();
            sb.append(sdf.format(new Date()));
            sb.append(" "+ClientMessage.USER_NAME+"\t:\n");
            sb.append(text+"\n");
            Future<Status> future =es.submit(new SendServer(sb.toString(),ip,Integer.valueOf(port),ClientMessage.USER_NAME));
            Status status = future.get();
            if(status==Status.Send_Success){
                ta_wait_sendMessage.setText("");
                StringBuilder stringBuilder=new StringBuilder(ta_message.getText());
                stringBuilder.append(sdf.format(new Date()));
                stringBuilder.append(" "+ClientMessage.USER_NAME+"\t:\n");
                stringBuilder.append(text+"\n");
                ta_message.setText(stringBuilder.toString());
                storeMsg(stringBuilder.toString());
            }else{
                Util.alertInformationDialog("错误","你的信息发送失败，请重试！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //将收到的信息保存起来
    public void storeMsg(String message){
        for( ChatListModel cm:ServerMessage.items){
            if(cm.getUserName().equals(lb_name.getText())){
                cm.setMessage(message);
                break;
            }
        }
    }

    public void updataMsg(String append){
        ta_message.setText(ta_message.getText()+append);
    }

    public void loadMsg(){
        for( ChatListModel cm:ServerMessage.items){
            if(cm.getUserName().equals(lb_name.getText())){
                cm.setChatWinController(this);
                ta_message.setText(cm.getMessage());
                break;
            }
        }
    }

}
