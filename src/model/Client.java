package model;

import controller.ClientController;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Client {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Error creating client");
            e.printStackTrace();
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void sendMessageToServer(String messageToServer){
        try {
            bufferedWriter.write(messageToServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println("Error Sending message to client");
            e.printStackTrace();
        }
    }

    public void receiveMessageFromServer(VBox vBox){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {

                    try {
                        String messageFromServer = bufferedReader.readLine();
                        ClientController.addLabel(messageFromServer, vBox);
                    } catch (IOException e) {
                        System.out.println("Error receiving message from the client");
                        closeEverything(socket,bufferedReader,bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
