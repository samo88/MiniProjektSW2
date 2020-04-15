package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameView extends BorderPane {

private BoardPane boardPane;

private Stage stage;

    public GameView(Stage stage){


      
        this.stage = stage;
        this.getStyleClass().add("game");
    }

    public void startGame(){

        Scene scene = new Scene(this, 1000, 800);
        stage.setScene(scene);
        stage.setTitle("CONNECT4 - THE GAME");
        scene.getStylesheets().add(getClass().getResource("applications.css").toExternalForm());
        stage.show();
        stage.setResizable(false);

    }

}
