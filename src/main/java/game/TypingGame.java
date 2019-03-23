package game;

import java.awt.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.io.*;
import java.lang.InterruptedException;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.application.Platform;
import javafx.scene.text.Text;

// main class for typing game
/**
 * TypingGame
 */
public class TypingGame extends Application {

    Random rand = new Random();

    // IO streams
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    public static void main(String[] args) {
        // main method to run program
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane pane = new BorderPane(); // main pane for game
        Button startButton = new Button("Start!");

        // pane to display score
        GridPane scorePane = new GridPane();
        scorePane.setPadding(new Insets(11.5,12.5,13.5,14.5));
        scorePane.setAlignment(Pos.CENTER);
        scorePane.setVgap(5.5);
        scorePane.setHgap(5.5);

        // labels to diplay the score
        scorePane.add(new Label("Welcome to Typing Tutor!"),0,0);
        scorePane.add(new Label("Type the words before they reach the bottom of the screen!"),0,1);
        scorePane.add(startButton,0,2);
        pane.setTop(scorePane);

        startButton.setOnAction(e-> {

            scorePane.getChildren().clear();
//            getGame();
            TextField typing = new TextField(); // text field for text entry
            typing.setPromptText("Type Words Here");
            pane.setBottom(typing); // put in the main pane

            scorePane.add(new Label("Score:"), 0, 0);
            Label score = new Label("0");
            scorePane.add(score, 1, 0);
            scorePane.add(new Label("Lives:"), 2, 0);
            Label lives = new Label("3");
            scorePane.add(lives, 3, 0);

            pane.setTop(scorePane);// add scores to main pane


            Pane wordPane = new Pane(); // pane for words to appear
            pane.setCenter(wordPane);

            // main game functionality
            // create list of words to use in game
            GenerateStrings strings = new GenerateStrings();
            ArrayList<String> words = strings.getWordList();
            // create the word to type
            Text word1 = new Text("");
            wordPane.getChildren().add(word1);
            resetWord(word1, words);


            // thread to run game
            new Thread(() -> {
                try {
                    while (true) {
                        int[] status = {0};
                        Platform.runLater(() -> {
                            word1.setY(word1.getY() + 1); // move the word down
                            // check to see if at bottom of pane
                            if (400.0 == word1.getY()) {
                                int l = Integer.parseInt(lives.getText().trim());
                                if (0 >= l) {
                                    status[0] = 1;
                                } else {
                                    l -= 1;
                                    lives.setText(l + "");
                                    resetWord(word1, words);
                                }
                            }
                        });
                        Thread.sleep(20);
                        if (1 == status[0]) break;
                    }
                    Thread.currentThread().interrupt();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();

            // add Typing functionality
            typing.setOnAction((f) -> {
                try {
                    String typed = typing.getText().trim();
                    if (typed.equals(word1.getText().trim())) {
                        int sc = Integer.parseInt(score.getText().trim());
                        sc += 1;
                        score.setText(sc + "");
                        resetWord(word1, words);
                    }
                    typing.clear();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });


        try {
            // getting localhost ip
            InetAddress ip = InetAddress.getByName("localhost");
            // establish the connection with server port
            Socket getServer = new Socket(ip, 8000);

            //Create an input stream to receive data from the server
            fromServer = new DataInputStream(getServer.getInputStream());
            // Create an output stream to send data to the server
            toServer = new DataOutputStream(getServer.getOutputStream());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //set scene
        Scene scene = new Scene(pane, 400, 400); // set scene
        primaryStage.setScene(scene); // put scene into stage
        primaryStage.setTitle("Typing Tutor"); // set title of the stage
        primaryStage.show(); // display the stage

    }


    private void resetWord(Text word, ArrayList<String> words){
        word.setText(words.get(rand.nextInt(words.size()-1)));
        word.setY(0);
        word.setX(rand.nextInt(350));
    }

}