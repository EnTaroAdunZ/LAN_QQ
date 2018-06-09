//package ztf.start;
//
//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.EventHandler;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.ListCell;
//import javafx.scene.control.ListView;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import ztf.bean.ChatListModel;
//
//import java.util.Random;
//
///**
// * @Description:
// * @Author:ZTF
// * @Date:2018/6/6 18:06
// */
//public class ListViewWithImages extends Application {
//
//    private final Image IMAGE1 = new Image("http://othgjp7hs.bkt.clouddn.com/18-6-8/2780393.jpg");
//    private final Image IMAGE2 = new Image("http://othgjp7hs.bkt.clouddn.com/18-6-8/36061669.jpg");
//    private final Image IMAGE3 = new Image("http://othgjp7hs.bkt.clouddn.com/18-6-8/35725819.jpg");
//    private final Image IMAGEWHITE = new Image("http://othgjp7hs.bkt.clouddn.com/18-6-8/75659527.jpg");
//    private Image[] listOfImages = {IMAGE1, IMAGE2, IMAGE3};
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//
//        ListView<ChatListModel> listView = new ListView<>();
//        ObservableList<ChatListModel> items =FXCollections.observableArrayList (
//               new ChatListModel("aaa","1","123"),
//                new ChatListModel("bbb","2","123"),
//                new ChatListModel("ccc","3","123")
//        );
//        listView.setItems(items);
//        listView.setOnMouseClicked(click -> {
//            if (click.getClickCount() == 2) {
//                ChatListModel selectedItem = listView.getSelectionModel()
//                        .getSelectedItem();
//                System.out.println(selectedItem);
//            }
//        });
//        listView.setCellFactory(param -> new ListCell<ChatListModel>() {
//            private ImageView imageView = new ImageView();
//            @Override
//            public void updateItem(ChatListModel chatListModel, boolean empty) {
//                super.updateItem(chatListModel, empty);
//                if (empty) {
//                    setText(null);
//                    setGraphic(null);
//                } else {
//                    if(chatListModel.getInit().equals("false")){
//                        chatListModel.setInit("ture");
//                        int index = new Random().nextInt(listOfImages.length);
//                        imageView.setImage(listOfImages[index]);
//                        chatListModel.setHeadPortrait(String.valueOf(index));
//                        imageView.setFitHeight(56);
//                        imageView.setFitWidth(56);
//                        setText(chatListModel.getUserName());
//                        setGraphic(imageView);
//                    }else{
//                        if(chatListModel.getMsgFlag().equals("true")){
//                            if(chatListModel.getFlicker().equals("true")){
//                                imageView.setImage(IMAGEWHITE);
//                                imageView.setFitHeight(56);
//                                imageView.setFitWidth(56);
//                                setText(chatListModel.getUserName());
//                                setGraphic(imageView);
//                            }else{
//                                normalHeadPortrait(chatListModel);
//                            }
//                        }else{
//                            normalHeadPortrait(chatListModel);
//                        }
//                    }
//                }
//
//            }
//
//            private void normalHeadPortrait(ChatListModel chatListModel) {
//                imageView.setImage(listOfImages[Integer.valueOf(chatListModel.getHeadPortrait())]);
//                imageView.setFitHeight(56);
//                imageView.setFitWidth(56);
//                setText(chatListModel.getUserName());
//                setGraphic(imageView);
//            }
//        });
//
//        new Thread(() -> {
//            try{
//                Thread.sleep(4000);
//                while(true){
//                    items.get(0).setMsgFlag("true");
//                    items.get(1).setMsgFlag("true");
//                    items.get(2).setMsgFlag("true");
//
//
//                    for(int i=0;i<10;i++){
//                        items.get(0).setFlicker("true");
//                        items.get(1).setFlicker("true");
//                        items.get(2).setFlicker("true");
//                        listView.refresh();
//                        Thread.sleep(200);
//                        items.get(0).setFlicker("false");
//                        items.get(1).setFlicker("false");
//                        items.get(2).setFlicker("false");
//                        listView.refresh();
//                        Thread.sleep(200);
//                    }
//
//                    items.get(0).setMsgFlag("false");
//                    items.get(1).setMsgFlag("false");
//                    items.get(1).setMsgFlag("false");
//                    listView.refresh();
//                    Thread.sleep(4000);
//                }
//            }catch (Exception e){
//            }
//        }).start();
//
//
//        VBox box = new VBox(listView);
//        box.setAlignment(Pos.CENTER);
//        Scene scene = new Scene(box, 200, 200);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
