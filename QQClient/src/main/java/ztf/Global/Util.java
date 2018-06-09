package ztf.Global;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/8 21:36
 */
public class Util {
    public static void alertInformationDialog(String p_header, String p_message){
        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle("提示");
        _alert.setHeaderText(p_header);
        _alert.setContentText(p_message);
        _alert.show();
    }
    public static boolean alertChooseDialog(String p_header, String p_message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认信息对话框");
        alert.setHeaderText(p_header);
        alert.setContentText(p_message);

        Optional result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
}
