import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server extends Application {

    //Variables
    private TextArea textArea = new TextArea();
    private int clientNumber = 0;

    @Override
    public void start(Stage primaryStage) {

        //Set scene
        Scene scene = new Scene(new ScrollPane(textArea), 450, 200);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Creating server
        new Thread( () -> {
            try {

                ServerSocket serverSocket = new ServerSocket(8000); //create serversocket

                textArea.appendText("Server started at " + new Date() + '\n');

                while (true) {

                    //Listen for a new connection request
                    Socket socket = serverSocket.accept();
                    clientNumber++; //increase client no

                    Platform.runLater( () -> {

                        textArea.appendText("A new client is connected to this server!" + '\n');
                        textArea.appendText(" - Starting thread for client " + clientNumber + " at " + new Date() + '\n');

                        //instance of InetAddress for the client on the socket
                        InetAddress inetAddress = socket.getInetAddress();
                        //get client's host name
                        textArea.appendText(" - Client " + clientNumber + "'s Host name is " + inetAddress.getHostName() + '\n');
                        //get client's IP address
                        textArea.appendText(" - Client " + clientNumber + "'s IP address is " + inetAddress.getHostAddress() + '\n' + '\n');
                    });

                    new Thread(new clientHandler(socket)).start();
                }
            }
            catch(IOException ex) {
                System.err.println(ex);
            }
        }).start();
    }

    class clientHandler implements Runnable {
        private Socket socket;

        public clientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {

                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    // 6. Receive radius from the client
                    double radius = inputFromClient.readDouble();

                    double area = radius * radius * Math.PI;

                    // 7. Send area back to the client
                    outputToClient.writeDouble(area);

                    Platform.runLater(() -> {
                        textArea.appendText("radius received from client: " + radius + '\n');
                        textArea.appendText("Area found: " + area + '\n');
                    });
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}