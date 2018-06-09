package ztf.controller;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import ztf.Global.ClientMessage;
import ztf.Global.ServerMessage;
import ztf.Global.Util;
import ztf.bean.ChatListModel;
import ztf.bean.Link;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/8 16:40
 */
public class ChatListController implements Initializable {
    @FXML
    public ImageView iv_head;
    @FXML
    public Label lb_name;
    @FXML
    public Label lb_ip;
    @FXML
    public Label lb_port;
    @FXML
    ListView<ChatListModel> lv_chatList;
    private Window window;

    public void setValue(Window window){
        this.window=window;
    }

    private final Image IMAGE1 = new Image("http://othgjp7hs.bkt.clouddn.com/18-6-8/2780393.jpg");
    private final Image IMAGE2 = new Image("http://othgjp7hs.bkt.clouddn.com/18-6-8/36061669.jpg");
    private final Image IMAGE3 = new Image("http://othgjp7hs.bkt.clouddn.com/18-6-8/35725819.jpg");
    private final Image IMAGE4 = new Image("http://othgjp7hs.bkt.clouddn.com/18-6-8/24494195.jpg");
    private final Image IMAGE5 = new Image("http://othgjp7hs.bkt.clouddn.com/18-6-8/51868892.jpg");
    private final Image IMAGE6 = new Image("http://othgjp7hs.bkt.clouddn.com/18-6-8/16875648.jpg");
    private final Image IMAGEWHITE = new Image("http://othgjp7hs.bkt.clouddn.com/18-6-8/75659527.jpg");
    private Image[] listOfImages = {IMAGE1, IMAGE2, IMAGE3,IMAGE4,IMAGE5,IMAGE6};
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClientMessage.chatListController=this;
        int index = new Random().nextInt(listOfImages.length);
        iv_head.setImage(listOfImages[index]);
        iv_head.setFitHeight(100);
        iv_head.setFitWidth(100);
        lb_name.setText(ClientMessage.USER_NAME);
        lb_ip.setText(ClientMessage.Client_IP);
        lb_port.setText(String.valueOf(ClientMessage.Client_PORT));
        ServerMessage.lv_chatList=lv_chatList;
        lv_chatList.setItems(ServerMessage.items);
        lv_chatList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                try {
                    ChatListModel selectedItem = lv_chatList.getSelectionModel()
                            .getSelectedItem();
                    if(selectedItem.getOpenFlag().equals("false")){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chatWin.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        Stage stg=new Stage();
                        stg.setTitle(ClientMessage.USER_NAME+"的聊天框");
                        stg.setScene(scene);
                        selectedItem.setOpenFlag("true");
                        selectedItem.setMsgFlag("false");
                        stg.setOnCloseRequest(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent event) {
                                selectedItem.setOpenFlag("false");
                            }
                        });
                        stg.show();
                        ChatWinController chatWinController= (ChatWinController) loader.getController();
                        chatWinController.setVavlue(selectedItem.getUserName(),selectedItem.getIp(),selectedItem.getPort(),stg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        lv_chatList.setCellFactory(param -> new ListCell<ChatListModel>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(ChatListModel chatListModel, boolean empty) {
                super.updateItem(chatListModel, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if(chatListModel.getInit().equals("false")){
                        chatListModel.setInit("ture");
                        int index = new Random().nextInt(listOfImages.length);
                        imageView.setImage(listOfImages[index]);
                        chatListModel.setHeadPortrait(String.valueOf(index));
                        imageView.setFitHeight(56);
                        imageView.setFitWidth(56);
                        setText(chatListModel.getUserName());
                        setGraphic(imageView);
                    }else{
                        if(chatListModel.getMsgFlag().equals("true")){
                            if(chatListModel.getFlicker().equals("true")){
                                imageView.setImage(IMAGEWHITE);
                                imageView.setFitHeight(56);
                                imageView.setFitWidth(56);
                                setText(chatListModel.getUserName());
                                setGraphic(imageView);
                            }else{
                                normalHeadPortrait(chatListModel);
                            }
                        }else{
                            normalHeadPortrait(chatListModel);
                        }
                    }
                }

            }

            private void normalHeadPortrait(ChatListModel chatListModel) {
                imageView.setImage(listOfImages[Integer.valueOf(chatListModel.getHeadPortrait())]);
                imageView.setFitHeight(56);
                imageView.setFitWidth(56);
                setText(chatListModel.getUserName());
                setGraphic(imageView);
            }
        });
    }

    public void saveFile(Link link, File file){
        Platform.runLater(() -> {
            boolean result = Util.alertChooseDialog("文件接收通知"
                    , "来自" + link.getIp() + "的" + link.getUser().getUserName() + "要给你传输文件，是否同意？");
            if(result){
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("选择文件保存位置");
                fileChooser.setInitialFileName(file.getName());
                File saveFile = fileChooser.showSaveDialog(window);
                new Thread(()->{
                    try (FileInputStream fileInputStream=new FileInputStream(file);
                         FileOutputStream fileOutputStream=new FileOutputStream(saveFile)){
                        byte[] b = new byte[2048];
                        int read;
                        while ((read = fileInputStream.read(b)) != -1) {
                            fileOutputStream.write(b,0,read);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }).start();
            }else{
                Util.alertInformationDialog("成功取消文件传送","你已经成功取消来自" + link.getIp() + "的" + link.getUser().getUserName()+"的文件传输");
            }
        });

    }

}
