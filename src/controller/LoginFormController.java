package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public JFXTextField txtName;
    public JFXButton btnLog;
    public AnchorPane loginContexts;

    public static String name;


    public void LoggingOnAction(ActionEvent actionEvent) throws IOException {
        name = txtName.getText();
        txtName.clear();
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(LoginFormController.class.getResource("/view/client.fxml"))));
        stage.setTitle("Chat Application");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    public void enterOnAction(ActionEvent actionEvent) {
        btnLog.fire();
    }
}
