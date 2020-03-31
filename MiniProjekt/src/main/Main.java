package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        StartView start = new StartView(primaryStage);
        BoardPane board = new BoardPane();
        InfoPane info = new InfoPane();
        PlayerPane playerOne = new PlayerPane();
        PlayerPane playerTwo = new PlayerPane();

        GameView game = new GameView(primaryStage);
        Controller controller = new Controller(start,game,board,primaryStage,info,playerOne,playerTwo);

        start.startSign();
    }
}
