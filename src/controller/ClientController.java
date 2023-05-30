package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;


public class ClientController {
    public Label lblClientName;
    public JFXTextField txtType;
    public ScrollPane txtField;
    public AnchorPane context = new AnchorPane();


//    final int PORT = 500;
    public AnchorPane emojiPane;
    public JFXButton button_send;
    public VBox vbox_messages;


    private Client client;

    public void initialize(){

        lblClientName.setText(LoginFormController.name);


        try{
            client = new Client(new Socket("localhost",500));
            System.out.println("Connected to Server");
        }catch (IOException e){}

        vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                txtField.setVvalue((Double) newValue);
            }
        });

//        client.recieveMessageFromServer(vbox_messages);

        client.receiveMessageFromServer(vbox_messages);
        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String messageToSend = txtType.getText();
                if(!messageToSend.isEmpty()){
                    HBox hBox= new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);

                    hBox.setPadding(new Insets(5,5,5,10));


                    javafx.scene.text.Text text = new javafx.scene.text.Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);


                    textFlow.setStyle("-fx-color: rgb(239,242,255);" +
                            "-fx-background-color: rgb(15,125,242);" +
                            " -fx-background-radius: 20px");



                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.color(0.934, 0.945, 0.996));

                    hBox.getChildren().add(textFlow);
                    vbox_messages.getChildren().add(hBox);

                    client.sendMessageToServer(messageToSend);
                    txtType.clear();
                }
            }
        });
    }

    public void EmojiOnAction(MouseEvent mouseEvent) {

    }


    public void CamOnAction(MouseEvent mouseEvent) {

    }

    public void sendOnAction(ActionEvent actionEvent) {
    }

    public static  void addLabel(String msgFromServer, VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        javafx.scene.text.Text text = new Text(msgFromServer);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235);" +
                " -fx-background-radius: 20px");

        textFlow.setPadding(new Insets(5,10,5,10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);

            }
        });
    }
}

